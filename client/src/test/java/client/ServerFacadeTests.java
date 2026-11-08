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

    }

    @Test
    void createGameRejectsInvalidAuthToken() throws Exception {}

    @Test
    void listGamesReturnsCreatedGames() throws Exception {}

    @Test
    void listGamesRejectsInvalidAuthToken() throws Exception {}

    @Test
    void joinGameAssignsPlayerToRequestedColor() throws Exception {}

    @Test
    void joinGameRejectsAlreadyTakenColor() throws Exception {}

    @Test
    void clearRemovesUsersAuthTokensAndGames() throws Exception {}
}
