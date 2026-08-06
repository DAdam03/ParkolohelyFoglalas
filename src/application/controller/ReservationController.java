package application.controller;


import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;

import application.dao.ReservationDAO;

import application.model.ParkingSpace;
import application.model.Car;
import application.model.Reservation;



public class ReservationController {
	
	private static ReservationController instance;
	
	private ReservationDAO reservation_dao;
	
	public ReservationController() {
		this.reservation_dao = ReservationDAO.GetInstance();
	}
	
	public static ReservationController GetInstance() {
		if(instance == null) {
			instance = new ReservationController();
		}
		return instance;
	}
	
	
	
	public void AddReservation(LocalDateTime start_time, LocalDateTime end_time, Car reservator, ParkingSpace parking_space) {
		Reservation reservation = new Reservation(0, start_time, end_time, reservator, parking_space);
		
		reservation_dao.InsertReservation(reservation);
		
	}
	
	
	
	public List<Reservation> ListReservations() {
		return reservation_dao.ListReservations();
	}
	
	public List<Reservation> ListReservationsBySpace(ParkingSpace parking_space) {
		return reservation_dao.ListReservationsBySpace(parking_space);
	}
	
	public List<Reservation> ListReservationsByCar(Car car) {
		return reservation_dao.ListReservationsByCar(car);
	}
	
	public List<Reservation> ListReservationsByTimePeriod(LocalDateTime start_time, LocalDateTime end_time) {
		return reservation_dao.ListReservationsByTimePeriod(start_time, end_time);
	}
	
	public List<Reservation> ListReservationsByTimePeriodAndSpace(LocalDateTime start_time, LocalDateTime end_time, ParkingSpace parking_space) {
		return reservation_dao.ListReservationsByTimePeriodAndSpace(start_time, end_time, parking_space);
	}
	
	public List<Reservation> ListReservationsByTimePeriodAndCar(LocalDateTime start_time, LocalDateTime end_time, Car car) {
		return reservation_dao.ListReservationsByTimePeriodAndCar(start_time, end_time, car);
	}
	
	
	
	public void DeleteReservation(int id) {
		reservation_dao.DeleteReservation(id);
	}
	
	
}





