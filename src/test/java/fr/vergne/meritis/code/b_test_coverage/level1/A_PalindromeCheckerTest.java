package fr.vergne.meritis.code.b_test_coverage.level1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class A_PalindromeCheckerTest {

	@Test
	public void testPalindromeIsPalindrome() {
		assertThat(new PalindromeChecker().isPalindrome("noon"), is(true));
	}
}
