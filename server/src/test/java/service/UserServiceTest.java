package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

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
    public void userNameTaken() throws ForbiddenResponse {
        var dataAccess = new MemoryDataAccess();
        var service = new UserService(dataAccess);

        var user1 = new UserData("username", "password", "email");
        var user2 = new UserData("username", "dumbPassword", "dumbEmail");

        service.register(user1);
        assertThrows(ForbiddenResponse.class, () -> service.register(user2));

        assertEquals(user1, dataAccess.getUser(user1.username()));
    }

}
