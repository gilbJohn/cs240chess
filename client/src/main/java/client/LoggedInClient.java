package client;

import java.io.IOException;

public class LoggedInClient {
    private ServerFacade facade;

    public LoggedInClient(ServerFacade facade) {
        this.facade = facade;
    }

    public void printHelp() {
        System.out.print("""
                            create <GAME_NAME> - create a chess game
                            list - show available games
                            join <WHITE|BLACK> <GAME_NUMBER> - join a game as White or Black
                            observe <GAME_NUMBER> - watch a game
                            logout - return to the logged-out menu
                            quit - exit the client
                            help - show available commands
                            """);
    }

    public String logout(String authToken) throws IOException, InterruptedException {
        facade.logout(authToken);
        return null;
    }

    public void createGame(String[] parts, String authToken) throws IOException, InterruptedException {
        try  {
            System.out.print("GAME ID: " + facade.createGame(parts[1], authToken));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void listGames(String authToken) throws IOException, InterruptedException {
        try {
            System.out.print(facade.listGames(authToken));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void joinGame(String[] parts, String authToken) throws IOException, InterruptedException, RuntimeException {
        try {
            facade.joinGame(parts[1], Integer.parseInt(parts[2]), authToken);
        } catch (IOException | InterruptedException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }


    public void observe(String[] parts) {

    }
}
