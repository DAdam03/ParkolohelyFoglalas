package application.model;

import java.time.LocalDateTime;



public class Reservation {
	
	int id;
	
	LocalDateTime start_time;
	LocalDateTime end_time;
	
	Car reservator;
	ParkingSpace parking_space;
	
	
	public Reservation(int id, LocalDateTime start_time, LocalDateTime end_time, Car reservator, ParkingSpace parking_space) {
		this.id            = id;
		this.start_time    = start_time;
		this.end_time      = end_time;
		this.reservator    = reservator;
		this.parking_space = parking_space;
	}
	
	
	
}



