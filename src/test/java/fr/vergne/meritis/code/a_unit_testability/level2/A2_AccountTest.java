package fr.vergne.meritis.code.a_unit_testability.level2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class A2_AccountTest {

	@BeforeAll
	static void init() {
		//Hidden.initDB();
	}

	@AfterAll
	static void tearDown() {
		//Hidden.tearDownDB();
	}

	@Test
	void testGetAmount() throws Exception {
		A_Account component = new A_Account(123.0);
		double amount = component.getAmount();
		assertThat(amount, is(equalTo(123.0)));
	}

	@Test
	void testChangeAmount() throws Exception {
		A_Account component = new A_Account(123.0);
		double newAmount = component.changeAmount(321.0);
		assertThat(newAmount, is(equalTo(444.0)));
	}

}
