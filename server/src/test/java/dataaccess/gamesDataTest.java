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

        var testGame = new GameData(
                0, "", "", "testGame", new ChessGame());

        int gameID = dataAccess.createGame(testGame);

        var expected = new GameData(
                gameID, "", "", "testGame", testGame.game());

        assertEquals(expected, dataAccess.getGame(gameID));
    }

    @Test
    void createGamesHaveDifferentIDs() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testGame = new GameData(
                0, "", "", "testGame", new ChessGame());

        int firstID = dataAccess.createGame(testGame);
        int secondID = dataAccess.createGame(testGame);

        assertNotEquals(firstID, secondID);
        assertNotNull(dataAccess.getGame(firstID));
        assertNotNull(dataAccess.getGame(secondID));
    }

    @Test
    void getNullGame() throws DataAccessException{
        var dataAccess = new SqlDataAccess();

        assertNull(dataAccess.getGame(-1));
    }

    @Test
    void listGames() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var board = new ChessGame();

        var testGame1 = new GameData(0, "", "", "testGame1", board);
        var testGame2 = new GameData(0, "", "", "testGame2", board);
        var testGame3 = new GameData(0, "", "", "testGame3", board);

        var gameID1 = assertDoesNotThrow(()->dataAccess.createGame(testGame1));
        var gameID2 = assertDoesNotThrow(()->dataAccess.createGame(testGame2));
        var gameID3 = assertDoesNotThrow(()->dataAccess.createGame(testGame3));

        var gameList = assertDoesNotThrow(dataAccess::listGames);

        assertEquals(3, gameList.size());

        assertTrue(gameList.contains(new GameData(gameID1, "", "", "testGame1", board)));
        assertTrue(gameList.contains(new GameData(gameID2, "", "", "testGame2", board)));
        assertTrue(gameList.contains(new GameData(gameID3, "", "", "testGame3", board)));
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
        var testGame = new GameData(0, "", "", "testGame", new ChessGame());

        var gameID = dataAccess.createGame(testGame);
        var upDatedGame = new GameData(gameID, "white", "black", "newName", new ChessGame());

        dataAccess.updateGame(upDatedGame);

        assertEquals(upDatedGame, dataAccess.getGame(gameID));
    }

    @Test
    void updateGameRejectsNullName() throws DataAccessException {
        var dataAccess = new SqlDataAccess();
        var testGame = new GameData(0, "", "", "testGame", new ChessGame());

        var gameID = dataAccess.createGame(testGame);

        var upDatedGame = new GameData(gameID, "white", "black", null, new ChessGame());


        assertThrows(DataAccessException.class, () -> dataAccess.updateGame(upDatedGame));
    }
}
