package database;

/*
 * LoginRequest = helper function to login
 */
public class LoginRequest {

	protected static boolean login(String username, String password) {
		try {
			final String url = "https://script.google.com/macros/s/AKfycbwO4YW5CsBX9mgRegkB-_lAun7-8CZJEYSeN0Xu06ffrqKNYYY/exec";
			AxiosResponse inputLine = Axios.GET(url + "?action=login&username=" + username + "&password=" + password);
			if (inputLine.getData().substring(11, 15).equals("true")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
