package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Info {
	int[] position;
	int reserveNeed;

	Info(int[] position, int reserveNeed) {
		this.position = position;
		this.reserveNeed = reserveNeed;
	}
	public int[] getPosition() {
		return position;
	}
	public int getReserveNeed() {
		return reserveNeed;
	}
}

public class Config {
	public static final String[] zones = { "A", "B", "C", "D", "E" };
	public static final Map<String, Info> seats = Collections.unmodifiableMap(new HashMap<String, Info>() {
		private static final long serialVersionUID = 1L;
		{
			put("A", new Info(new int[] { 1, 2, 3 }, 6));
			put("B", new Info(new int[] { 4, 5 }, 2));
			put("C", new Info(new int[] { 6, 7, 8, 9 }, 3));
			put("D", new Info(new int[] { 10, 11 }, 1));
			put("E", new Info(new int[] { 10, 11 }, 2));
		}
	});
}
