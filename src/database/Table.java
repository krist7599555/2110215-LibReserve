package database;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Table {
	static private JSONObject table, floor1st, floor2nd;

	public static final String __dirname() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}

	static {
		try {
			table = new JSONObject(new String(Files.readAllBytes(Paths.get(__dirname() + "/src/database/table.json"))));
			floor1st = table.getJSONObject("1st floor");
			floor2nd = table.getJSONObject("2nd floor");
		} catch (Exception e) {
			System.err.println("error on static database");
			e.printStackTrace();
		}
	}

	public static JSONObject getFloor(int ifloor) {
		return ifloor == 1 ? floor1st : ifloor == 2 ? floor2nd : null;
	}

	public static ArrayList<ArrayList<String>> getFloorSeats(int ifloor) {
		JSONObject floor = getFloor(ifloor);

		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		try {
			JSONArray row = floor.getJSONArray("seat");
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

	public static void main(String[] args) {
		System.out.println("HELLO");
	}
}
