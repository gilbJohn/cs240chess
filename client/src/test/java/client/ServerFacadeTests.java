package client;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
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

    @Test
    void registerReturnsAuthToken() throws Exception {}

    @Test
    void registerRejectsDuplicateUsername() throws Exception {}

    @Test
    void loginReturnsAuthToken() throws Exception {}

    @Test
    void loginRejectsWrongPassword() throws Exception {}

    @Test
    void logoutInvalidatesAuthToken() throws Exception {}

    @Test
    void logoutRejectsInvalidAuthToken() throws Exception {}

    @Test
    void createGameReturnsIdAndGameAppearsInList() throws Exception {}

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
