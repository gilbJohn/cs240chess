package client;

import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() throws IOException, InterruptedException {
        server = new Server();
        var port = server.run(8080);
        serverFacade = new ServerFacade(port);
        serverFacade.clear();
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }
    // Leave it INNNN!!!

    @Test
    void registerReturnsAuthToken() throws Exception {

        String authToken = serverFacade.register("user", "password", "email");

        assertNotNull(authToken);
    }

    @Test
    void registerRejectsDuplicateUsername() throws Exception {
        serverFacade.register("user", "password", "email");
        assertThrows(IOException.class, () -> serverFacade.register("user", "password", "email"));
    }

    @Test
    void loginReturnsAuthToken() throws Exception {
        serverFacade.register("user", "password", "email");
        String authToken = serverFacade.login("user", "password");

        assertNotNull(authToken);
    }

    @Test
    void loginRejectsWrongPassword() throws Exception {
        assertThrows(IOException.class, () ->serverFacade.login("user", "password"));
    }

    @Test
    void logoutInvalidatesAuthToken() throws Exception {
        serverFacade.register("user", "password", "email");
        String auth = serverFacade.login("user", "password");

        serverFacade.listGames(auth);
        serverFacade.logout(auth);

        assertThrows(IOException.class, () -> serverFacade.listGames(auth));
    }

    @Test
    void logoutRejectsInvalidAuthToken() throws Exception {
        assertThrows(IOException.class, () -> serverFacade.listGames("LMAO"));
    }

    @Test
    void createGameReturnsIdAndGameAppearsInList() throws Exception {
        serverFacade.register("user", "password", "email");
        String auth = serverFacade.login("user", "password");

        int id = serverFacade.createGame("Test", auth);

        assertTrue(serverFacade.listGames(auth).stream().anyMatch(game -> game.gameID() == id));
    }

    @Test
    void createGameRejectsInvalidAuthToken() throws Exception {
       assertThrows(IOException.class, () -> serverFacade.createGame("LMAO", "LMAO"));
    }

    @Test
    void listGamesReturnsCreatedGames() throws Exception {
        serverFacade.register("user", "password", "email");
        String auth = serverFacade.login("user", "password");

        int id1 = serverFacade.createGame("Test", auth);
        int id2 = serverFacade.createGame("Test", auth);

        assertTrue(serverFacade.listGames(auth).stream().anyMatch(game -> game.gameID() == id1 || game.gameID() == id2));

    }

    @Test
    void listGamesRejectsInvalidAuthToken() throws Exception {
        assertThrows(IOException.class, () -> serverFacade.listGames( "LMAO"));
    }

    @Test
    void joinGameAssignsPlayerToRequestedColor() throws Exception {
        serverFacade.register("user", "password", "email");
        String auth = serverFacade.login("user", "password");

        int id = serverFacade.createGame("Test", auth);

        assertDoesNotThrow(()->serverFacade.joinGame("WHITE", id, auth));

        assertTrue(serverFacade.listGames(auth).stream()
                .anyMatch(game -> game.gameID() == id
                        && "user".equals(game.whiteUsername())));
    }

    @Test
    void joinGameRejectsAlreadyTakenColor() throws Exception {
        serverFacade.register("user", "password", "email");
        String auth = serverFacade.login("user", "password");

        int id = serverFacade.createGame("Test", auth);

        assertDoesNotThrow(()->serverFacade.joinGame("WHITE", id, auth));
        assertThrows(IOException.class, ()->serverFacade.joinGame("WHITE", id, auth));
    }

    @Test
    void clearRemovesUsersAuthTokensAndGames() throws Exception {
        String oldAuth = serverFacade.register("user", "password", "email");
        serverFacade.createGame("Test", oldAuth);

        serverFacade.clear();

        assertThrows(IOException.class, () -> serverFacade.listGames(oldAuth));
        assertThrows(IOException.class, () -> serverFacade.login("user", "password"));

        String newAuth = serverFacade.register("user", "password", "email");
        assertTrue(serverFacade.listGames(newAuth).isEmpty());
    }
}
