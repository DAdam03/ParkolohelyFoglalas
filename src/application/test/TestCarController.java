package application.test;


import java.util.List;
import java.util.ArrayList;

import application.model.Car;
import application.controller.CarController;


public class TestCarController {
	
	public static String test_license_plate = "TESTCTR-012";
	
	
	private void TestAdd(){
		CarController car_controller = CarController.GetInstance();
		
		Car new_car = car_controller.AddCar(test_license_plate, false, false);
		Car new_car_2 = car_controller.AddCar(test_license_plate, false, false);
		
		try{
			assert new_car != null && new_car.GetLicensePlate().equals(test_license_plate) : "first new car incorrect";
			assert new_car_2 == null : "second new car incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void TestAll(){
		System.out.println("Testing CarController");
		TestAdd();
		
		CarController car_controller = CarController.GetInstance();
		car_controller.DeleteCar(test_license_plate);
	}
	
	
}

