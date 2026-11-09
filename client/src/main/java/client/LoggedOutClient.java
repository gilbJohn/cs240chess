package client;

import java.io.IOException;

public class LoggedOutClient {

    private final ServerFacade facade;

    public LoggedOutClient(ServerFacade facade) {
        this.facade = facade;
    }

    public void printHelp() {
        System.out.print("""
                            register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                            login <USERNAME> <PASSWORD> - to play chess
                            quit - playing chess
                            help - with possible commands
                            """);
    }

    public void register(String[] parts) {
        try {
            if (parts.length != 4) {
                System.out.println("Usage: register <username> <password> <email>");
            }
            facade.register(parts[1], parts[2], parts[3]);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(String[] parts) {
        try {
            if (parts.length != 3) {
                System.out.println("Usage: login <username> <password>");
            }
            facade.login(parts[1], parts[2]);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
