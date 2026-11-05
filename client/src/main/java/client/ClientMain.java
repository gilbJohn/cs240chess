package client;

import chess.*;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ServerFacade facade = new ServerFacade(8080);

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            facade.login(username, password);
        } catch (IOException e) {
            System.out.println("Could not communicate with the server.");
        } catch (InterruptedException e) {
            System.out.println("Login was interrupted.");
        }
    }
}
