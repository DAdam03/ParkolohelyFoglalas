package application.dao;


import java.nio.file.*;
import java.sql.*;


public class DatabaseInitializer {

    public static void InitializeDatabase() throws Exception {

        try (Connection connection = Database.GetConnection()) {

            String schema = Files.readString(Paths.get("src/schema.sql"));

            Statement statement = connection.createStatement();
            statement.executeUpdate(schema);
        }
    }
}