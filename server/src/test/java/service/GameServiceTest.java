package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    @Test
    @DisplayName("List Games Works")
    public void listGamesPositive() throws DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var auth = new AuthData("123", "user");

        dataAccess.createAuth(auth);

        var service = new GameService(dataAccess);

        assertTrue(service.listGames(auth.authToken()).isEmpty());

        var game1 = new GameData(0, "WHITE", "BLACK", "NAME", new ChessGame());
        var game2 = new GameData(1, "WHITE", "BLACK", "Game2", new ChessGame());
        var game3 = new GameData(2, "Steve", "Mike", "Game3", new ChessGame());

        dataAccess.createGame(game1);
        dataAccess.createGame(game2);
        dataAccess.createGame(game3);

        var result = service.listGames(auth.authToken());
        assertTrue(result.contains(game1));
        assertTrue(result.contains(game2));
        assertTrue(result.contains(game3));
    }
    @Test
    @DisplayName("List Games Works")
    public void listGamesNoAuth() throws DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var auth = new AuthData("123", "user");


        var service = new GameService(dataAccess);

        var game1 = new GameData(0, "WHITE", "BLACK", "NAME", new ChessGame());
        var game2 = new GameData(1, "WHITE", "BLACK", "Game2", new ChessGame());
        var game3 = new GameData(2, "Steve", "Mike", "Game3", new ChessGame());

        dataAccess.createGame(game1);
        dataAccess.createGame(game2);
        dataAccess.createGame(game3);

        var result = assertThrows(UnauthorizedResponse.class, () -> service.listGames(auth.authToken()));
        }

    @Test
    @DisplayName("Creates a Game")
    public void createGame() throws DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var auth = new AuthData("123", "user");

        dataAccess.createAuth(auth);


        var service = new GameService(dataAccess);

        var request = new GameData(0, null, null, "TEST GAME", new ChessGame());

        int gameID = service.createGame(auth.authToken(), request);

        var storedGame = dataAccess.getGame(gameID);

        assertNotNull(storedGame);
        assertEquals(gameID, storedGame.gameID());
        }

    @Test
    @DisplayName("Create a Game but No Auth")
    public void createNoAuth() throws DataAccessException {


        var dataAccess = new MemoryDataAccess();
        var auth = new AuthData("123", "user");

        var service = new GameService(dataAccess);

        var request = new GameData(0, null, null, "TEST GAME", new ChessGame());

        assertThrows(UnauthorizedResponse.class, () -> service.createGame(auth.authToken(), request));

        }
    @Test
    @DisplayName("Join Game Successfully")
    public void joinGame() throws DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var userService = new UserService(dataAccess);
        var user = new UserData("USER", "PASS", "EMAIL");

        var auth = userService.register(user);
        var service = new GameService(dataAccess);

        var request = new GameData(0, null, null, "TEST GAME", new ChessGame());

        int gameID = service.createGame(auth.authToken(), request);

        var joinRequest = new GameService.JoinGameRequest("WHITE", gameID);

        service.joinGame(auth.authToken(), joinRequest);


        var storedGame = dataAccess.getGame(gameID);

        assertNotNull(storedGame);
        assertEquals(gameID, storedGame.gameID());
        assertEquals("TEST GAME", storedGame.gameName());
        assertEquals("USER", storedGame.whiteUsername());
        assertNull(storedGame.blackUsername());
    }

    @Test
    @DisplayName("Join Color is Taken")
    public void colorTaken() throws DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var service = new GameService(dataAccess);

        var auth = new AuthData("test-token", "alice");
        dataAccess.createAuth(auth);

        var game = new GameData(
                123, "bob", null, "Test game", new ChessGame());
        dataAccess.createGame(game);

        var request = new GameService.JoinGameRequest("WHITE", 123);

        assertThrows(ForbiddenResponse.class, () -> service.joinGame(auth.authToken(), request));

        assertEquals(game, dataAccess.getGame(123));
    }
}
