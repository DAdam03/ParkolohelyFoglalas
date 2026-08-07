package application.test;



import java.util.List;
import java.util.ArrayList;

import application.model.Car;
import application.dao.CarDAO;


public class TestCarDAO {
	
	
	public static String test_license_plate = "TESTDAO-012";
	
	
	private void TestInsert(){
		CarDAO car_dao = CarDAO.GetInstance();
		List<Car> cars_before = car_dao.ListCars();
		
		Car new_car = new Car(0, test_license_plate, false, false);
		
		car_dao.InsertCar(new_car);
		// @Note: Azonos rendszamu hozzaadasat nem szabad engedni
		car_dao.InsertCar(new_car);
		
		List<Car> cars_after = car_dao.ListCars();
		
		for(Car car : cars_before){
			for(int ind=0; ind<cars_after.size(); ++ind){
				if(car.GetLicensePlate().equals(cars_after.get(ind).GetLicensePlate())){
					cars_after.remove(ind);
					break;
				}
			}
		}
		
		try{
			assert cars_after.size() == 1 : "the number of new cars isn't 1";
			assert cars_after.get(0).GetLicensePlate().equals(test_license_plate) : "the inserted car is incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private void TestGetByLicensePlate(){
		CarDAO car_dao = CarDAO.GetInstance();
		Car car = car_dao.GetCarByLicensePlate(test_license_plate);
		
		try{
			assert car != null : "the returned car is null";
			assert car.GetLicensePlate().equals(test_license_plate) : "the returned car is incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private void TestDelete(){
		CarDAO car_dao = CarDAO.GetInstance();
		List<Car> cars_before = car_dao.ListCars();
		
		car_dao.DeleteCar(test_license_plate);
		
		List<Car> cars_after = car_dao.ListCars();
		
		for(Car car : cars_after){
			for(int ind=0; ind<cars_before.size(); ++ind){
				if(car.GetLicensePlate().equals(cars_before.get(ind).GetLicensePlate())){
					cars_before.remove(ind);
					break;
				}
			}
		}
		
		try{
			assert cars_before.size() == 1 : "the number of deleted cars isn't 1";
			assert cars_before.get(0).GetLicensePlate().equals(test_license_plate) : "the deleted car is incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void TestAll(){
		System.out.println("Testing CarDAO");
		
		TestInsert();
		TestGetByLicensePlate();
		TestDelete();
	}
	
	
}



