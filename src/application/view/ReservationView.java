package application.view;


import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.model.Car;
import application.model.ParkingSpace;
import application.model.Reservation;


public class ReservationView {
	
	
	
	public static void DisplayReservations(List<Reservation> reservations) {
		
		System.out.println("Foglalas kezdete    | Foglalas vege       | Parkolohely oszlop | Parkolohely sor | Parkolohely tipusa | Rendszam");
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		for(Reservation reservation : reservations){
			
			int[] parking_space_coordinates = ParkingSpaceView.GetParkingSpaceColumnAndRow(reservation.GetParkingSpace());
			int column_padding = "Parkolohely oszlop".length() - Integer.toString(parking_space_coordinates[0]).length();
			int row_padding = "Parkolohely sor".length() - Integer.toString(parking_space_coordinates[0]).length();
			
			String output_string = reservation.GetStartTime().format(formatter)+" | "+reservation.GetEndTime().format(formatter)+" | "+parking_space_coordinates[0];
			
			for(int i=0; i<column_padding; ++i){
				output_string += " ";
			}
			
			output_string += " | " + parking_space_coordinates[1];
			
			for(int i=0; i<row_padding; ++i){
				output_string += " ";
			}
			
			output_string += " | " + (reservation.GetParkingSpace().GetType() == ParkingSpace.ParkingSpaceType.HANDICAPPED ? "Mozgaskorlatozott" : reservation.GetParkingSpace().GetType() == ParkingSpace.ParkingSpaceType.ELECTRIC ? "Elektromos       " : "-                ") + "  | " + reservation.GetReservator().GetLicensePlate();
			
			System.out.println(output_string);
		}
		
		System.out.println("");
		
	}
	
	
	
}

