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

    // Piece identity
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

        ChessPiece piece = board.getPiece(myPosition);

        if (piece.getPieceType() == PieceType.BISHOP) {
            int[][] directions = {
                    {1, 1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1}
            };
            slidingMove(board, myPosition, directions, piece, moves);
        }

        if (piece.getPieceType() == PieceType.ROOK) {
            int[][] directions = {
                    {1, 0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };
            slidingMove(board, myPosition, directions, piece, moves);
        }

        if (piece.getPieceType() == PieceType.QUEEN) {
            int[][] directions = {
                    {1, 1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1},
                    {1, 0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };

            slidingMove(board, myPosition, directions, piece, moves);
        }
        // Single-destination moves: kings and knights.
        if (piece.getPieceType() == PieceType.KING) {

            int[][] directions = {
                    {1, 1},
                    {1, -1},
                    {-1, 1},
                    {-1, -1},
                    {1, 0},
                    {0, 1},
                    {-1, 0},
                    {0, -1}
            };
            singleMove(board, myPosition, directions, piece, moves);
        }

        if (piece.getPieceType() == PieceType.KNIGHT) {

            int[][] directions = {
                    {1, 2},
                    {2, 1},
                    {-1, 2},
                    {2, -1},
                    {1, -2},
                    {-2, 1},
                    {-1, -2},
                    {-2, -1}
            };
            singleMove(board, myPosition, directions, piece, moves);
        }

        if (piece.getPieceType() == PieceType.PAWN) {
            pawnMoves(board, myPosition, piece, moves);
            // attack Right
        }
        return moves;
    }

    // Shared movement helpers

    // Returns true when either coordinate is outside the board.
    private boolean isNotOnboard(int row, int col) {
        return row > 8 || row < 1 || col < 1 || col > 8;
    }

    // Walks each direction until the board edge or a blocking piece.
    private void slidingMove(ChessBoard board, ChessPosition myPosition, int[][] directions,
                             ChessPiece piece, Collection<ChessMove> moves) {
        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (true) {
                row += direction[0];
                col += direction[1];

                if (isNotOnboard(row, col)) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(row, col);
                if (board.getPiece(newPosition) == null) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                } else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    break;
                } else {
                    break;
                }
            }
        }
    }

    // Checks each offset once; intermediate squares do not affect these moves.
    private void singleMove(ChessBoard board, ChessPosition myPosition, int[][] directions,
                            ChessPiece piece, Collection<ChessMove> moves) {
        for (int[] direction : directions) {

            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            row += direction[0];
            col += direction[1];

            if (isNotOnboard(row, col)) {
                continue;
            }

            ChessPosition newPosition = new ChessPosition(row, col);
            if (board.getPiece(newPosition) == null) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            } else if (board.getPiece(newPosition).getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
    }

    private void pawnMoves(
            ChessBoard board,
            ChessPosition myPosition,
            ChessPiece piece,
            Collection<ChessMove> moves) {

        int direction;

        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            direction = -1;
        } else {
            direction = 1;
        }

        // Forward and Double Forward

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ChessPosition forward = new ChessPosition(row + direction, col);

        if (board.getPiece(forward) == null) {
            // Check if promotion
            if (forward.getRow() == 8 || forward.getRow() == 1) {
                moves.add(new ChessMove(myPosition, forward, PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, forward, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, forward, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, forward, PieceType.KNIGHT));
            } else {
                if (row == 7 || row == 2) {
                    // potentially need to add piece color logic to this
                    ChessPosition doubleForward = new ChessPosition(row + direction + direction, col);
                    if (board.getPiece(doubleForward) == null) {
                        moves.add(new ChessMove(myPosition, doubleForward, null));
                    }

                }
                moves.add(new ChessMove(myPosition, forward, null));
            }
        }

        for(int columnOffset: new int[]{-1, 1}) {
            int captureRow = row+direction;
            int captureCol = col+columnOffset;

            if(isNotOnboard(captureRow, captureCol)) {
                continue;
            }

            ChessPosition destination = new ChessPosition(captureRow, captureCol);
            ChessPiece target = board.getPiece(destination);

            if(target != null && target.getTeamColor() != piece.getTeamColor()) {
                if (forward.getRow() == 8 || forward.getRow() == 1) {
                    moves.add(new ChessMove(myPosition, destination, PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, destination, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, destination, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, destination, PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, destination, null));
                }
            }
        }
    }
    // Equality, hashing, and display
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
