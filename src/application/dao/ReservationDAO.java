package application.dao;


import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;


import application.model.Reservation;
import application.model.ParkingSpace;
import application.model.Car;


public class ReservationDAO {
	
	public ReservationDAO() {}
	
	
	
	public void InsertReservation(Reservation reservation) {
		
	}
	
	
	public List<Reservation> ListReservations() {
		List<Reservation> result = new ArrayList<Reservation>();
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByCar(Car car) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByTimePeriod(LocalDateTime start_time, LocalDateTime end_time) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		return result;
	}
	
	
	public void DeleteReservation(int id) {
		
	}
	
}