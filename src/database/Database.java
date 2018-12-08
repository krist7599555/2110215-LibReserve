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

	public static ArrayList<JSONObject> getPositionRecord(String key) {
		ArrayList<JSONObject> res = new ArrayList<>();
		if (!reserve.has(key)) return res;
		try {
			JSONArray arr = reserve.getJSONArray(key);
			for (int i = 0; i < arr.length(); ++i) {
				res.add(arr.getJSONObject(i));
			}
		} catch (JSONException e) {
			System.out.println("[ERROR] Parse Json Error in getPositionRecord");
			e.printStackTrace();
		}
		return res;
	}

	public static boolean add(String username, int startTime, int endTime, String position) {
		String queryStr = new StringBuilder()
				.append("?username=" + username)
				.append("&startTime=" + startTime)
				.append("?endTime=" + endTime)
				.append("&position=" + position).toString();
		var res = Axios.GET("http://128.199.216.159:3721/add" + queryStr);
		if (!res.isOK()) {
			System.err.println(res);
		}
		return res.isOK();
	}
	
}
