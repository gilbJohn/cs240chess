package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryDataAccess implements DataAccess{
    private final Map<String, UserData> users = new HashMap<>();
    private final Map<String, GameData> games = new HashMap<>();
    private final Map<String, AuthData> authTokens = new HashMap<>();

    @Override
    public void clear() {
        users.clear();
        games.clear();
        authTokens.clear();
    }
}
