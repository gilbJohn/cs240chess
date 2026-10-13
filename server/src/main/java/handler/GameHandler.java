package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import service.GameService;

public class GameHandler {
    private final GameService gameService;
    public GameHandler(GameService gameService) {

        this.gameService = gameService;
    }

    public static void listGames(@NotNull Context context) {
        try {

        } catch (UnauthorizedResponse error){
            var gson = new Gson();
            String token = context.header("authorization");

            var result = GameService.listGames(token);
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(result));
        }
    }

    public void createGame(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            var result = GameService.createGame(token);
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(result));

        } catch (UnauthorizedResponse error) {

        }

    }

    public void joinGame(@NotNull Context context) {
        try {
            var gson = new Gson();
            String token = context.header("authorization");

            GameService.joinGame(token);
            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson("{}"));

        } catch (UnauthorizedResponse error) {

        }
    }
}
