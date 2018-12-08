package database;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Database {
	static private JSONObject reserve;

	static {
		try {
			var table = Axios.GET("http://128.199.216.159:3721/table");
			Database.reserve = new JSONObject(table.getData());
		} catch (Exception e) {
			System.err.println("[Error] Parse Json Error");
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
		return (ArrayList<JSONObject>) toArrayList(arr);
	}
	public static ArrayList<JSONObject> getPositionRecord(String key) {
		System.err.println("[ERROR] NEED To fetch from SERVER in getPositionRecord in Database.java");
		ArrayList<JSONObject> res = new ArrayList<>();
		if (!reserve.has(key)) return res;
		try {
			JSONArray arr = reserve.getJSONArray(key);
			for (int i = 0; i < arr.length(); ++i) {
				res.add(arr.getJSONObject(i));
			}
		} catch (JSONException e) {
			System.err.println("[ERROR] Parse Json Error in getPositionRecord in Database.java");
			e.printStackTrace();
		}
		return res;
	}

	public static boolean add(String username, int startTime, int endTime, String position) {
		String queryStr = new StringBuilder()
				.append("?username=" + username)
				.append("&startTime=" + startTime)
				.append("&endTime=" + endTime)
				.append("&position=" + position).toString();
		String url = "http://128.199.216.159:3721/add" + queryStr;
		var res = Axios.GET(url);
		if (!res.isOK()) {
			System.err.println(res + " GET " + url);
		} else {
			System.out.println(res + " GET " + url);
		}
		return res.isOK();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; ++i) {			
			add("mee_ok", i * 30, i * 30 + 30, "C4");
		}
	}
	
}
