package fr.vergne.meritis.code.a_unit_testability.level2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class C_Account {

	private final int id;
	private final Supplier<Connection> connectionSupplier;

	public C_Account(Supplier<Connection> connectionSupplier, double startAmount) {
		this.connectionSupplier = connectionSupplier;
		try (Connection connection = connectionSupplier.get()) {
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

	public C_Account(double startAmount) {
		this(createConnectionSupplier(), startAmount);
	}

	public double getAmount() {
		try (Connection connection = connectionSupplier.get()) {
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
		try (Connection connection = connectionSupplier.get()) {
			PreparedStatement selectStatement = connection.prepareStatement("SELECT amount FROM account WHERE id = ?;");
			selectStatement.setDouble(1, id);
			ResultSet selectResultSet = selectStatement.executeQuery();
			selectResultSet.next();
			oldAmount = selectResultSet.getDouble("amount");
		} catch (SQLException cause) {
			throw new RuntimeException("Cannot get amount of account " + id, cause);
		}

		double newAmount = oldAmount + amountDiff;

		try (Connection connection = connectionSupplier.get()) {
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

	private static Supplier<Connection> createConnectionSupplier() {
		return () -> {
			try {
				return DriverManager.getConnection(Hidden.dbConnectionDefinition);
			} catch (SQLException cause) {
				throw new RuntimeException("Cannot get DB connection", cause);
			}
		};
	}
}
