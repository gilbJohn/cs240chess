package handler;

import com.google.gson.Gson;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import service.GameService;

import java.util.Map;

public class GameHandler {
    private final GameService gameService;
    public GameHandler(GameService gameService) {

        this.gameService = gameService;
    }

    public void listGames(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            var result = gameService.listGames(token);
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(Map.of("games", result)));

        } catch (UnauthorizedResponse error){
            context.status(401);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
    }

    public void createGame(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            var game = gson.fromJson(context.body(), GameData.class);

            var result = gameService.createGame(token, game);
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(Map.of("gameID", result)));

        } catch (UnauthorizedResponse error) {
            context.status(401);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        } catch (BadRequestResponse error) {
            context.status(400);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }

    }

    public void joinGame(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            var game = gson.fromJson(context.body(), GameService.joinGameRequest.class);
            gameService.joinGame(token, game);
            context.status(200);
            context.contentType("application/json");
            context.result("{}");

        } catch (UnauthorizedResponse error) {
            context.status(401);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
        catch (BadRequestResponse error) {
            context.status(400);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
        catch (ForbiddenResponse error) {
            context.status(403);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
    }
}
