package application.test;



import java.util.List;
import java.util.ArrayList;

import application.model.ParkingSpace;
import application.dao.ParkingSpaceDAO;


public class TestParkingSpaceDAO {
	
	private ParkingSpace TestInsert(){
		ParkingSpaceDAO parking_space_dao = ParkingSpaceDAO.GetInstance();
		List<ParkingSpace> parking_spaces_before = parking_space_dao.ListParkingSpaces();
		
		ParkingSpace new_parking_space = new ParkingSpace(0, ParkingSpace.ParkingSpaceType.NORMAL);
		
		parking_space_dao.InsertParkingSpace(new_parking_space);
		
		List<ParkingSpace> parking_spaces_after = parking_space_dao.ListParkingSpaces();
		
		for(ParkingSpace parking_space : parking_spaces_before){
			for(int ind=0; ind<parking_spaces_after.size(); ++ind){
				if(parking_space.GetId() == parking_spaces_after.get(ind).GetId()){
					parking_spaces_after.remove(ind);
					break;
				}
			}
		}
		
		ParkingSpace inserted = null;
		
		try{
			assert parking_spaces_after.size() == 1 : "the number of new parking_spaces isn't 1";
			inserted = parking_spaces_after.get(0);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return inserted;
	}
	
	private void TestDelete(ParkingSpace to_delete){
		ParkingSpaceDAO parking_space_dao = ParkingSpaceDAO.GetInstance();
		List<ParkingSpace> parking_spaces_before = parking_space_dao.ListParkingSpaces();
		
		parking_space_dao.DeleteParkingSpace(to_delete.GetId());
		
		List<ParkingSpace> parking_spaces_after = parking_space_dao.ListParkingSpaces();
		
		for(ParkingSpace parking_space : parking_spaces_after){
			for(int ind=0; ind<parking_spaces_before.size(); ++ind){
				if(parking_space.GetId() == parking_spaces_before.get(ind).GetId()){
					parking_spaces_before.remove(ind);
					break;
				}
			}
		}
		
		try{
			assert parking_spaces_before.size() == 1 : "the number of deleted parking_spaces isn't 1";
			assert parking_spaces_before.get(0).GetId() == to_delete.GetId() : "the deleted parking_space is incorrect";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void TestAll(){
		System.out.println("Testing ParkingSpaceDAO");
		
		ParkingSpace inserted = TestInsert();
		TestDelete(inserted);
	}
	
	
}



