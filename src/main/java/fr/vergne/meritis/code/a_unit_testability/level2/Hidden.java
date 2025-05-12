package fr.vergne.meritis.code.a_unit_testability.level2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class Hidden {
	static String dbConnectionDefinition;
	private static Path dbPath;

	static void initDB() {
		try {
			dbPath = Files.createTempFile("db", ".db");
		} catch (IOException cause) {
			throw new RuntimeException("Cannot create SQLite DB " + dbPath, cause);
		}
		dbConnectionDefinition = "jdbc:sqlite:" + dbPath;
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
			Statement statement;
			try {
				statement = connection.createStatement();
			} catch (SQLException cause) {
				throw new RuntimeException("Cannot create statement for DB", cause);
			}
			String query = "CREATE TABLE account (id INTEGER PRIMARY KEY, amount DOUBLE);";
			try {
				statement.execute(query);
			} catch (SQLException cause) {
				throw new RuntimeException("Cannot execute DB query: " + query, cause);
			}
		} catch (SQLException cause) {
			throw new RuntimeException("Cannot connect to SQLite DB " + dbPath, cause);
		}
	}

	static void tearDownDB() {
		try {
			Files.deleteIfExists(dbPath);
		} catch (IOException cause) {
			throw new RuntimeException("Cannot delete SQLite DB " + dbPath, cause);
		}
	}
}
