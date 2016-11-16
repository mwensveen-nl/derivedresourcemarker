package nl.mwensveen.derivedresourcemarker.util;

import nl.mwensveen.derivedresourcemarker.util.FileRegExpUtil;
import org.junit.Assert;
import org.junit.Test;

public class FileRegExpUtilTest {

	@Test
	public void testStarRegExp() {
		Assert.assertTrue(FileRegExpUtil.matches(".*\\.abc", "bla.abc"));
	}

	@Test
	public void testStarWildCard() {
		Assert.assertTrue(FileRegExpUtil.matchesWildCard("*.abc", "bla.abc"));
	}

	@Test
	public void testStarRegExpNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matches(".*\\.abc", "bla.abcd"));
	}

	@Test
	public void testStarWildCardNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("*.abc", "bla.abcd"));
	}

	@Test
	public void testExactMatchRegExp() {
		Assert.assertTrue(FileRegExpUtil.matches("bla.abc", "bla.abc"));
	}

	@Test
	public void testExactWildCard() {
		Assert.assertTrue(FileRegExpUtil.matchesWildCard("bla.abc", "bla.abc"));
	}

	@Test
	public void testExactMatchRegExpNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matches("bla.abc", "bla.abcd"));
	}

	@Test
	public void testExactWildCardNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("bla.abc", "bla.abcd"));
	}

	@Test
	public void testExactWildCardNoMatch2() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("bla.abc", "abla.abc"));
	}

	@Test
	public void testQuestionMarkRegExp() {
		Assert.assertTrue(FileRegExpUtil.matches(".\\.abc", "a.abc"));
	}

	@Test
	public void testQuestionMarkWildCard() {
		Assert.assertTrue(FileRegExpUtil.matchesWildCard("?.abc", "a.abc"));
	}

	@Test
	public void testQuestionMarkRegExpNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matches(".\\.abc", "a.abcd"));
	}

	@Test
	public void testQuestionMarkWildCardNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("?.abc", "a.abcd"));
	}

	@Test
	public void testQuestionMarkWildCardNoMatch2() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("?.abc", "ab.abc"));
	}

	@Test
	public void testEndStarRegExp() {
		Assert.assertTrue(FileRegExpUtil.matches("abc\\..*", "abc.bla"));
	}

	@Test
	public void testEndStarWildCard() {
		Assert.assertTrue(FileRegExpUtil.matchesWildCard("abc.*", "abc.bla"));
	}

	@Test
	public void testEndStarRegExpNoMach() {
		Assert.assertFalse(FileRegExpUtil.matches("abc\\..*", "abcd.bla"));
	}

	@Test
	public void testEndStarWildCardNoMatch() {
		Assert.assertFalse(FileRegExpUtil.matchesWildCard("abc.*", "abcd.bla"));
	}

}
