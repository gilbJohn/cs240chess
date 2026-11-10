package client;

import chess.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException{

        ServerFacade facade = new ServerFacade(8080);
        LoggedOutClient loggedOut = new LoggedOutClient(facade);
        LoggedInClient loggedIn = new LoggedInClient(facade);
        PlayingClient playing = new PlayingClient(facade);

        String authToken = null;

        try (Scanner scanner = new Scanner(System.in)){
            while (true) {
                if (authToken == null) {
                    System.out.print("[Logged OUT] > ");
                } else {
                    System.out.print("[Logged IN] > ");
                }

                if (!scanner.hasNextLine()) {
                    break;
                }

                String input = scanner.nextLine().trim();
                String[] parts = input.split("\\s+");
                String command = parts[0];

                if ("quit".equals(command)) {
                    break;
                }
                try {
                    if (authToken == null) {
                        if (Objects.equals(command, "help")) {
                            loggedOut.printHelp();
                        }
                        if ("register".equals(command)) {
                            authToken = loggedOut.register(parts);
                        }
                        if ("login".equals(command)) {
                            authToken = loggedOut.login(parts);
                        }

                    } else {
                        switch (command) {
                            case "help" -> loggedIn.printHelp();
                            case "logout" -> authToken = loggedIn.logout(authToken);
                            case "create" -> loggedIn.createGame(parts, authToken);
                            case "list" -> loggedIn.listGames(authToken);
                            case "join" -> loggedIn.joinGame(parts, authToken);
                            case "observe" -> loggedIn.observe(parts, authToken);
                            default -> System.out.println("Please enter a valid command.");
                        }

                }
                } catch (IOException | RuntimeException e) {
                    System.out.print(e.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
