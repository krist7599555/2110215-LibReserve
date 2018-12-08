package history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Log {
	static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static final SimpleDateFormat DATEONLYFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public String username;
	public String position;
	public Integer startTime;
	public Integer endTime;
	public Integer reserveTime;

	static String now() {
		return DATEFORMAT.format(new Date());
	}

//	static String toSimpleDate(String date) {
//		try {
//			Calendar c = Calendar.getInstance();
//			for (int i = -1; i <= 1; ++i) {
//				c.setTime(DATEFORMAT.parse(now()));
//				c.add(Calendar.DATE, i);
//				String newstr = DATEONLYFORMAT.format(c.getTime());
//				date = date.replace(newstr, i == -1 ? "yesterday" : i == 0 ? "today" : i == 1 ? "tomorrow" : "");
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return date;
//	}
	static String toSimpleTime(int tm) {
		return (tm / 60) + ":" + (tm % 60);
	}
	
	
	public Log(JSONObject jo) {
		try {
			this.username      = jo.has("username")     ? jo.getString("username")     : "NO USER";
			this.position  = jo.has("position") ? jo.getString("position") : "Z0";
			this.startTime = jo.has("startTime")? jo.getInt("startTime")   : 0;
			this.endTime   = jo.has("endTime")  ? jo.getInt("endTime")     : 0;
			this.reserveTime = jo.has("reserveTime") ? jo.getInt("reserveTime") : 0;
		} catch (JSONException e) {
			System.err.println("[Error] JSON parse Log.java :" + jo);
		}
	}

	public static Log NONE() {
		return new Log(new JSONObject());
	}

	String getUser() {
		return username;
	}

	String getZone() {
		return position.substring(0, 1);
	}

	String getSeat() {
		return position.substring(1);
	}

	String getStartTime() {
		return toSimpleTime(this.startTime);
	}

	String getEndTime() {
		return toSimpleTime(this.endTime);
	}

	String getReserveTime() {
		return toSimpleTime(this.reserveTime);
	}

	String getPosition() {
		return getZone() + getSeat();
	}

	String getTitle() {
		return getZone() + getSeat() + " " + getStartTime();
	}
	@Override 
	public String toString() {
		return getUser() + " " + getPosition();
		
	}
};

