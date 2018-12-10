package database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import history.Log;

/*
 * Table = Helper function to process seats & position
 * 
 * 		= Pure table 		=> process to json and matrix
 * 		= Server Record		=> helping filter data & record before render
 *  
 */
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
			return table.getJSONObject(zone.substring(0, 1));
		} catch (JSONException e) {
			System.err.println("[Error] Table.java getZone [" + zone + "] not in " + table);
			e.printStackTrace();
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
	public static ArrayList<String> getFaltSeats(String zone) {
		ArrayList<String> res = new ArrayList<>();
		try {
			JSONArray row = table.getJSONObject(zone).getJSONArray("seat");
			for (int i = 0; i < row.length(); ++i) {
				JSONArray col = row.getJSONArray(i);
				for (int j = 0; j < col.length(); ++j) {
					String s = col.getString(j);
					if (s.length() != 0) {
						res.add(s);
					}
				}
			}

		} catch (JSONException e) {
		}
		return res;
	}
	

	public static ArrayList<String> getValidSeat(long s, long t, String search) {
		var res = new ArrayList<String>();
		if (s < Log.getNowTimeMinute()) {
			return res;
		}
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
	
	public static int getRequireNumber(String pos) {
		try {
			return table.getJSONObject(pos.substring(0, 1)).getInt("require");
		} catch (JSONException e) {
			return 5;
		}
	}
	
	public static boolean isValidSeat(long s, long t, String position) {
		return s != t && getValidSeat(s, t, position).contains(position);
	}

}
