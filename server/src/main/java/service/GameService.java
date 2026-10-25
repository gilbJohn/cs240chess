package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import model.GameData;

import java.util.Collection;
import java.util.Objects;

public class GameService {

    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Collection<GameData> listGames(String token) throws DataAccessException {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }
        return dataAccess.listGames();
    }
//
    //
    // CREATING The GAME
    public int createGame(String token, GameData game) throws DataAccessException {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }
        if (game == null || game.gameName() == null) {
            throw new BadRequestResponse("bad request");
        }


        int gameID = dataAccess.createGame(new GameData(0, null, null, game.gameName(), new ChessGame()));
        return gameID;
    }


    // JOINING THE GAME THIS IS SO HARD TO READ
    public void joinGame(String token, JoinGameRequest request) throws DataAccessException {
        if(token == null || dataAccess.getAuth(token) == null) {
            throw new UnauthorizedResponse("unauthorized");
        }

        if (request == null
                || (!Objects.equals(request.playerColor(), "WHITE")
                && !Objects.equals(request.playerColor(), "BLACK"))) {
            throw new BadRequestResponse("bad request");
        }

        int gameID = request.gameID();
        GameData game = dataAccess.getGame(gameID);
        String color = request.playerColor;
        String username = dataAccess.getAuth(token).username();

        if (game == null) {
            throw new BadRequestResponse("bad request");
        }

        if(Objects.equals(color, "WHITE")) {
            if(dataAccess.getGame(gameID).whiteUsername() != null){
               throw new ForbiddenResponse("Already Taken");
            }
            GameData updatedGame = new GameData(gameID, username, game.blackUsername(), game.gameName(), game.game());
            dataAccess.updateGame(updatedGame);
        }

        if (Objects.equals(color, "BLACK")) {
            if(dataAccess.getGame(gameID).blackUsername() != null){
                throw new ForbiddenResponse("Already Taken");
            }
            GameData updatedGame = new GameData(gameID, game.whiteUsername(), username, game.gameName(), game.game());
            dataAccess.updateGame(updatedGame);
        }

    }
    public record JoinGameRequest(String playerColor, int gameID){

    }
}
