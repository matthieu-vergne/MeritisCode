package fr.vergne.meritis.code.b_test_coverage.level2;

import java.util.Collection;

public class Hotel {
	final String hotelId;
	final String hotelName;
	final String description;
	final Collection<String> tags;
	final Address address;
	final Collection<Room> rooms;

	public Hotel(String hotelId, String hotelName, String description, Collection<String> tags, Address address,
			Collection<Room> rooms) {
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.description = description;
		this.tags = tags;
		this.address = address;
		this.rooms = rooms;
	}

	public Room roomAtNumber(int roomNumber) {
		return rooms.stream().filter(r -> r.roomNumber == roomNumber).findFirst().orElseThrow();
	}

	public static class Room {
		final String description;
		final int roomNumber;
		final String type;
		final double baseRate;

		public Room(String description, int roomNumber, double baseRate, String type) {
			this.description = description;
			this.roomNumber = roomNumber;
			this.type = type;
			this.baseRate = baseRate;
		}
	}
}