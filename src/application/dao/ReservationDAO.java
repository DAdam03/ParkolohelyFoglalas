package application.dao;


import java.util.List;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.sql.*;

import application.model.Reservation;
import application.model.ParkingSpace;
import application.model.Car;


public class ReservationDAO {
	
	private static ReservationDAO instance;
	
	public ReservationDAO() {}
	
	public static ReservationDAO GetInstance() {
		if(instance == null) {
			instance = new ReservationDAO();
		}
		return instance;
	}
	
	
	
	public void InsertReservation(Reservation reservation) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO reservations (start_time, end_time, car_id, parking_space_id) VALUES(?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS))
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
			statement.setString(1, reservation.GetStartTime().format(formatter));
			statement.setString(2, reservation.GetEndTime().format(formatter));
			statement.setInt(3, reservation.GetReservator().GetId());
			statement.setInt(4, reservation.GetParkingSpace().GetId());
			
			int inserted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	public List<Reservation> ListReservations() {
		List<Reservation> result = new ArrayList<Reservation>();
		
		try(Connection connection = Database.GetConnection();
            Statement statement   = connection.createStatement();
            ResultSet result_set  = statement.executeQuery("SELECT reservations.id, start_time, end_time, car_id, parking_space_id, license, handicapped, electric, space_type FROM reservations, cars, parking_spaces WHERE car_id = cars.id AND parking_space_id = parking_spaces.id;"))
        {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_time = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_time = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				Car car = new Car(result_set.getInt("car_id"), result_set.getString("license"), result_set.getInt("handicapped")==1, result_set.getInt("electric")==1);
				ParkingSpace parking_space = new ParkingSpace(result_set.getInt("parking_space_id"), result_set.getInt("space_type"));
				
                Reservation reservation = new Reservation(id, start_time, end_time, car, parking_space);

                result.add(reservation);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsBySpace(ParkingSpace parking_space) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		try(Connection connection = Database.GetConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT reservations.id, start_time, end_time, car_id, cars.license, handicapped, electric FROM reservations, cars WHERE car_id = cars.id AND parking_space_id = ?;"))
        {
			statement.setInt(1, parking_space.GetId());
			ResultSet result_set = statement.executeQuery();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_time = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_time = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				Car car = new Car(result_set.getInt("car_id"), result_set.getString("license"), result_set.getInt("handicapped")==1, result_set.getInt("electric")==1);
                Reservation reservation = new Reservation(id, start_time, end_time, car, parking_space);

                result.add(reservation);
            }
			
			result_set.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByCar(Car car) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		try(Connection connection = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT reservations.id, start_time, end_time, parking_space_id, space_type FROM reservations, parking_spaces WHERE car_id = ? AND parking_space_id = parking_spaces.id;"))
        {
			statement.setInt(1, car.GetId());
			ResultSet result_set = statement.executeQuery();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_time = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_time = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				ParkingSpace parking_space = new ParkingSpace(result_set.getInt("parking_space_id"), result_set.getInt("space_type"));
				
                Reservation reservation = new Reservation(id, start_time, end_time, car, parking_space);

                result.add(reservation);
            }
			
			result_set.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByTimePeriod(LocalDateTime start_time, LocalDateTime end_time) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		try(Connection connection = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT reservations.id, start_time, end_time, car_id, parking_space_id, license, handicapped, electric, space_type FROM reservations, cars, parking_spaces WHERE car_id = cars.id AND parking_space_id = parking_spaces.id AND start_time < ? AND end_time > ?;"))
        {
			statement.setString(1, end_time.format(formatter));
			statement.setString(2, start_time.format(formatter));
			ResultSet result_set = statement.executeQuery();
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_t = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_t = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				Car car = new Car(result_set.getInt("car_id"), result_set.getString("license"), result_set.getInt("handicapped")==1, result_set.getInt("electric")==1);
				ParkingSpace parking_space = new ParkingSpace(result_set.getInt("parking_space_id"), result_set.getInt("space_type"));
				
                Reservation reservation = new Reservation(id, start_t, end_t, car, parking_space);

                result.add(reservation);
            }
			
			result_set.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByTimePeriodAndSpace(LocalDateTime start_time, LocalDateTime end_time, ParkingSpace parking_space) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		try(Connection connection = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT reservations.id, start_time, end_time, car_id, license, handicapped, electric FROM reservations, cars WHERE car_id = cars.id AND parking_space_id = ? AND start_time < ? AND end_time > ?;"))
        {
			statement.setInt(1, parking_space.GetId());
			statement.setString(2, end_time.format(formatter));
			statement.setString(3, start_time.format(formatter));
			ResultSet result_set = statement.executeQuery();
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_t = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_t = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				Car car = new Car(result_set.getInt("car_id"), result_set.getString("license"), result_set.getInt("handicapped")==1, result_set.getInt("electric")==1);
				
                Reservation reservation = new Reservation(id, start_t, end_t, car, parking_space);

                result.add(reservation);
            }
			
			result_set.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		
		return result;
	}
	
	
	public List<Reservation> ListReservationsByTimePeriodAndCar(LocalDateTime start_time, LocalDateTime end_time, Car car) {
		List<Reservation> result = new ArrayList<Reservation>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		try(Connection connection = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT reservations.id, start_time, end_time, parking_space_id,  space_type FROM reservations, parking_spaces WHERE car_id = ? AND parking_space_id = parking_spaces.id AND start_time < ? AND end_time > ?;"))
        {
			statement.setInt(1, car.GetId());
			statement.setString(2, end_time.format(formatter));
			statement.setString(3, start_time.format(formatter));
			ResultSet result_set = statement.executeQuery();
			
            while(result_set.next()){
				int id = result_set.getInt("id");
				LocalDateTime start_t = LocalDateTime.parse(result_set.getString("start_time"), formatter);
				LocalDateTime end_t = LocalDateTime.parse(result_set.getString("end_time"), formatter);
				ParkingSpace parking_space = new ParkingSpace(result_set.getInt("parking_space_id"), result_set.getInt("space_type"));
				
                Reservation reservation = new Reservation(id, start_t, end_t, car, parking_space);

                result.add(reservation);
            }
			
			result_set.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		
		return result;
	}
	
	
	
	
	public void DeleteReservation(int id) {
		try(Connection connection       = Database.GetConnection();
			PreparedStatement statement = connection.prepareStatement("Delete from reservations where id=?;"))
		{
			
			statement.setInt(1, id);
			
			int deleted_rows = statement.executeUpdate();
			
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
}