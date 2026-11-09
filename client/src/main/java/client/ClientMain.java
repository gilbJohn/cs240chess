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
                    if (Objects.equals(command, "help")) {
                        loggedIn.printHelp();
                    }
                    if ("logout".equals(command)){
                        authToken = loggedIn.logout(authToken);
                    }
                    if ("create".equals(command)) {
                        loggedIn.createGame(parts, authToken);
                    }
                    if ("list".equals(command)) {
                        loggedIn.listGames(authToken);
                    }
                    if ("join".equals(command)) {
                        loggedIn.joinGame(parts, authToken);
                    }
                    if("quit".equals(command)) {
                        break;
                    }
                    if("observe".equals(command)) {
                        loggedIn.observe(parts);
                    } else {
                        System.out.print("please input a valid command");
                    }
                }
            }
        }
    }
}
