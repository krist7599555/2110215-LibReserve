package database;


/*
 * Store = Authenticate logic and memoi state
 */
public class Store {

	private static boolean _isLogin = false;
	private static String _username = null;

	public static boolean login(String username, String password) {
		if (Config.AUTO_LOGIN || LoginRequest.login(username, password)) {
			Store._username = username;
			if (username.length() == 0)
				Store._username = "1234567890";
			return setLogin(true);
		} else {
			Store._username = null;
			return setLogin(false);
		}
	}

	private static boolean setLogin(boolean b) {
		return Store._isLogin = b;
	}

	public static boolean isLogin() {
		return _isLogin;
	}

	public static boolean logout() {
		Store._username = null;
		if (isLogin()) {
			setLogin(false);
			return true;
		} else {
			setLogin(false);
			return false;
		}
	}

	public static String getUsername() {
		return isLogin() ? _username : null;
	}
}
