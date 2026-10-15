package handler;
import dataaccess.DataAccessException;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
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

            ctx.status(200);
            ctx.contentType("application/json");
            ctx.result(gson.toJson(result));
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
        try {
            var gson = new Gson();
            var user = gson.fromJson(context.body(), UserData.class);
            var result = userService.login(user);

            context.status(200);
            context.contentType("application/json");
            context.result(gson.toJson(result));
        } catch (UnauthorizedResponse error) {
            context.status(400);
            context.contentType("application/json");
            context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
        }
    }

    public void logout(@NotNull Context context) {
        try {
            var gson = new Gson();

            String token = context.header("authorization");
            userService.logOut(token);

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
    }
}
