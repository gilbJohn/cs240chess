package handler;
import dataaccess.DataAccessException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import service.ClearService;
import service.UserService;
import io.javalin.http.Context;
import com.google.gson.Gson;

import java.util.Map;

public class UserHandler {
    private final UserService userService;
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void register(Context ctx) {
        try {
            var gson = new Gson();
            var user = gson.fromJson(ctx.body(), UserData.class);
            var result = userService.register(user);

            ctx.contentType("application/json");
            ctx.result(gson.toJson(Map.of("username", result.username())));
        }
        catch (BadRequestResponse error) {
            ctx.status(400);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
        catch (ForbiddenResponse error) {
            ctx.status(403);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }

    }

    public void login(@NotNull Context context) {
    }

    public void logout(@NotNull Context context) {
    }
}
