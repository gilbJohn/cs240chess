package client;

import chess.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

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

                if (authToken == null) {
                    if ("quit".equals(command)) {
                        break;
                    }
                    if (Objects.equals(command, "help")) {
                        loggedOut.printHelp();
                    }
                    if ("register".equals(command)) {
                        loggedOut.register(parts);
                    }
                    if ("login".equals(command)) {
                        loggedOut.login(parts);
                    }

                } else {

                }
            }
        }
    }
}
