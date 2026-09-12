package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
       return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        int row = myPosition.getRow();
        int col = myPosition.getColumn();




        List<ChessMove> moves = new ArrayList<>();
        // -- is going left up diagonal 1:1
        // -+ is going right up diagonal 1:8
        // +- going left down 8:1
        // ++ going right down 8:8
        if (piece.getPieceType() == PieceType.BISHOP) {
            int[][] directions = {
                    {-1,-1},
                    {1,-1},
                    {-1,1},
                    {1,1}
            };

            for (int[] direction : directions){
                row = myPosition.getRow();
                col = myPosition.getColumn();

                boolean blocked = false;

                while (!blocked) {

                    row += direction[0];
                    col += direction[1];

                    if (row > 8 || row < 1 || col > 8 || col < 1) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);


                    if(board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }

                    else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        moves.add(newMove);
                        blocked = true;
                    }

                    else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                        blocked = true;
                    }
                }


            }
            return moves;
        }
        if (piece.getPieceType() == PieceType.PAWN) {
            int direction;
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                direction = 1;
            }
            else {
                direction = -1;
            }
            row = myPosition.getRow();
            col = myPosition.getColumn();

            ChessPosition newPosition = new ChessPosition(row + direction, col);
            ChessPosition attackLeft = new ChessPosition(row + direction, col + 1);
            ChessPosition attackRight = new ChessPosition(row + direction, col - 1);


            if (board.getPiece(newPosition) == null ) {
                if (newPosition.getRow() == 8 || newPosition.getRow() == 1) {
                    ChessMove forwardMoveKnight = new ChessMove(myPosition,newPosition, PieceType.KNIGHT);
                    ChessMove forwardMoveQueen = new ChessMove(myPosition, newPosition, PieceType.QUEEN);
                    ChessMove forwardMoveRook = new ChessMove(myPosition, newPosition, PieceType.ROOK);
                    ChessMove forwardMoveBishop = new ChessMove(myPosition, newPosition, PieceType.BISHOP);
                }
                else  {
                    ChessMove forwardMove = new ChessMove(myPosition, newPosition, null);
                }
            }
            if (board.getPiece(attackLeft).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {

            }
            if (board.getPiece(attackRight).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {

            }
            if ((myPosition.getRow() == 7 && piece.getTeamColor() == ChessGame.TeamColor.WHITE) || (myPosition.getRow() == 2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE)) {

            }


          return null;
        }
        if (piece.getPieceType() == PieceType.KING) {
            int[][] directions = {
                    {-1,-1},
                    {1,-1},
                    {-1,1},
                    {1,1},
                    {-1,0},
                    {0,-1},
                    {1,0},
                    {0,1}
            };

            for (int[] direction : directions){
                row = myPosition.getRow();
                col = myPosition.getColumn();

                    row += direction[0];
                    col += direction[1];

                    if (row > 8 || row < 1 || col > 8 || col < 1) {
                        continue;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);


                    if(board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }

                    else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        moves.add(newMove);
                    }
            }
            return moves;
        }
        // Rook Logic
        if (piece.getPieceType() == PieceType.ROOK) {
            int[][] directions = {
                    {-1, 0},
                    {0,-1},
                    {1,0},
                    {0,1}
            };

            for (int[] direction : directions){
                row = myPosition.getRow();
                col = myPosition.getColumn();

                boolean blocked = false;

                while (!blocked) {

                    row += direction[0];
                    col += direction[1];

                    if (row > 8 || row < 1 || col > 8 || col < 1) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);


                    if(board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }

                    else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        moves.add(newMove);
                        blocked = true;
                    }

                    else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                        blocked = true;
                    }
                }


            }


            return moves;
        }
        if (piece.getPieceType() == PieceType.QUEEN) {
            int[][] directions = {
                    {-1,-1},
                    {1,-1},
                    {-1,1},
                    {1,1},
                    {-1,0},
                    {0,-1},
                    {1,0},
                    {0,1}
            };

            for (int[] direction : directions){
                row = myPosition.getRow();
                col = myPosition.getColumn();

                boolean blocked = false;

                while (!blocked) {

                    row += direction[0];
                    col += direction[1];

                    if (row > 8 || row < 1 || col > 8 || col < 1) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    ChessMove newMove = new ChessMove(myPosition, newPosition, null);


                    if(board.getPiece(newPosition) == null) {
                        moves.add(newMove);
                    }

                    else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        moves.add(newMove);
                        blocked = true;
                    }

                    else if (board.getPiece(newPosition).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                        blocked = true;
                    }
                }
            }
            return moves;
        }
        if (piece.getPieceType() == PieceType.KNIGHT) {
            int[][] directions = {
                    {1,2},
                    {2,1},
                    {-1,2},
                    {2,-1},
                    {1,-2},
                    {-2,1},
                    {-1,-2},
                    {-2,-1}
            };

            for (int[] direction : directions){
                row = myPosition.getRow();
                col = myPosition.getColumn();

                row += direction[0];
                col += direction[1];

                if (row > 8 || row < 1 || col > 8 || col < 1) {
                    continue;
                }

                ChessPosition newPosition = new ChessPosition(row, col);
                ChessMove newMove = new ChessMove(myPosition, newPosition, null);


                if(board.getPiece(newPosition) == null) {
                    moves.add(newMove);
                }

                else if (board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(newMove);
                }
            }
            return moves;
        }
        return List.of();
    }
}
