package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class);

    @Test
    @DisplayName("Adds User and Auth")
    public void addsData() throws DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user1 = new UserData("username", "password", "email");
        var user2 = new UserData("dumb", "dumb", "dumb");

        var auth1 = service.register(user1);
        var auth2 = service.register(user2);


        assertEquals(user1, dataAccess.getUser(user1.username()));
        assertEquals(user2, dataAccess.getUser(user2.username()));

        assertNotNull(auth1.authToken());
        assertNotNull(auth2.authToken());

        assertEquals(auth1.authToken(), dataAccess.getAuth(auth1.authToken()).authToken());
        assertEquals(auth2.authToken(), dataAccess.getAuth(auth2.authToken()).authToken());
    }

    @Test
    @DisplayName("Username Taken")
    public void userNameTaken() throws ForbiddenResponse, DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user1 = new UserData("username", "password", "email");
        var user2 = new UserData("username", "dumbPassword", "dumbEmail");

        service.register(user1);
        assertThrows(ForbiddenResponse.class, () -> service.register(user2));

        assertEquals(user1, dataAccess.getUser(user1.username()));
    }

    @Test
    @DisplayName("SuccessFul Login")
    public void successfulLogin() throws UnauthorizedResponse, DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user = new UserData("username", "password", "email");

        service.register(user);

        var auth = assertDoesNotThrow(() -> service.login(user));

        assertEquals(auth.username(), user.username());
        assertNotNull(auth.authToken());
        assertEquals(auth, dataAccess.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Unauthorized Login")
    public void unauthorizedLogin() throws UnauthorizedResponse, DataAccessException {

        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user = new UserData("username", "password", "email");

        service.register(user);

        var fakeUser = new UserData("Dumb", "Dumb", "Dumb");

        assertThrows(UnauthorizedResponse.class, () -> service.login(fakeUser));

        assertNull(dataAccess.getUser(fakeUser.username()));
    }

    @Test
    @DisplayName("SuccessFul Logout")
    public void successfulLogout() throws ForbiddenResponse, DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user = new UserData("username", "password", "email");

        service.register(user);
        service.login(user);

        var auth = assertDoesNotThrow(() -> service.login(user));

        assertEquals(auth.username(), user.username());
        assertNotNull(auth.authToken());
        assertEquals(auth, dataAccess.getAuth(auth.authToken()));

        assertDoesNotThrow(() -> service.logOut((auth.authToken())));

        assertNull(dataAccess.getAuth(auth.authToken()));
    }
    @Test
    @DisplayName("Unauthorized Logout")
    public void unauthorizedLogout() throws ForbiddenResponse, DataAccessException {
        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user = new UserData("username", "password", "email");

        service.register(user);
        service.login(user);

        var auth = assertDoesNotThrow(() -> service.login(user));

        assertEquals(auth.username(), user.username());
        assertNotNull(auth.authToken());
        assertEquals(auth, dataAccess.getAuth(auth.authToken()));

        assertThrows(UnauthorizedResponse.class, () -> service.logOut("fake Token"));
    }

}
