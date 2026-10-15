package handler;

import com.google.gson.Gson;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;
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
            ErrorResponse.send(context, 401, error);
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
            ErrorResponse.send(context, 401, error);
        } catch (BadRequestResponse error) {
            ErrorResponse.send(context, 400, error);
        }

    }

    public void joinGame(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            var game = gson.fromJson(context.body(), GameService.JoinGameRequest.class);
            gameService.joinGame(token, game);
            context.status(200);
            context.contentType("application/json");
            context.result("{}");

        } catch (UnauthorizedResponse error) {
            ErrorResponse.send(context, 401, error);
        }
        catch (BadRequestResponse error) {
            ErrorResponse.send(context, 400, error);
        }
        catch (ForbiddenResponse error) {
            ErrorResponse.send(context, 403, error);
        }
    }
}
