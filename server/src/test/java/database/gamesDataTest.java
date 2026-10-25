package database;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SqlDataAccess;
import model.GameData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class gamesDataTest {
    @Test
    void createAndGetGames() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testGame = new GameData(1, "", "", "testGame", new ChessGame());

        var gameData = assertDoesNotThrow(()->dataAccess.createGame(testGame));

        assertEquals(testGame, dataAccess.getGame(gameData));
    }

    @Test
    void sameGameTwice() throws DataAccessException {

        var dataAccess = new SqlDataAccess();

        var testGame = new GameData(1, "", "", "testGame", new ChessGame());

        var gameData = assertDoesNotThrow(()->dataAccess.createGame(testGame));

        assertEquals(testGame, dataAccess.getGame(gameData));

        assertThrows(DataAccessException.class, ()-> dataAccess.createGame(testGame));
    }

    @Test
    void getNullGame() throws DataAccessException{

    }

}
