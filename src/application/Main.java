package application;


import java.util.List;


import application.dao.DatabaseInitializer;

import application.controller.CarController;
import application.controller.ParkingSpaceController;

import application.model.Car;
import application.model.ParkingSpace;


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
		
		
		
	}
	
	
}


