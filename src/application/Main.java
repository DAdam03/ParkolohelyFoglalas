package application;


import java.util.List;


import application.dao.DatabaseInitializer;

import application.controller.CarController;

import application.model.Car;


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
		
	}
	
	
}


