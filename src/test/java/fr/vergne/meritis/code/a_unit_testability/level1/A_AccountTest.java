package fr.vergne.meritis.code.a_unit_testability.level1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class A_AccountTest {

	private static SessionFactory sessionFactory;
	private static Session session;

	@BeforeAll
	static void init() {
		Hidden.initDB();
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", Hidden.dbConnectionDefinition);
		config.addAnnotatedClass(Account.class);
		sessionFactory = config.buildSessionFactory();
		session = sessionFactory.openSession();
	}

	@AfterAll
	static void tearDown() {
		session.close();
		sessionFactory.close();
		Hidden.tearDownDB();
	}

	@Test
	void testGetAmount() throws Exception {
		A_Account account = new A_Account(session, 123.0);
		double amount = account.getAmount();
		assertThat(amount, is(equalTo(123.0)));
	}

	@Test
	void testChangeAmount() throws Exception {
		A_Account account = new A_Account(session, 123.0);
		double newAmount = account.changeAmount(321.0);
		assertThat(newAmount, is(equalTo(444.0)));
	}

}
