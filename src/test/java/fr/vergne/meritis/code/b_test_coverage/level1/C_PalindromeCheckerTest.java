package fr.vergne.meritis.code.b_test_coverage.level1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class C_PalindromeCheckerTest {

	@Test
	public void testPalindromeIsPalindrome() {
		assertThat(new PalindromeChecker().isPalindrome("noon"), is(true));
	}

	@Test
	public void testNonPalindromeIsNotPalindrome() {
		assertThat(new PalindromeChecker().isPalindrome("box"), is(false));
	}

	@Test
	public void testNearPalindromeIsNotPalindrome() {
		assertThat(new PalindromeChecker().isPalindrome("neon"), is(false));
	}
}
