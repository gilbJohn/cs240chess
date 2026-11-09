package client;

import java.io.IOException;
import java.util.Scanner;

public class LoggedOutClient {

    public void printHelp() {
        System.out.print("""
                            register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                            login <USERNAME> <PASSWORD> - to play chess
                            quit - playing chess
                            help - with possible commands
                            """);
    }
}
