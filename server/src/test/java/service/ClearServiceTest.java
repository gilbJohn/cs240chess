package service;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataaccess.MemoryDataAccess;
import dataaccess.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearServiceTest {
    @Test
    @DisplayName("Removes All Data")
    public void clearRemovesAllData() {

        var dataAccess = new MemoryDataAccess();
        var service = new ClearService(dataAccess);

        assertNull(dataAccess.getUser("alice"));
        assertNull(dataAccess.getAuth("test-token"));
        assertTrue(dataAccess.listGames().isEmpty());
    }

}
