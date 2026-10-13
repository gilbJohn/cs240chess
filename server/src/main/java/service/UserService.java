package service;

import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {
    private final MemoryDataAccess dataAccess;

    public UserService(MemoryDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }


    public AuthData register(UserData user) throws BadRequestResponse {
        String token = UUID.randomUUID().toString();
        AuthData auth = new AuthData(token, user.username());
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestResponse("Need to put in valid info");
        }

        if (dataAccess.getUser(user.username()) != null) {
            throw new ForbiddenResponse("Username Already Taken");
        }
        dataAccess.createUser(user);
        dataAccess.createAuth(auth);
        return auth;
    }
}
