package application.dao;


import java.util.List;
import java.util.ArrayList;

import java.sql.*;

import application.model.ParkingSpace;
import application.model.Car;


public class ParkingSpaceDAO {
	
	private static ParkingSpaceDAO instance;
	
	public ParkingSpaceDAO() {}
	
	public static ParkingSpaceDAO GetInstance() {
		if(instance == null) {
			instance = new ParkingSpaceDAO();
		}
		return instance;
	}
	
	
	
	public void InsertParkingSpace(ParkingSpace parking_space) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO parking_spaces (space_type) VALUES(?);", Statement.RETURN_GENERATED_KEYS))
		{
            statement.setInt(1, parking_space.GetType().index());
			
			int inserted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	public List<ParkingSpace> ListParkingSpaces() {
		List<ParkingSpace> result = new ArrayList<ParkingSpace>();
		
		try(Connection connection = Database.GetConnection();
            Statement statement   = connection.createStatement();
            ResultSet result_set  = statement.executeQuery("SELECT * FROM parking_spaces;"))
        {
            while(result_set.next()){
                ParkingSpace parking_space = new ParkingSpace(result_set.getInt("id"), result_set.getInt("space_type"));

                result.add(parking_space);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return result;
	}
	
	
	public void DeleteParkingSpace(int id) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("Delete from parking_spaces where id=?;"))
		{
			
			statement.setInt(1, id);
			
			int deleted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
}