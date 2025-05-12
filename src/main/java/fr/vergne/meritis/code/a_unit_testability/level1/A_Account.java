package fr.vergne.meritis.code.a_unit_testability.level1;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class A_Account {

	private final Account dbAccount;
	private final Session session;

	public A_Account(Session session, double startAmount) {
		this.session = session;
		this.dbAccount = new Account();
		dbAccount.setAmount(startAmount);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(dbAccount);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
	}

	public A_Account(Session session, int accountId) {
		this.session = session;
		this.dbAccount = session.find(Account.class, accountId);
	}

	public int getId() {
		return dbAccount.getId();
	}

	public double getAmount() {
		return dbAccount.getAmount();
	}

	public double changeAmount(double amountDiff) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dbAccount.setAmount(dbAccount.getAmount() + amountDiff);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
		return dbAccount.getAmount();
	}

}
