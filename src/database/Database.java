package database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Database {
	static private JSONObject reserve;

	public static final String __dirname() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}
	static {
		try {
			Database.reserve = new JSONObject(
					new String(Files.readAllBytes(Paths.get(__dirname() + "/src/database/reserve.json"))));
		} catch (Exception e) {
			System.err.println("error on static database");
			e.printStackTrace();
		}
	}

	public static ArrayList<JSONObject> getPositionRecord(String key) {
		ArrayList<JSONObject> res = new ArrayList<JSONObject>();
		try {
			JSONObject pos = reserve.getJSONObject(key);
			JSONArray rec = pos.getJSONArray("records");
			for (int i = 0; i < rec.length(); ++i) {
				res.add(rec.getJSONObject(i));
			}
		} catch (JSONException e) {}
		return res;
	}
}
