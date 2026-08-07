package application.test;


import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.model.Car;
import application.model.ParkingSpace;
import application.model.Reservation;

import application.controller.ReservationController;


public class TestReservationController {
	
	private void TestAdd(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		ReservationController reservation_controller = ReservationController.GetInstance();
		
		Car dummy_car = new Car(0, "asd", false, false);
		
		ParkingSpace dummy_space = new ParkingSpace(0, ParkingSpace.ParkingSpaceType.HANDICAPPED);
		ParkingSpace dummy_space_2 = new ParkingSpace(0, ParkingSpace.ParkingSpaceType.ELECTRIC);
		ParkingSpace dummy_space_3 = new ParkingSpace(0, ParkingSpace.ParkingSpaceType.NORMAL);
		
		LocalDateTime time_1 = LocalDateTime.parse("2026-08-10 10:00:00", formatter);
		LocalDateTime time_2 = LocalDateTime.parse("2026-08-10 11:00:00", formatter);
		
		List<Reservation> reservations_before = reservation_controller.ListReservations();
		
		
		reservation_controller.AddReservation(time_2, time_1, dummy_car, dummy_space_3);
		
		List<Reservation> reservations_after = reservation_controller.ListReservations();
		
		try{
			assert reservations_before.size() == reservations_after.size() : "incorrectly added reservation when times where reversed";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		reservation_controller.AddReservation(time_1, time_2, dummy_car, dummy_space);
		
		reservations_after = reservation_controller.ListReservations();
		
		try{
			assert reservations_before.size() == reservations_after.size() : "incorrectly added reservation when using handicapped space";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		
		reservation_controller.AddReservation(time_1, time_2, dummy_car, dummy_space_2);
		
		reservations_after = reservation_controller.ListReservations();
		
		try{
			assert reservations_before.size() == reservations_after.size() : "incorrectly added reservation when using electric space";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void TestAll(){
		System.out.println("Testing CarController");
		TestAdd();
	}
	
	
}

