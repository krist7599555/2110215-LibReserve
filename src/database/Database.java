package database;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.HistoryConflictException;
import exception.NotLoginException;


/*
 * Database 
 * 		= middleware to connected with server
 * 		
 * 		GET /add 		= add new record
 * 		GET /remove 	= remove record
 * 		GET /table		= return records base on filtering
 * 
 */
public class Database {
	static private JSONObject reserve;

	static {
		initialize();
	}

	public static void initialize() {
		try {
			var table = Axios.GET("http://128.199.216.159:3721/table");
			Database.reserve = new JSONObject(table.getData());
		} catch (Exception e) {
			System.err.println("[Error] Parse Json Error Database.java");
			e.printStackTrace();
		}
	}

	public static ArrayList<?> toArrayList(JSONArray arr) {
		ArrayList<Object> res = new ArrayList<>();
		for (int i = 0; i < arr.length(); ++i) {
			try {
				res.add(arr.get(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<JSONObject> getPositionRecordFULL(String key) {
		AxiosResponse table = Axios.GET("http://128.199.216.159:3721/table/" + key);
		JSONArray arr;
		try {
			arr = new JSONArray(table.getData());
		} catch (JSONException e) {
			arr = new JSONArray();
			e.printStackTrace();
		}
		return sortByStartTime((ArrayList<JSONObject>) toArrayList(arr));
	}

	public static ArrayList<JSONObject> sortByStartTime(ArrayList<JSONObject> arr) {
		arr.sort((l, r) -> {
			try {
				int ltm = l.getInt("startTime");
				int rtm = r.getInt("startTime");
				return ltm - rtm;
			} catch (JSONException e) {
				return 0;
			}
		});
		return arr;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<JSONObject> getHistory(String username) {
		AxiosResponse table = Axios.GET("http://128.199.216.159:3721/history/" + username);
		JSONArray arr;
		try {
			arr = new JSONArray(table.getData());
		} catch (JSONException e) {
			arr = new JSONArray();
			e.printStackTrace();
		}
		return sortByStartTime((ArrayList<JSONObject>) toArrayList(arr));
	}
	public static boolean isHistoryConflict(String username, int start, int end) {
		for (JSONObject log : getHistory(username)) {
			try {
				int s = log.getInt("startTime");
				int t = log.getInt("endTime");
				if (start < t && s < end) {
					return true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				System.err.println("[Error] Casting error Database.java /add of " + log);
			}
		}
		return false;
	}

	public static ArrayList<JSONObject> getPositionRecord(String key) {
		ArrayList<JSONObject> res = new ArrayList<>();
		if (!reserve.has(key))
			return res;
		try {
			JSONArray arr = reserve.getJSONArray(key);
			for (int i = 0; i < arr.length(); ++i) {
				res.add(arr.getJSONObject(i));
			}
		} catch (JSONException e) {
			System.err.println("[Error] Parse Json Error in getPositionRecord in Database.java");
			e.printStackTrace();
		}
		return sortByStartTime(res);
	}

	public static boolean add(String username, int startTime, int endTime, String position) throws NotLoginException, HistoryConflictException {
		if (!Store.isLogin()) throw new NotLoginException("user \"" + username + "\" is not login yet.");
		if (!Config.ALLOW_MULTI_RESERVE && isHistoryConflict(username, startTime, endTime)) {
			throw new HistoryConflictException();
		}
		String queryStr = new StringBuilder().append("?username=" + username).append("&startTime=" + startTime)
				.append("&endTime=" + endTime).append("&position=" + position).toString();
		String url = "http://128.199.216.159:3721/add" + queryStr;
		var res = Axios.GET(url);
		if (!res.isOK()) {
			System.err.println(res + " GET " + url);
		} else {
			System.out.println(res + " GET " + url);
		}
		refresh();
		return res.isOK();
	}

	public static boolean remove(String username, int startTime, int endTime, String position) {
		String queryStr = new StringBuilder().append("?username=" + username).append("&startTime=" + startTime)
				.append("&endTime=" + endTime).append("&position=" + position).toString();
		String url = "http://128.199.216.159:3721/remove" + queryStr;
		var res = Axios.GET(url);
		if (!res.isOK()) {
			System.err.println(res + " GET " + url);
		} else {
			System.out.println(res + " GET " + url);
		}
		refresh();
		return res.isOK();
	}

	static void refresh() {
		Database.initialize();
		Table.initialize();
	}

}
