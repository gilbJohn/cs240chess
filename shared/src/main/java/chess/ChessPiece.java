package chess;

import java.awt.desktop.QuitEvent;
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
        List<ChessMove> moves = new ArrayList<>();

        // implement moves here;

        ChessPiece piece = board.getPiece(myPosition);

        //pieces :)

        //Main Guy
        if(piece.getPieceType() == PieceType.BISHOP) {
            int[][] directions = {
                    {1,1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1}
            };

            for(int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                while(true) {
                    row += direction[0];
                    col += direction[1];

                    if(row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    if(board.getPiece(newPosition) == null) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }

        if(piece.getPieceType() == PieceType.ROOK) {
            int[][] directions = {
                    {1,0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };

            for(int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                while(true) {
                    row += direction[0];
                    col += direction[1];

                    if(row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    if(board.getPiece(newPosition) == null) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    else{
                        break;
                    }
                }
            }

        }
        if(piece.getPieceType() == PieceType.QUEEN) {
            int[][] directions = {
                    {1,1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1},
                    {1,0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };

            for(int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                while(true) {
                    row += direction[0];
                    col += direction[1];

                    if(row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    ChessPosition newPosition = new ChessPosition(row, col);
                    if(board.getPiece(newPosition) == null) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                    else{
                        break;
                    }
                }
            }
        }
        if(piece.getPieceType() == PieceType.KING) {

            int[][] directions = {
                    {1,1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1},
                    {1,0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };

            for(int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                row += direction[0];
                col += direction[1];

                if(row < 1 || row > 8 || col < 1 || col > 8) {
                    continue;
                }

                ChessPosition newPosition = new ChessPosition(row, col);
                if(board.getPiece(newPosition) == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }
        if(piece.getPieceType() == PieceType.KNIGHT) {

            int[][] directions = {
                    {1,2},
                    {2, 1},
                    {-1, 2},
                    {2, -1},
                    {1,-2},
                    {-2, 1},
                    {-1, -2},
                    {-2, -1}
            };

            for(int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                row += direction[0];
                col += direction[1];

                if(row < 1 || row > 8 || col < 1 || col > 8) {
                    continue;
                }

                ChessPosition newPosition = new ChessPosition(row, col);
                if(board.getPiece(newPosition) == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

        }


        //What would Jesus Do?
        //Difficult but learning

        // Last one my guy
        if(piece.getPieceType() == PieceType.PAWN) {
            int direction;

            if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                direction = -1;
            } else {
                direction = 1;
            }

            // Forward and Double Forward

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            ChessPosition forward = new ChessPosition(row + direction, col);

            if(board.getPiece(forward ) == null) {
                // Check if promotion
                if(forward.getRow() == 8 || forward.getRow() == 1) {
                    moves.add(new ChessMove(myPosition, forward, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, forward, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, forward, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, forward, PieceType.KNIGHT));
                } else {
                    if (row == 7 || row == 2) {
                        // potentially need to add piece color logic to this
                        ChessPosition doubleForward = new ChessPosition(row+direction+direction, col);
                        if(board.getPiece(doubleForward) == null) {
                            moves.add(new ChessMove(myPosition, doubleForward, null));
                        }

                    }
                    moves.add(new ChessMove(myPosition, forward, null));
                }
            }


            // attack Right
            if(col < 8) {
                ChessPosition attackRight = new ChessPosition(row+direction, col + 1);

                if(board.getPiece(attackRight) != null && board.getPiece(attackRight).getTeamColor() != piece.getTeamColor()) {
                    if(attackRight.getRow() == 8 || attackRight.getRow() == 1) {
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.KNIGHT));
                    } else {
                        moves.add(new ChessMove(myPosition, attackRight, null));
                    }
                }
            }


            // attack left

            if(col > 1) {
                ChessPosition attackRight = new ChessPosition(row+direction, col - 1);

                if(board.getPiece(attackRight) != null && board.getPiece(attackRight).getTeamColor() != piece.getTeamColor()) {
                    if(forward.getRow() == 8 || forward.getRow() == 1) {
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, attackRight, PieceType.KNIGHT));
                    } else {
                        moves.add(new ChessMove(myPosition, attackRight, null));
                    }
                }
            }

        }


        return moves;
    }


    // Additional things


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

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
