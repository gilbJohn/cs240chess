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

    public void createUser(UserData user) {
        users.put(user.username(), user);
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

//    public void createGame(UserData user){
//
//    }

    public Map<String, GameData> listGames() {
        return games;
    }

//    public GameData getGame() {
//
//        return null;
//    }

//    public void updateGame() {
//
//    }

//    public void createAuth(String token) {
//
//    }

    public AuthData getAuth(String auth) {
        return authTokens.get(auth);
    }
}
