package service;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataaccess.MemoryDataAccess;
import dataaccess.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    @Test
    @DisplayName("Removes All Data")
    public void clearRemovesAllData() throws DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var service = new ClearService(dataAccess);

        var user = new UserData("username", "password", "email");
        var game = new GameData(1234, "white", "black", "game", new ChessGame());
        var auth = new AuthData("authToken", "username");

        dataAccess.createUser(user);
        dataAccess.createGame(game);
        dataAccess.createAuth(auth);

        assertEquals(user, dataAccess.getUser(user.username()));
        assertEquals(auth, dataAccess.getAuth(auth.authToken()));
        assertTrue(dataAccess.listGames().contains(game));

        service.clear();


        assertNull(dataAccess.getUser(user.username()));
        assertNull(dataAccess.getAuth(auth.authToken()));
        assertTrue(dataAccess.listGames().isEmpty());
    }

}
