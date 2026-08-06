package application.dao;


import java.util.List;
import java.util.ArrayList;

import java.sql.*;

import application.model.Car;


public class CarDAO {
	
	
	private static CarDAO instance;
	
	public CarDAO() {}
	
	public static CarDAO GetInstance() {
		if(instance == null) {
			instance = new CarDAO();
		}
		return instance;
	}
	
	
	public void InsertCar(Car car) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO cars (license, handicapped, electric) VALUES(?,?,?);", Statement.RETURN_GENERATED_KEYS))
		{
			
			statement.setString(1, car.GetLicensePlate());
            statement.setInt(2, car.GetCanUseHandicappedSpace() ? 1 : 0);
            statement.setInt(3, car.GetCanUseElectricSpace() ? 1 : 0);
			
			int inserted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	
	public List<Car> ListCars() {
		List<Car> result = new ArrayList<Car>();
		
		try(Connection connection = Database.GetConnection();
            Statement statement   = connection.createStatement();
            ResultSet result_set  = statement.executeQuery("SELECT * FROM cars;"))
        {
            while(result_set.next()){
                Car car = new Car(result_set.getInt("id"), result_set.getString("license"), result_set.getInt("handicapped") == 1, result_set.getInt("electric") == 1);

                result.add(car);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
		
		
		return result;
	}
	
	
	public void DeleteCar(String license_plate) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("Delete from cars where license=?;"))
		{
			
			statement.setString(1, license_plate);
			
			int deleted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
}


