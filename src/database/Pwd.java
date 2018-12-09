package database;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Pwd = helper function to working with relative path
 */
public class Pwd {
	public final static String root;
	public final static String file;

	static {
		Path currentRelativePath = Paths.get("");
		root = currentRelativePath.toAbsolutePath().toString() + "/src";
		file = "file:///" + root;
	}
}
