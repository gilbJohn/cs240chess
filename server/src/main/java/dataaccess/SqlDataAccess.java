package dataaccess;

import java.sql.SQLException;

public class SqlDataAccess {

    public SqlDataAccess() throws DataAccessException {
        DatabaseManager.createDatabase();

        String usersSQL = """
                CREATE TABLE IF NOT EXISTS
                            users (
                                username VARCHAR(255) PRIMARY KEY,
                                password VARCHAR(255) NOT NULL,
                                email VARCHAR(255) NOT NULL
                            )
                """;
        String gamesSQL = """
                CREATE TABLE IF NOT EXISTS
                            games (
                                gameID INT AUTO_INCREMENT PRIMARY KEY,
                                whiteUsername VARCHAR(255),
                                blackUsername VARCHAR(255),
                                gameName VARCHAR(255) NOT NULL,
                                game TEXT NOT NULL
                            )
                """;

        String authSQL = """
                CREATE TABLE IF NOT EXISTS
                            auth (
                                authToken VARCHAR(255) PRIMARY KEY,
                                username VARCHAR(255) NOT NULL
                            )
                """;


        try (var connection = DatabaseManager.getConnection();
             var userStatement = connection.prepareStatement(usersSQL);
             var gameStatement = connection.prepareStatement(gamesSQL);
             var authStatement = connection.prepareStatement(authSQL);
        )
        {
            userStatement.executeUpdate();
            gameStatement.executeUpdate();
            authStatement.executeUpdate();
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            throw new DataAccessException("Failed to create database tables", e);
        }
    }

    public static void main(String[] args) throws DataAccessException {
        new SqlDataAccess();
    }
}
