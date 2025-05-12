package fr.vergne.meritis.code.b_test_coverage.level1;

/**
 * Source: https://www.baeldung.com/java-mutation-testing-with-pitest
 */
public class PalindromeChecker {
	public boolean isPalindrome(String inputString) {
		if (inputString.length() == 0) {
			return true;
		} else {
			char firstChar = inputString.charAt(0);
			char lastChar = inputString.charAt(inputString.length() - 1);
			String mid = inputString.substring(1, inputString.length() - 1);
			return (firstChar == lastChar) && isPalindrome(mid);
		}
	}
}
