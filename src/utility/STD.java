package utility;

public class STD {
	public static <T> T back(T[] ar) {
		return ar[ar.length - 1];
	}
	public static int count(String str, String tok) {
		return (str.length() - str.replace(tok, "").length()) / tok.length();
	}
}
