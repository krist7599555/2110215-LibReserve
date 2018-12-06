package database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Table {
	static private JSONObject table;

	public static final String __dirname() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}

	static {
		try {
			table = new JSONObject(new String(Files.readAllBytes(Paths.get(__dirname() + "/src/database/table.json"))));
		} catch (Exception e) {
			System.err.println("error on static database");
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getZones() {
		@SuppressWarnings("unchecked")
		Iterator<String> it = table.keys();
		ArrayList<String> res = new ArrayList<String>();
		while (it.hasNext()) {
			res.add((String) it.next());
		}
		return res;
	}
	
	public static JSONObject getZone(String zone) {
		try {
			return table.getJSONObject(zone);
		} catch (JSONException e) {
			return null;
		}
	}

	public static ArrayList<ArrayList<String>> getSeats(String zone) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		try {
			JSONArray row = table.getJSONObject(zone).getJSONArray("seat");
			for (int i = 0; i < row.length(); ++i) {
				ArrayList<String> tmp = new ArrayList<String>();
				JSONArray col = row.getJSONArray(i);
				for (int j = 0; j < col.length(); ++j) {
					tmp.add(col.getString(j));
				}
				res.add(tmp);
			}

		} catch (JSONException e) {
		}
		return res;
	}

}
