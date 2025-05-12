package fr.vergne.meritis.code.a_unit_testability.level2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class A_Account {

	private final int id;

	public A_Account(double startAmount) {
		try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO account (amount) VALUES (?) RETURNING id;");
			statement.setDouble(1, startAmount);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			id = resultSet.getInt("id");
		} catch (SQLException cause) {
			throw new RuntimeException("Cannot create account", cause);
		}
	}

	public double getAmount() {
		try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
			PreparedStatement statement = connection.prepareStatement("SELECT amount from account where id = ?;");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getDouble("amount");
		} catch (SQLException cause) {
			throw new RuntimeException("Cannot get amount of account " + id, cause);
		}
	}

	public double changeAmount(double amountDiff) {
		double oldAmount;
		try (Connection connection = DriverManager.getConnection(Hidden.dbConnectionDefinition)) {
			PreparedStatement selectStatement = connection.prepareStatement("SELECT amount FROM account WHERE id = ?;");
			selectStatement.setDouble(1, id);
			ResultSet selectResultSet = selectStatement.executeQuery();
			selectResultSet.next();
			oldAmount = selectResultSet.getDouble("amount");
		} catch (SQLException cause) {
			throw new RuntimeException("Cannot get amount of account " + id, cause);
		}

		double newAmount = oldAmount + amountDiff;

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

}
