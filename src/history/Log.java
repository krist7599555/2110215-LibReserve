package history;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import database.Config;
import database.Database;

/*
 * Log = Wrapper of JSONObject information
 * 
 */
public class Log {

	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public String username;
	public String position;
	public Integer startTime;
	public Integer endTime;
	public Integer reserveTime;

	public static String getNowTime() {
		return TIMEFORMAT.format(Config.DATE_TIME);
	}

	public static String getNowDate() {
		return DATEFORMAT.format(Config.DATE_TIME);
	}

	public static String getNowDateTime() {
		return DATETIMEFORMAT.format(Config.DATE_TIME);
	}

	public static long getNowTimeMinute() {
		String[] s = getNowTime().split(":");
		return Integer.valueOf(s[0]) * 60 + Integer.valueOf(s[1]);
	}

	static String toSimpleTime(int tm) {
		return (tm / 60) + ":" + (tm % 60) + (tm % 60 <= 9 ? "0" : "");
	}

	public Log(String username, int startTime, int endTime, String position) {
		this.username = username;
		this.startTime = startTime;
		this.endTime = endTime;
		this.position = position;
		this.reserveTime = 0;
	}

	public Log(JSONObject jo) {
		try {
			this.username = jo.has("username") ? jo.getString("username") : "NO USER";
			this.position = jo.has("position") ? jo.getString("position") : "Z0";
			this.startTime = jo.has("startTime") ? jo.getInt("startTime") : 0;
			this.endTime = jo.has("endTime") ? jo.getInt("endTime") : 0;
			this.reserveTime = jo.has("reserveTime") ? jo.getInt("reserveTime") : 0;
		} catch (JSONException e) {
			System.err.println("[Error] JSON parse Log.java :" + jo);
		}
	}

	public String getUser() {
		return username;
	}

	public String getZone() {
		return position.substring(0, 1);
	}

	public String getSeat() {
		return position.substring(1);
	}

	public String getStartTime() {
		return toSimpleTime(this.startTime);
	}

	public String getEndTime() {
		return toSimpleTime(this.endTime);
	}

	public String getReserveTime() {
		return toSimpleTime(this.reserveTime);
	}

	public String getPosition() {
		return getZone() + getSeat();
	}

	public String getTitle() {
		return getZone() + getSeat() + " " + getStartTime();
	}

	@Override
	public String toString() {
		return "LOG: " + getUser() + " " + getPosition() + " " + getStartTime() + " " + getEndTime();
	}

	public boolean equals(Log log) {
		return log != null && toString().equals(log.toString());
	}
};
