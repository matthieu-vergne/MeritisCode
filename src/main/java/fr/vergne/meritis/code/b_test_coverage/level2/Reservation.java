package fr.vergne.meritis.code.b_test_coverage.level2;

import fr.vergne.meritis.code.b_test_coverage.level2.Hotel.Room;

public class Reservation {
	String reservationId;
	Hotel hotel;
	Hotel.Room room;
	Profile guestProfile;
	double rate;

	public Reservation(String reservationId, Hotel hotel, Room room, Profile guestProfile, double rate) {
		this.reservationId = reservationId;
		this.hotel = hotel;
		this.room = room;
		this.guestProfile = guestProfile;
		this.rate = rate;
	}

	public static class Profile {
		String firstName;
		String lastName;
		Address address;

		public Profile(String firstName, String lastName, Address address) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.address = address;
		}
	}
}
