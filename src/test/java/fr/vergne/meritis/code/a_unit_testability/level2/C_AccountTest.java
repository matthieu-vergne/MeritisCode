package fr.vergne.meritis.code.a_unit_testability.level2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class C_AccountTest {

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
		var db = new Object() {
			Double doubleResult = null;
		};
		C_Account component = new C_Account(createConnectionSupplier(() -> db.doubleResult), 123.0);
		db.doubleResult = 123.0;
		double amount = component.getAmount();
		assertThat(amount, is(equalTo(123.0)));
	}

	@Test
	void testChangeAmount() throws Exception {
		var db = new Object() {
			Double doubleResult = null;
		};
		Supplier<Connection> connectionSupplier = createConnectionSupplier(() -> db.doubleResult);
		C_Account component = new C_Account(connectionSupplier, 123.0);
		db.doubleResult = 444.0;
		double newAmount = component.changeAmount(321.0);
		assertThat(newAmount, is(equalTo(444.0)));
	}

	private Supplier<Connection> createConnectionSupplier(Supplier<Double> doubleSupplier) {
		return () -> {
			try {
				ResultSet resultSet = mock(ResultSet.class);
				when(resultSet.getDouble(anyString())).then(invocation -> doubleSupplier.get());
				
				PreparedStatement statement = mock(PreparedStatement.class);
				when(statement.executeQuery()).thenReturn(resultSet);
				
				Connection connection = mock(Connection.class);
				when(connection.prepareStatement(anyString())).thenReturn(statement);
				
				return connection;
			} catch (SQLException cause) {
				throw new RuntimeException("Mocking, nothing should be thrown");
			}
		};
	}
}
