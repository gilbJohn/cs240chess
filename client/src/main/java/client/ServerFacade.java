package client;

import com.google.gson.Gson;
import model.GameData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(int port) {
        this.serverURL = "http://localhost:" + port;
    }

    public void login(String username, String password) throws IOException, InterruptedException {
        String json = new Gson().toJson(Map.of(
                "username", username,
                "password", password
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL+ "/session"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void register(String username, String password, String email) throws IOException, InterruptedException {
        String json = new Gson().toJson(Map.of(
                "username", username,
                "password", password,
                "email", email
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL+ "/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public void logout(String authToken) throws IOException, InterruptedException{

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/session"))
                .header("authorization", authToken)
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public Collection<GameData> listGames(String authToken) throws IOException, InterruptedException{

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/session"))
                .header("authorization", authToken)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
