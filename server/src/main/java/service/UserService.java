package service;

import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.UserData;

import java.util.Objects;
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

    public AuthData login(UserData user) throws UnauthorizedResponse {
        var username = user.username();

        var storedUser = dataAccess.getUser(username);

        if (storedUser == null
                || !Objects.equals(user.password(), storedUser.password())) {
            throw new UnauthorizedResponse("unauthorized");
        }

        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestResponse("Need to put in valid info");
        }

        var auth = new AuthData(UUID.randomUUID().toString(), username);

        dataAccess.createAuth(auth);
        return auth;
    }


    public void logOut(String token) {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }

        dataAccess.deleteAuth(token);
    }
}
