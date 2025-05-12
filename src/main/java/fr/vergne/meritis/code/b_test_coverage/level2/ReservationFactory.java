package fr.vergne.meritis.code.b_test_coverage.level2;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import fr.vergne.meritis.code.b_test_coverage.level2.Reservation.Profile;

public class ReservationFactory {
	Hotel hotel;

	public ReservationFactory(Hotel hotel) {
		this.hotel = hotel;
	}

	public Reservation create(String xml) {
		JsonNode xmlNode = parseXml(xml);

		JsonNode reservationNode = xmlNode.get("HotelReservations").get("HotelReservation");
		String reservationId = reservationNode.get("UniqueID").get("ID").textValue();

		JsonNode customerNode = reservationNode.get("ResGuests").get("ResGuest").get("Profiles").get("ProfileInfo")
				.get("Profile").get("Customer");

		JsonNode addressNode = customerNode.get("Address");
		String streetAddress = addressNode.get("AddressLine").textValue();
		String city = addressNode.get("CityName").textValue();
		String stateProvince = addressNode.get("StateProv").textValue();
		Address address = new Address(streetAddress, city, stateProvince);

		JsonNode nameNode = customerNode.get("PersonName");
		String firstName = nameNode.get("GivenName").textValue();
		String lastName = nameNode.get("Surname").textValue();
		Profile guestProfile = new Profile(firstName, lastName, address);

		JsonNode roomRateNode = reservationNode.get("RoomStays").get("RoomStay").get("RoomRates").get("RoomRate");
		int roomNumber = Integer.parseInt(roomRateNode.get("RoomID").textValue());
		JsonNode rateNode = roomRateNode.get("Rates").get("Rate");
		long days = Duration.parse(rateNode.get("Duration").textValue()).toDaysPart();
		double base = Double.parseDouble(rateNode.get("Base").get("AmountBeforeTax").textValue());
		double baseTax = Double.parseDouble(rateNode.get("Base").get("Taxes").get("Amount").textValue());
		double discount = Double.parseDouble(rateNode.get("Discount").get("AmountBeforeTax").textValue());
		double discountTax = Double.parseDouble(rateNode.get("Discount").get("Taxes").get("Amount").textValue());
		double fee = Double.parseDouble(rateNode.get("Fees").get("Fee").get("Amount").textValue());

		double rate = (base + baseTax - discount - discountTax + fee) * days;

		return new Reservation(//
				reservationId, //
				hotel, //
				hotel.roomAtNumber(roomNumber), //
				guestProfile, //
				rate//
		);
	}

	private JsonNode parseXml(String xml) {
		XmlMapper mapper = new XmlMapper();
		JsonNode xmlNode;
		try {
			xmlNode = mapper.readTree(xml);
		} catch (JsonProcessingException cause) {
			throw new RuntimeException(cause);
		}
		return xmlNode;
	}
}