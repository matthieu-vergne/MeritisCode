package fr.vergne.meritis.code.a_unit_testability.level2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class B_Account {

	private final int id;
	private final DB db;

	public B_Account(DB db, double startAmount) {
		this.db = db;
		this.id = db.createAccountWithStartValue(startAmount);
	}
	
	public double getAmount() {
		return db.getAccountValue(id);
	}

	public double changeAmount(double amountDiff) {
		double oldAmount = db.getAccountValue(id);
		double newAmount = oldAmount + amountDiff;
		return db.setAccountValue(id, newAmount);
	}

	public static interface DB {
		int createAccountWithStartValue(double startAmount);

		double setAccountValue(int id, double newAmount);

		double getAccountValue(int id);

		public static DB create() {
			return new DB() {

				@Override
				public int createAccountWithStartValue(double startAmount) {
					try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
						PreparedStatement statement = connection
								.prepareStatement("INSERT INTO account (amount) VALUES (?) RETURNING id;");
						statement.setDouble(1, startAmount);
						ResultSet resultSet = statement.executeQuery();
						resultSet.next();
						return resultSet.getInt("id");
					} catch (SQLException cause) {
						throw new RuntimeException("Cannot create account", cause);
					}
				}

				@Override
				public double getAccountValue(int id) {
					try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
						PreparedStatement selectStatement = connection
								.prepareStatement("SELECT amount FROM account WHERE id = ?;");
						selectStatement.setDouble(1, id);
						ResultSet selectResultSet = selectStatement.executeQuery();
						selectResultSet.next();
						return selectResultSet.getDouble("amount");
					} catch (SQLException cause) {
						throw new RuntimeException("Cannot get amount of account " + id, cause);
					}
				}

				@Override
				public double setAccountValue(int id, double newAmount) {
					try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
						PreparedStatement updateStatement = connection
								.prepareStatement("UPDATE account SET amount = ? WHERE id = ? RETURNING amount;");
						updateStatement.setDouble(1, newAmount);
						updateStatement.setInt(2, id);
						ResultSet updateResultSet = updateStatement.executeQuery();
						updateResultSet.next();
						return updateResultSet.getDouble("amount");
					} catch (SQLException cause) {
						throw new RuntimeException("Cannot change amount of account " + id, cause);
					}
				}
			};
		}
	}
}
