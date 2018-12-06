package database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginRequest {

	protected static boolean login(String username, String password) {
		try {
			return LoginRequest.call_me(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected static boolean call_me(String username, String password) throws Exception {
		String url = "https://script.google.com/macros/s/AKfycbwO4YW5CsBX9mgRegkB-_lAun7-8CZJEYSeN0Xu06ffrqKNYYY/exec?action=login&username="
				+ username + "&password=" + password;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = in.readLine();
		if (inputLine.substring(11, 15).equals("true")) {
			return true;
		}
		in.close();
		return false;
	}
}
