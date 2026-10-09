package server;

import io.javalin.*;

import service.*;
import handler.*;
import dataaccess.*;


public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        var dataAccess = new MemoryDataAccess();
        var clearService = new ClearService(dataAccess);
        var clearHandler = new ClearHandler(clearService);

        javalin.delete("/db", clearHandler::clear);
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
