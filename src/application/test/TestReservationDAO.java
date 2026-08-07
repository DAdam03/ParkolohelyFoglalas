package application.test;



import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.model.Reservation;
import application.model.Car;
import application.model.ParkingSpace;


import application.dao.CarDAO;
import application.dao.ParkingSpaceDAO;
import application.dao.ReservationDAO;


public class TestReservationDAO {
	
	private Reservation TestInsert(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		CarDAO car_dao                    = CarDAO.GetInstance();
		ParkingSpaceDAO parking_space_dao = ParkingSpaceDAO.GetInstance();
		ReservationDAO reservation_dao    = ReservationDAO.GetInstance();
		
		List<Reservation> reservations_before = reservation_dao.ListReservations();
		
		Car car = car_dao.ListCars().get(0);
		ParkingSpace parking_space = parking_space_dao.ListParkingSpaces().get(0);
		Reservation new_reservation = new Reservation(0, LocalDateTime.parse("2026-08-10 10:00:00", formatter), LocalDateTime.parse("2026-08-10 11:00:00", formatter), car, parking_space);
		
		reservation_dao.InsertReservation(new_reservation);
		
		List<Reservation> reservations_after = reservation_dao.ListReservations();
		
		for(Reservation reservation : reservations_before){
			for(int ind=0; ind<reservations_after.size(); ++ind){
				if(reservation.GetId() == reservations_after.get(ind).GetId()){
					reservations_after.remove(ind);
					break;
				}
			}
		}
		
		Reservation inserted = null;
		
		try{
			assert reservations_after.size() == 1 : "the number of new reservations isn't 1";
			inserted = reservations_after.get(0);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return inserted;
	}
	
	private void TestDelete(Reservation to_delete){
		ReservationDAO reservation_dao = ReservationDAO.GetInstance();
		List<Reservation> reservations_before = reservation_dao.ListReservations();
		
		reservation_dao.DeleteReservation(to_delete.GetId());
		
		List<Reservation> reservations_after = reservation_dao.ListReservations();
		
		for(Reservation reservation : reservations_after){
			for(int ind=0; ind<reservations_before.size(); ++ind){
				if(reservation.GetId() == reservations_before.get(ind).GetId()){
					reservations_before.remove(ind);
					break;
				}
			}
		}
		
		try{
			assert reservations_before.size() == 1 : "the number of deleted reservations isn't 1";
			assert reservations_before.get(0).GetId() == to_delete.GetId() : "the deleted reservation is incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void TestAll(){
		System.out.println("Testing ReservationDAO");
		
		Reservation inserted = TestInsert();
		TestDelete(inserted);
	}
	
	
}



