package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;

import java.util.Map;

public class ErrorResponse {
    public static void send(Context context, int errorStatus, Exception error) {
        context.status(errorStatus);
        context.contentType("application/json");
        context.result(new Gson().toJson(Map.of("message", "Error: " + error.getMessage())));
    }
}
