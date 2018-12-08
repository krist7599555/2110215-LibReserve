package database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// STATIC TABLE POSITION
public class Table {
	static private JSONObject table;
	static private ArrayList<String> allseat = new ArrayList<>();
	static private ArrayList<String> allzone = new ArrayList<>();

	static {
		initialize();
	}

	protected static void initialize() {
		try {
			table = new JSONObject(new String(Files.readAllBytes(Paths.get(Pwd.root + "/database/table.json"))));
			for (var it = table.keys(); it.hasNext();) {
				var key = (String) it.next();
				allzone.add(key);

				String arr = table.getJSONObject(key).getJSONArray("seat").toString();
				for (String i : arr.split("[,\"\\[\\]]")) {
					if (i.length() != 0) {
						allseat.add(i);
					}
				}
				allseat.sort((l, r) -> {
					int len = l.length() - r.length();
					if (len == 0) return l.compareTo(r);
					int cmp = l.charAt(0) - r.charAt(0);
					if (cmp != 0) return cmp;
					return len;
				});
			}
		} catch (Exception e) {
			System.err.println("[Error] Table.java static");
			e.printStackTrace();
		}
	}
	
	public static void reinitialize() {
		initialize();
	}

	public static ArrayList<String> getZones() {
		return allzone;
	}

	public static JSONObject getZone(String zone) {
		try {
			return table.getJSONObject(zone);
		} catch (JSONException e) {
			return null;
		}
	}

	public static ArrayList<ArrayList<String>> getSeats(String zone) {
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		try {
			JSONArray row = table.getJSONObject(zone).getJSONArray("seat");
			for (int i = 0; i < row.length(); ++i) {
				ArrayList<String> tmp = new ArrayList<>();
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

	private static ArrayList<String> getValidSeat(int s, int t) {
		var res = new ArrayList<String>();
		for (String position : allseat) {
			boolean ok = true;
			for (var record : Database.getPositionRecord(position)) {
				try {
					int start = record.getInt("startTime");
					int end = record.getInt("endTime");
					if (s < end && start < t) { // if intersect
						ok = false;
						break;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if (ok) {
				res.add(position);
			}
		}
		return res;
	}

}
