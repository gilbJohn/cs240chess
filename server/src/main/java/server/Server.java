package server;

import com.google.gson.Gson;
import io.javalin.*;

import model.UserData;
import service.*;
import handler.*;
import dataaccess.*;
import dataaccess.sql.SqlDataAccess;
import io.javalin.http.Context;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        DataAccess dataAccess;

        //storage;
        try {
            dataAccess = new SqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException("Could not initialize database", e);
        }


        // clear logic
        var clearService = new ClearService(dataAccess);
        var clearHandler = new ClearHandler(clearService);
        javalin.delete("/db", clearHandler::clear);

        // Register, login, logout
        var authService = new UserService(dataAccess);
        var authHandler = new UserHandler(authService);
        javalin.post("/user", authHandler::register);

        javalin.post("/session", authHandler::login);
        javalin.delete("/session", authHandler::logout);


        //game endpoints
        var gameService = new GameService(dataAccess);
        var gameHandler = new GameHandler(gameService);

        javalin.get("/game", gameHandler::listGames);
        javalin.post("/game", gameHandler::createGame);
        javalin.put("/game", gameHandler::joinGame);
    }
    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

        public void clear() {
        }

}
