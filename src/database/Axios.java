package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * AxiosResponse = Helper class for sending request
 * 
 */
class AxiosResponse {
	private int code;
	private String data;

	AxiosResponse(int code, String data) {
		this.code = code;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public String getData() {
		return data;
	}

	public boolean isOK() {
		return 200 <= code && code < 300;
	}

	@Override
	public String toString() {
		return "AxiosResponse(" + code + ", \"" + data + "\")";
	}
};

public class Axios {
	public static AxiosResponse GET(String url) {
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();

			int code = connection.getResponseCode();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(code < 300 ? connection.getInputStream() : connection.getErrorStream()));
			StringBuilder results = new StringBuilder();
			for (String line; (line = reader.readLine()) != null;) {
				results.append(line);
			}
			connection.disconnect();

			return new AxiosResponse(code, results.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return new AxiosResponse(400, "Bad request: " + url);
		}
	}
}
