package dataaccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.sql.SqlDataAccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class gamesDataTest {

    @BeforeEach
    void setUp() throws DataAccessException {
        new SqlDataAccess().clear();
    }
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
        var dataAccess = new SqlDataAccess();

        assertNull(dataAccess.getGame(-1));
    }

    @Test
    void listGames() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testGame1 = new GameData(1, "", "", "testGame1", new ChessGame());
        var testGame2 = new GameData(2, "", "", "testGame2", new ChessGame());
        var testGame3 = new GameData(3, "", "", "testGame3", new ChessGame());

        var gameID1 = assertDoesNotThrow(()->dataAccess.createGame(testGame1));
        var gameID2 = assertDoesNotThrow(()->dataAccess.createGame(testGame2));
        var gameID3 = assertDoesNotThrow(()->dataAccess.createGame(testGame3));

        var gameList = assertDoesNotThrow(dataAccess::listGames);

        assertEquals(3, gameList.size());

        assertTrue(gameList.contains(testGame1));
        assertTrue(gameList.contains(testGame2));
        assertTrue(gameList.contains(testGame3));
    }

    @Test
    void listGamesNull() throws DataAccessException {
        var dataAccess = new SqlDataAccess();
        dataAccess.clear();

        assertEquals(List.of(), dataAccess.listGames());
    }

    @Test
    void updateGameSavesChanges() throws DataAccessException{
        var dataAccess = new SqlDataAccess();
        var testGame = new GameData(1, "", "", "testGame", new ChessGame());
        var upDatedGame = new GameData(1, "white", "black", "newName", new ChessGame());

        dataAccess.createGame(testGame);

        dataAccess.updateGame(upDatedGame);

        assertEquals(upDatedGame, dataAccess.getGame(1));
    }

    @Test
    void updateGameRejectsNullName() throws DataAccessException {
        var dataAccess = new SqlDataAccess();
        var testGame = new GameData(1, "", "", "testGame", new ChessGame());
        var upDatedGame = new GameData(1, "white", "black", null, new ChessGame());

        dataAccess.createGame(testGame);

        assertThrows(DataAccessException.class, () -> dataAccess.updateGame(upDatedGame));
    }
}
