package service;

import dataaccess.DataAccess;

import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }


    public AuthData register(UserData user) throws BadRequestResponse, DataAccessException {
        String token = UUID.randomUUID().toString();
        AuthData auth = new AuthData(token, user.username());
        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestResponse("Need to put in valid info");
        }

        if (dataAccess.getUser(user.username()) != null) {
            throw new ForbiddenResponse("Username Already Taken");
        }

        String passwordHash = BCrypt.hashpw(user.password(), BCrypt.gensalt());

        UserData storedUser = new UserData(user.username(), passwordHash, user.email());

        dataAccess.createUser(storedUser);
        dataAccess.createAuth(auth);
        return auth;
    }

    public AuthData login(UserData user) throws UnauthorizedResponse, DataAccessException {
        if (user == null || user.username() == null || user.password() == null) {
            throw new BadRequestResponse("bad request");
        }

        var username = user.username();

        var storedUser = dataAccess.getUser(username);

        if (storedUser == null
                || !BCrypt.checkpw(user.password(), storedUser.password())) {
            throw new UnauthorizedResponse("unauthorized");
        }


        var auth = new AuthData(UUID.randomUUID().toString(), username);

        dataAccess.createAuth(auth);
        return auth;
    }


    public void logOut(String token) throws DataAccessException {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }

        dataAccess.deleteAuth(token);
    }
}
