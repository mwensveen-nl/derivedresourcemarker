package nl.mwensveen.derivedresourcemarker.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRegExpUtil {

	private FileRegExpUtil() {
		// private constructor for util

	}

	public static boolean matches(String regExp, String fileName) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(fileName);

		return matcher.matches();
	}

	public static boolean matchesWildCard(String wildCard, String fileName) {
		return matches("\\A" + wildCard.replace(".", "\\.").replace("*", ".*").replace("?", ".") + "\\Z", fileName);

	}
}
