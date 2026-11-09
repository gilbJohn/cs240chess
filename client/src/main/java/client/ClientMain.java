package client;

import chess.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        ServerFacade facade = new ServerFacade(8080);
        LoggedOutClient loggedOut = new LoggedOutClient();
        LoggedInClient loggedIn = new LoggedInClient();
        PlayingClient playing = new PlayingClient();

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

                if (authToken == null) {
                    if ("quit".equals(input)) {
                        break;
                    }
                    if (Objects.equals(input, "help")) {
                        loggedOut.printHelp();
                    }
                    if ("login".equals(input)) {
                        // later
                    }

                } else {

                }
            }
        }
    }
}
