package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryDataAccess implements DataAccess{
    private final Map<String, UserData> users = new HashMap<>();
    private final Map<Integer, GameData> games = new HashMap<>();
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

    public void createGame(GameData game){
        games.put(game.gameID(), game);
    }

    public Collection<GameData> listGames() {
        return new ArrayList<>(games.values());
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public void updateGame() {

    }

    public void createAuth(AuthData auth) {
        authTokens.put(auth.authToken(), auth);
    }

    public AuthData getAuth(String auth) {
        return authTokens.get(auth);
    }
}
