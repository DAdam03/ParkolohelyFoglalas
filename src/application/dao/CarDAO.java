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
	
	
	public int InsertCar(Car car) {
		int inserted_rows = 0;
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO cars (license, handicapped, electric) VALUES(?,?,?);", Statement.RETURN_GENERATED_KEYS))
		{
			
			statement.setString(1, car.GetLicensePlate());
            statement.setInt(2, car.GetCanUseHandicappedSpace() ? 1 : 0);
            statement.setInt(3, car.GetCanUseElectricSpace() ? 1 : 0);
			
			inserted_rows = statement.executeUpdate();
			
		}catch(SQLIntegrityConstraintViolationException e){
			// @Hack
			System.out.println("Mar letezik ilyen rendszamu auto.");
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return inserted_rows;
	}
	
	public Car GetCarByLicensePlate(String license_plate) {
		Car result = null;
		
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM cars WHERE license = ? LIMIT 1;", Statement.RETURN_GENERATED_KEYS))
		{
			
			statement.setString(1, license_plate);
			
			ResultSet result_set = statement.executeQuery();
			
			
			while(result_set.next()){
                Car car = new Car(result_set.getInt("id"), result_set.getString("license"), result_set.getInt("handicapped") == 1, result_set.getInt("electric") == 1);

                result = car;
            }
			
			
			result_set.close();
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return result;
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
	
	
	public int DeleteCar(String license_plate) {
		int deleted_rows = 0;
		
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("Delete from cars where license=?;"))
		{
			
			statement.setString(1, license_plate);
			
			deleted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		
		return deleted_rows;
	}
	
}


