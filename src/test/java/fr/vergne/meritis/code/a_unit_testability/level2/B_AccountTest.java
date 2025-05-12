package fr.vergne.meritis.code.a_unit_testability.level2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class B_AccountTest {

	@BeforeAll
	static void init() {
//		Hidden.initDB();
	}

	@AfterAll
	static void tearDown() {
//		Hidden.tearDownDB();
	}

	@Test
	void testGetAmount() throws Exception {
		B_Account component = new B_Account(mockDB(), 123.0);
		double amount = component.getAmount();
		assertThat(amount, is(equalTo(123.0)));
	}

	@Test
	void testChangeAmount() throws Exception {
		B_Account component = new B_Account(mockDB(), 123.0);
		double newAmount = component.changeAmount(321.0);
		assertThat(newAmount, is(equalTo(444.0)));
	}

	private B_Account.DB mockDB() {
		return new B_Account.DB() {

			private double amount;

			@Override
			public int createAccountWithStartValue(double startAmount) {
				this.amount = startAmount;
				return 1;
			}

			@Override
			public double getAccountValue(int id) {
				return this.amount;
			}

			@Override
			public double setAccountValue(int id, double newAmount) {
				this.amount = newAmount;
				return this.amount;
			}
		};
	}
}
