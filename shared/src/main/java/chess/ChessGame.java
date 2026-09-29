package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private  TeamColor teamTurn;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        if(board.getPiece(startPosition) == null) {
            return null;
        }

        ChessPiece piece = board.getPiece(startPosition);

        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);

        Collection<ChessMove> validMoves = new java.util.ArrayList<>();

        for (ChessMove move: potentialMoves) {

            ChessPiece savePiece = board.getPiece(move.getEndPosition());
            board.addPiece(startPosition, null);
            board.addPiece(move.getEndPosition(), piece);

            boolean isKingSafe = !isInCheck(piece.getTeamColor());

            board.addPiece(startPosition, piece);
            board.addPiece(move.getEndPosition(), savePiece);

            if(isKingSafe) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        ChessPosition startPosition = move.getStartPosition();
        ChessPiece piece = board.getPiece(startPosition);

        if(piece == null) {
            throw  new InvalidMoveException("No piece at starting position");
        }

        if(piece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException("You can't move your opponents piece piece");
        }

        Collection<ChessMove> validMoves = validMoves(startPosition);
        if(!validMoves.contains(move)) {
            throw new InvalidMoveException("Piece can't move there");
        }

        if(move.getPromotionPiece() != null) {
            piece = new ChessPiece(teamTurn, move.getPromotionPiece());
        }

        board.addPiece(startPosition, null);
        board.addPiece(move.getEndPosition(), piece);

        if(teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        }
        else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = findKing(teamColor);

        for(int row = 1; row <= 8; row++) {
            for(int col = 1; col <= 8; col++) {

                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if(piece == null || piece.getTeamColor() == teamColor) {
                    continue;
                }

                Collection<ChessMove> moves = piece.pieceMoves(board, position);

                for (ChessMove move: moves) {
                    if(move.getEndPosition().equals(kingPosition)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && hasNoLegalMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && hasNoLegalMoves(teamColor);
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    private ChessPosition findKing(TeamColor teamColor) {
        for(int row = 1; row <=8; row++) {
            for(int col =1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (board.getPiece(position) != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    return position;
                }
            }
        }
        return null;
    }

    private boolean hasNoLegalMoves(TeamColor teamColor) {
        for(int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if(piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(position);
                    if(moves != null && !moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }

    // I swear I added this stuff
}
