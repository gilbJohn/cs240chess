package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;

import java.util.ArrayList;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(MemoryDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public static ArrayList<ChessGame> listGames(String token) {
        return null;
    }

    public static int createGame(String token) {
        return 0;
    }

    public static void joinGame(String token) {
    }
}
