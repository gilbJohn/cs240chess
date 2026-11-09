package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoggedInClient {
    private final ServerFacade facade;

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
        if(parts.length < 2) {
            System.out.print("Usage: create <GAME_NAME>");
            return;
        }

        try  {
            String name = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
            System.out.print("GAME ID: " + facade.createGame(parts[1], authToken)
                               + "\n Created " + name + ". Type list to see available games \n"
            );
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // I need to define this here I guess
    private final List<Integer> gameIds = new ArrayList<>();

    public void listGames(String authToken) throws IOException, InterruptedException {
        try {

            var games = facade.listGames(authToken);
            gameIds.clear();

            for(var game: games) {
                gameIds.add(game.gameID());

                String white = game.whiteUsername() == null ? "Open" : game.whiteUsername();
                String black = game.blackUsername() == null ? "Open" : game.blackUsername();

                System.out.printf("TOTAL GAMES: %d. NAME: %s (ID: %d) | White: %s | Black: %s%n",
                        gameIds.size(), game.gameName(), game.gameID(), game.whiteUsername(), game.blackUsername()
                );
            }

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
