package dataaccess;

import java.sql.SQLException;

public class SqlDataAccess {

    public SqlDataAccess() throws DataAccessException {
        DatabaseManager.createDatabase();

        try (var connection = DatabaseManager.getConnection()){
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws DataAccessException {
        new SqlDataAccess();
    }
}
