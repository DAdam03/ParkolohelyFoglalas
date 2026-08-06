package application.dao;



import java.sql.*;


public class Database {
	
	private static final String URL      = "jdbc:sqlite:data/app.db";

    public static Connection GetConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
	
}


