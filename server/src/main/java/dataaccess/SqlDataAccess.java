package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SqlDataAccess implements DataAccess {

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

    // Run and test on the DATABASE
    public static void main(String[] args) throws DataAccessException {
        new SqlDataAccess();
    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        String sql = """
                INSERT INTO users (username, password, email)
                VALUES(?,?,?)
                """;
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.username());
            statement.setString(2, user.password());
            statement.setString(3, user.email());

            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("couldn't create user", e);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {

        String sql = """
                SELECT username, password, email\s
                FROM users\s
                WHERE username = ?
               \s""";
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (var results = statement.executeQuery()){
                if (results.next()) { // this line means if the user was found I guess.
                     return new UserData(
                            results.getString("username"),
                            results.getString("password"),
                            results.getString("email")
                    );
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("couldn't get the user",e);
        }
        return null;
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {
        String sql = """
                INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, game)
                VALUES(?,?,?, ?, ?)
                """;

        String gameJson = new Gson().toJson(game.game());
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setInt(1, game.gameID());
            statement.setString(2, game.whiteUsername());
            statement.setString(3, game.blackUsername());
            statement.setString(4, game.gameName());
            statement.setString(5, gameJson);

            statement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("couldn't create game", e);
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        String sql = """
                SELECT gameID, whiteUsername, blackUsername, gameName, game\s
                FROM games\s
                WHERE gameID = ?
               \s""";
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setInt(1, gameID);

            try (var results = statement.executeQuery()){
                if (results.next()) { // this line means if the user was found I guess.
                    return new GameData(
                            results.getInt("gameID"),
                            results.getString("whiteUsername"),
                            results.getString("blackUsername"),
                            results.getString("gameName"),
                            new Gson().fromJson(results.getString("game"), ChessGame.class)
                    );
                }

            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("couldn't do it :(", e);
        }
        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        String sql = """
                INSERT INTO auth (authtoken, username)
                VALUES(?,?)
                """;
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setString(1, auth.authToken());
            statement.setString(2, auth.username());

            statement.executeUpdate();

        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Couldn't create", e);
        }
    }

    @Override
    public AuthData getAuth(String auth) throws DataAccessException {
        String sql = """
                SELECT authtoken, username
                FROM auth\s
                WHERE authToken = ?
               \s""";
        try(var connection = DatabaseManager.getConnection();
            var statement = connection.prepareStatement(sql)) {

            statement.setString(1, auth);

            try (var results = statement.executeQuery()){
                if (results.next()) { // this line means if the user was found I guess.
                    return new AuthData(
                            results.getString("authToken"),
                            results.getString("username")
                    );
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("failed to get auth token", e);
        }
        return null;
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {

    }
}
