package database;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.SqlDataAccess;
import model.AuthData;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class authTokensTest {

    @Test
    void createAndGetAuth() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testAuth = new AuthData(UUID.randomUUID().toString(), "user");

        assertDoesNotThrow(() -> dataAccess.createAuth(testAuth));

        assertEquals(testAuth, dataAccess.getAuth(testAuth.authToken()));

    }

    @Test
    void sameTokenTwice() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testAuth = new AuthData(UUID.randomUUID().toString(), "user");

        assertDoesNotThrow(() -> dataAccess.createAuth(testAuth));

        assertEquals(testAuth, dataAccess.getAuth(testAuth.authToken()));
        assertThrows(DataAccessException.class, ()->dataAccess.createAuth(testAuth));
    }

    @Test
    void getEmptyAuth() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        assertNull(dataAccess.getAuth(UUID.randomUUID().toString()));
    }

    @Test
    void deleteEmptyAuth() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        assertDoesNotThrow(()-> dataAccess.deleteAuth(UUID.randomUUID().toString()));
    }

    @Test
    void deleteAuth() throws DataAccessException {
        var dataAccess = new SqlDataAccess();

        var testAuth = new AuthData(UUID.randomUUID().toString(), "user");

        assertDoesNotThrow(() -> dataAccess.createAuth(testAuth));

        assertEquals(testAuth, dataAccess.getAuth(testAuth.authToken()));

        assertDoesNotThrow(()-> dataAccess.deleteAuth(testAuth.authToken()));

        assertNull(dataAccess.getAuth(testAuth.authToken()));

    }

}
