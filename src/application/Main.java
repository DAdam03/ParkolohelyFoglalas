package application;


import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import application.dao.DatabaseInitializer;

import application.controller.CarController;
import application.controller.ParkingSpaceController;
import application.controller.ReservationController;

import application.model.Car;
import application.model.ParkingSpace;
import application.model.Reservation;


public class Main {
	
	public static void main(String[] args){
		
		try {
			
			DatabaseInitializer.InitializeDatabase();
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		
		
		CarController car_controller = CarController.GetInstance();
		
		car_controller.AddCar("asd-012", true, true);
		
		List<Car> cars = car_controller.ListCars();
		
		for(Car car : cars){
			System.out.println(car.GetLicensePlate()+", "+car.GetCanUseHandicappedSpace()+", "+car.GetCanUseElectricSpace());
		}
		
		car_controller.DeleteCar("asd-012");
		
		
		ParkingSpaceController parking_space_controller = ParkingSpaceController.GetInstance();
		
		parking_space_controller.AddParkingSpace(ParkingSpace.ParkingSpaceType.NORMAL);
		
		List<ParkingSpace> parking_spaces = parking_space_controller.ListParkingSpaces();
		
		for(ParkingSpace parking_space : parking_spaces){
			System.out.println(parking_space.GetId()+", "+parking_space.GetType());
		}
		
		parking_space_controller.DeleteParkingSpace(15);
		
		
		
		ReservationController reservation_controller = ReservationController.GetInstance();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		reservation_controller.AddReservation(LocalDateTime.parse("2026-09-01 00:00:00", formatter), LocalDateTime.parse("2026-09-01 10:00:00", formatter), cars.get(0), parking_spaces.get(0));
		
		List<Reservation> reservations = reservation_controller.ListReservations();
		
		for(Reservation reservation : reservations){
			System.out.println(reservation.GetId()+", "+reservation.GetStartTime().format(formatter)+", "+reservation.GetEndTime().format(formatter)+", "+reservation.GetReservator().GetId()+", "+reservation.GetReservator().GetLicensePlate()+", "+reservation.GetReservator().GetCanUseHandicappedSpace()+", "+reservation.GetReservator().GetCanUseElectricSpace()+", "+reservation.GetParkingSpace().GetId()+", "+reservation.GetParkingSpace().GetType());
		}
		
		
		System.out.println("\nreservations of space 0:");
		
		reservations = reservation_controller.ListReservationsBySpace(parking_spaces.get(0));
		
		for(Reservation reservation : reservations){
			System.out.println(reservation.GetId()+", "+reservation.GetStartTime().format(formatter)+", "+reservation.GetEndTime().format(formatter)+", "+reservation.GetReservator().GetId()+", "+reservation.GetReservator().GetLicensePlate()+", "+reservation.GetReservator().GetCanUseHandicappedSpace()+", "+reservation.GetReservator().GetCanUseElectricSpace()+", "+reservation.GetParkingSpace().GetId()+", "+reservation.GetParkingSpace().GetType());
		}
		
		
		System.out.println("\nreservations of car 0:");
		
		reservations = reservation_controller.ListReservationsByCar(cars.get(0));
		
		for(Reservation reservation : reservations){
			System.out.println(reservation.GetId()+", "+reservation.GetStartTime().format(formatter)+", "+reservation.GetEndTime().format(formatter)+", "+reservation.GetReservator().GetId()+", "+reservation.GetReservator().GetLicensePlate()+", "+reservation.GetReservator().GetCanUseHandicappedSpace()+", "+reservation.GetReservator().GetCanUseElectricSpace()+", "+reservation.GetParkingSpace().GetId()+", "+reservation.GetParkingSpace().GetType());
		}
		
		
		System.out.println("\nreservations between 2026-08-11 10:00:00 and 2026-08-20 12:00:00:");
		
		reservations = reservation_controller.ListReservationsByTimePeriod(LocalDateTime.parse("2026-08-11 10:00:00", formatter), LocalDateTime.parse("2026-08-20 12:00:00", formatter));
		
		for(Reservation reservation : reservations){
			System.out.println(reservation.GetId()+", "+reservation.GetStartTime().format(formatter)+", "+reservation.GetEndTime().format(formatter)+", "+reservation.GetReservator().GetId()+", "+reservation.GetReservator().GetLicensePlate()+", "+reservation.GetReservator().GetCanUseHandicappedSpace()+", "+reservation.GetReservator().GetCanUseElectricSpace()+", "+reservation.GetParkingSpace().GetId()+", "+reservation.GetParkingSpace().GetType());
		}
		
		
		reservation_controller.DeleteReservation(10);
		
		
		
	}
	
	
}


