package application.controller;


import java.util.List;
import java.util.ArrayList;

import application.dao.ParkingSpaceDAO;
import application.model.ParkingSpace;



public class ParkingSpaceController {
	
	private static ParkingSpaceController instance;
	
	private ParkingSpaceDAO parking_space_dao;
	
	
	public ParkingSpaceController() {
		this.parking_space_dao = ParkingSpaceDAO.GetInstance();
	}
	
	public static ParkingSpaceController GetInstance() {
		if(instance == null) {
			instance = new ParkingSpaceController();
		}
		return instance;
	}
	
	
	public void AddParkingSpace(ParkingSpace.ParkingSpaceType type) {
		ParkingSpace parking_space = new ParkingSpace(0, type);
		
		parking_space_dao.InsertParkingSpace(parking_space);
		
	}
	
	
	
	public List<ParkingSpace> ListParkingSpaces() {
		return parking_space_dao.ListParkingSpaces();
	}
	
	
	
	public void DeleteParkingSpace(int id) {
		parking_space_dao.DeleteParkingSpace(id);
	}
	
	
}

