package dataaccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.sql.SqlDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    @Test
    void clearWorks() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        assertDoesNotThrow(dataAccess::clear);

        var game = new GameData(0, null, null, "game", new ChessGame());
        var auth = new AuthData("token", "user");
        var user = new UserData("user", "password", "email");

        var gameId = dataAccess.createGame(game);
        dataAccess.createAuth(auth);
        dataAccess.createUser(user);

        dataAccess.clear();

        assertNull(dataAccess.getGame(gameId));
        assertNull(dataAccess.getAuth("token"));
        assertNull(dataAccess.getUser("user"));
    }

    @Test
    void clearEmptyDatabaseDoesNotThrow() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        dataAccess.clear();

        assertDoesNotThrow(dataAccess::clear);

        //should be done!
    }
}
