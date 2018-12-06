package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Log {
	static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static final SimpleDateFormat DATEONLYFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public String user;
	public String zone;
	public Integer seat;
	public String startTime;
	public String endTime;
	public String reserveTime;

	static String now() {
		return DATEFORMAT.format(new Date());
	}

	static String toSimpleDate(String date) {
		try {
			Calendar c = Calendar.getInstance();
			for (int i = -1; i <= 1; ++i) {
				c.setTime(DATEFORMAT.parse(now()));
				c.add(Calendar.DATE, i);
				String newstr = DATEONLYFORMAT.format(c.getTime());
				date = date.replace(newstr, i == -1 ? "yesterday" : i == 0 ? "today" : i == 1 ? "tomorrow" : "");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public Log(JSONObject jo) {
		try {
			this.user = jo.getString("user");
			this.zone = jo.getString("zone");
			this.seat = Integer.parseInt(jo.getString("seat"));
			this.startTime = jo.getString("startTime");
			this.endTime = jo.getString("endTime");
			this.reserveTime = jo.getString("reserveTime");
		} catch (JSONException e) {
			System.out.println("JSON parse to log fail " + jo);
		}
	}
	public Log(String user, String zone, Integer seat) {
		this(user, zone, seat, now(), now(), now());
	}

	public Log(String user, String zone, Integer seat, String startTime, String endTime, String reserveTime) {
		this.user = user;
		this.zone = zone;
		this.seat = seat;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reserveTime = reserveTime;
	}

	String getUser() {
		return user;
	}

	String getZone() {
		return zone;
	}

	String getSeat() {
		return seat.toString();
	}

	String getStartTime() {
		return toSimpleDate(this.startTime);
	}

	String getEndTime() {
		return toSimpleDate(this.endTime);
	}

	String getReserveTime() {
		return toSimpleDate(this.reserveTime);
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

