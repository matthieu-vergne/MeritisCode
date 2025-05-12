package fr.vergne.meritis.code.b_test_coverage.level2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.vergne.meritis.code.b_test_coverage.level2.Hotel.Room;

class A_ReservationFactoryTest {

	@Test
	public void test() throws IOException {
		String xml = loadXml("OTA_HotelResNotifRQ.xml");
		Room room1105 = new Room("Budget Room, 1 Queen Bed (Cityside)", 1105, 96.99, "Budget Room");
		Room room1205 = new Room("Deluxe Room, 2 Double Beds (City View)", 1205, 150.99, "Deluxe Room");
		Hotel hotel = new Hotel(//
				"1", //
				"Stay-Kay City Hotel", //
				"Ideally located on the main commercial artery of the city in the heart of New York.", //
				List.of("Free wifi", "on-site parking", "indoor pool", "continental breakfast"), //
				new Address("677 5th Ave", "New York", "NY"), //
				List.of(room1105, room1205)//
		);
		Reservation reservation = new ReservationFactory(hotel).create(xml);
		assertThat(reservation.guestProfile.firstName, is("John"));
	}

	private String loadXml(String xmlPath) throws IOException {
		InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
		StringBuilder builder = new StringBuilder();
		try (InputStreamReader streamReader = new InputStreamReader(xmlStream, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		}
		return builder.toString();
	}

}
