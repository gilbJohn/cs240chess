package database;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.SqlDataAccess;
import model.UserData;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class usersTest {
    @Test
    void createUser() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        String passwordHash = BCrypt.hashpw("test-password", BCrypt.gensalt());

        var expected = new UserData(
                UUID.randomUUID().toString(),
                passwordHash,
                "test@email.com"
        );

        dataAccess.createUser(expected);
        var actual = dataAccess.getUser(expected.username());

        assertEquals(expected, actual);
    }


}
