package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.GameData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

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
                .uri(URI.create(serverURL + "/game"))
                .header("authorization", authToken)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Unable to List Games");
        }
        Gson gson = new Gson();

        JsonObject body = gson.fromJson(response.body(), JsonObject.class);

        GameData[] games = gson.fromJson(body.get("games"), GameData[].class);

        return Arrays.asList(games);
    }

    public int createGame(String gameName, String authToken) throws IOException, InterruptedException {
        String json = new Gson().toJson(Map.of(
                "gameName", gameName
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL+ "/game"))
                .header("authorization", authToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to create Game");
        }
        Gson gson = new Gson();

        JsonObject body = gson.fromJson(response.body(), JsonObject.class);

        return body.get("gameID").getAsInt();

    }

    public void joinGame(String playerColor, int gameID, String authToken)  throws IOException, InterruptedException {
        String json = new Gson().toJson(Map.of(
                "playerColor", playerColor,
                "gameID", gameID
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL+ "/game"))
                .header("authorization", authToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Unable to join game (HTTP " + response.statusCode() + ")");
        }
    }

    public void clear()  throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverURL + "/db"))
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Unable to Clear Data (HTTP " + response.statusCode() + ")");
        }
    }
}
