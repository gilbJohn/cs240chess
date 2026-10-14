package service;

import chess.ChessGame;
import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;

import java.util.Collection;
import java.util.Objects;

public class GameService {

    private final MemoryDataAccess dataAccess;
    public GameService(MemoryDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Collection<GameData> listGames(String token) {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }
        return dataAccess.listGames();
    }
//
    //
    // CREATING The GAME
    public int createGame(String token, GameData game) {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }
        if (game == null || game.gameName() == null) {
            throw new BadRequestResponse("bad request");
        }

        int ID = dataAccess.gameID++;

        dataAccess.createGame(new GameData(ID, null, null, game.gameName(), new ChessGame()));
        return ID;
    }


    // JOINING THE GAME THIS IS SO HARD TO READ
    public void joinGame(String token, joinGameRequest request) {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }

        if (request == null
                || (!Objects.equals(request.playerColor(), "WHITE")
                && !Objects.equals(request.playerColor(), "BLACK"))) {
            throw new BadRequestResponse("bad request");
        }

        int ID = request.gameID();
        GameData game = dataAccess.getGame(ID);
        String color = request.playerColor;
        String username = dataAccess.getAuth(token).username();

        if (game == null) {
            throw new BadRequestResponse("bad request");
        }

        if(Objects.equals(color, "WHITE")) {
            if(dataAccess.getGame(ID).whiteUsername() != null){
               throw new ForbiddenResponse("Already Taken");
            }
            GameData updatedGame = new GameData(ID, username, game.blackUsername(), game.gameName(), game.game());
            dataAccess.updateGame(updatedGame);
        }

        if (Objects.equals(color, "BLACK")) {
            if(dataAccess.getGame(ID).blackUsername() != null){
                throw new ForbiddenResponse("Already Taken");
            }
            GameData updatedGame = new GameData(ID, game.whiteUsername(), username, game.gameName(), game.game());
            dataAccess.updateGame(updatedGame);
        }

    }
    public record joinGameRequest (String playerColor, int gameID){

    }
}
