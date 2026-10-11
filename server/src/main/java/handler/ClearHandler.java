package handler;

import dataaccess.DataAccessException;
import service.ClearService;
import io.javalin.http.Context;
import com.google.gson.Gson;

import java.util.Map;

public class ClearHandler {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }
    public void clear(Context ctx) throws DataAccessException {

        try {
            clearService.clear();
            ctx.status(200);
            ctx.result("{}");
        }
        catch (DataAccessException exception) {
            ctx.status(500);
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + exception.getMessage())));
        }
    }
}
