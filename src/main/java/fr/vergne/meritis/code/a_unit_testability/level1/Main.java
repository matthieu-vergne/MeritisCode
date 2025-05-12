package fr.vergne.meritis.code.a_unit_testability.level1;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
	public static void main(String[] args) {
		Hidden.initDB();
		Configuration config = new Configuration();
		config.setProperty("hibernate.connection.url", Hidden.dbConnectionDefinition);
		config.addAnnotatedClass(Account.class);
		try (SessionFactory sessionFactory = config.buildSessionFactory()) {
			int accountId;
			try (Session session = sessionFactory.openSession()) {
				A_Account account = new A_Account(session, 123.0);
				accountId = account.getId();
				System.out.println("Start " + accountId + " with " + account.getAmount());
			}

			try (Session session = sessionFactory.openSession()) {
				List<Account> accounts = session.createQuery("from Account", Account.class).list();
				accounts.forEach(account -> {
					System.out.println("Account " + account.getId() + " = " + account.getAmount());
				});
			}

			try (Session session = sessionFactory.openSession()) {
				double amount = 321.0;
				A_Account account = new A_Account(session, accountId);
				double finalAmount = account.changeAmount(amount);
				System.out.println("Add " + amount + " to " + accountId + " = " + finalAmount);
			}

			try (Session session = sessionFactory.openSession()) {
				List<Account> accounts = session.createQuery("from Account", Account.class).list();
				accounts.forEach(account -> {
					System.out.println("Account " + account.getId() + " = " + account.getAmount());
				});
			}
		}

	}
}
