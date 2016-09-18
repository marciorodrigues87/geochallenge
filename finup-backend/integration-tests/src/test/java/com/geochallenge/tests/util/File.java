package com.geochallenge.tests.util;

import java.util.Scanner;

public final class File {

	public static String asString(String path) {
		try (final Scanner scanner = new Scanner(File.class.getResourceAsStream(path))) {
			scanner.useDelimiter("NONE");
			return scanner.next();
		}
	}

	public static String asStringReplacing(String path, String replace, String replacement) {
		return asString(path).replace(replace, replacement);
	}

}
