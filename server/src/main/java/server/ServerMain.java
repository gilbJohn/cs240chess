package server;

import java.util.UUID;

public class ServerMain {
    public static void main(String[] args) {
        var port = 8080;
        Server server = new Server();
        server.run(port);
        System.out.println("♕ 240 Chess Server running on port " + port);


    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public class MemoryDataAccess {
        public void clear() {
        }
    }
}
