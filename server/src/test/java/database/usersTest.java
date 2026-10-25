package database;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.SqlDataAccess;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class usersTest {
    @Test
    void createUserAndGetUser() throws DataAccessException {
        var dataAccess = new SqlDataAccess();


        var expected = new UserData(
                UUID.randomUUID().toString(),
                "test-password",
                "test@email.com"
        );

        dataAccess.createUser(expected);
        var actual = dataAccess.getUser(expected.username());

        assertEquals(expected, actual);
    }

    @Test
    void createUserTwice() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var expected = new UserData(
                UUID.randomUUID().toString(),
                "test-password",
                "test@email.com"
        );

        dataAccess.createUser(expected);

        assertThrows(DataAccessException.class, () -> dataAccess.createUser(expected));
    }

    @Test
    void getUserNotExist() throws DataAccessException {
        var dataAccess = new SqlDataAccess();
        assertNull(dataAccess.getUser(UUID.randomUUID().toString()));
    }

}
