package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginRequest {

	public static boolean login(String username, String password) {
		     try {
		         return LoginRequest.call_me(username, password);
		        } catch (Exception e) {
		         e.printStackTrace();
		       }
		     return false;
		     }
			   
		public static boolean call_me(String username, String password) throws Exception {
		     String url = "https://script.google.com/macros/s/AKfycbwO4YW5CsBX9mgRegkB-_lAun7-8CZJEYSeN0Xu06ffrqKNYYY/exec?action=login&username=" + username + "&password=" + password;
		     URL obj = new URL(url);
		     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		     // optional default is GET
		     con.setRequestMethod("GET");
		     //add request header
		     con.setRequestProperty("User-Agent", "Mozilla/5.0");
//		     int responseCode = con.getResponseCode();
//		     System.out.println("\nSending 'GET' request to URL : " + url);
//		     System.out.println("Response Code : " + responseCode);
		     BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		     String inputLine = in.readLine();
		     if (inputLine.substring(11, 15).equals("true")) {
		    	 return true;
		     }
		     
//		    StringBuffer response = new StringBuffer();
//		     
//		     while ((inputLine = in.readLine()) != null) {
//		     	System.out.println(inputLine);
//		     }
		     in.close();
		     // print in String
		     // System.out.println(response.toString());
		     return false;
		   }
}
