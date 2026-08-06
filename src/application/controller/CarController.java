package application.controller;


import java.util.List;
import java.util.ArrayList;


import application.dao.CarDAO;

import application.model.Car;



public class CarController {
	
	private static CarController instance;
	
	private CarDAO car_dao;
	
	
	public CarController() {
		this.car_dao = CarDAO.GetInstance();
	}
	
	public static CarController GetInstance() {
		if(instance == null) {
			instance = new CarController();
		}
		return instance;
	}
	
	
	
	
	
	public void AddCar(String license_plate, boolean can_use_handicapped_space, boolean can_use_electric_space) {
		Car car = new Car(0, license_plate, can_use_handicapped_space, can_use_electric_space);
		
		car_dao.InsertCar(car);
		
	}
	
	
	
	public List<Car> ListCars() {
		return car_dao.ListCars();
	}
	
	
	
	public void DeleteCar(int id) {
		
	}
	
	
}



