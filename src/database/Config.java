package database;

import java.text.ParseException;
import java.util.Date;

import history.Log;

/*
 * Config = simple config file help you develop software easier
 * 
 */
public class Config {
	static public final String STARTER_PATH = "/root/A/A13";
	static public final Boolean AUTO_LOGIN = true;
	static public final Boolean ALLOW_MULTI_RESERVE = false;
	static public final Boolean FIX_DATE_TIME = false;
	static public final Date DATE_TIME;
	static {
		Date parse = null;
		if (FIX_DATE_TIME) {
			try {
				parse = Log.DATETIMEFORMAT.parse("2018-07-06 10:00");
			} catch (ParseException e) {
				System.err.println("[Error] Config.DATE_TIME Parse Error");
			}
		}
		DATE_TIME = parse == null ? new Date() : parse;
	}
}
