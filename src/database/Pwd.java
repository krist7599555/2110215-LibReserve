package database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

/*
 * Pwd = helper function to working with relative path
 */
public class Pwd {
//	public final static String root;
//	public final static String file;
//
//	static {
//		Path currentRelativePath = Paths.get("");
//		root = currentRelativePath.toAbsolutePath().toString() + "/src";
//		file = "file:///" + root;
//	}
	static {
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(getURL("../").getPath());
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		System.out.println(cl);
		System.out.println(root());
	}
	public static String root() {
		return getURL("../").getPath().replace("/bin/", "") + "/src";
	}
	public static URL getURL(String path) {
		return new Pwd().getClass().getResource(path);
	}
	public static InputStream getStream(String path) {
		return new Pwd().getClass().getResourceAsStream(path);
	}
	public static String getFile(String path) {
		return new BufferedReader(new InputStreamReader(new Pwd().getClass().getResourceAsStream(path))).lines().collect(Collectors.joining("\n"));
	}
}
