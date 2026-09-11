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

        // -- is going left up diagonal 1:1
        // -+ is going right up diagonal 1:8
        // +- going left down 8:1
        // ++ going right down 8:8
        if (piece.getPieceType() == PieceType.BISHOP) {
            List<ChessMove> moves = new ArrayList<>();

            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (row > 1 && col > 1) {
                row--;
                col--;

                ChessPosition newPosition = new ChessPosition(row, col);

                moves.add(new ChessMove(myPosition, newPosition, null));

            }

            row = myPosition.getRow();
            col = myPosition.getColumn();

            while (row > 1 && col > 8) {
                row--;
                col++;

                ChessPosition newPosition = new ChessPosition(row, col);

                moves.add(new ChessMove(myPosition, newPosition, null));

            }

            row = myPosition.getRow();
            col = myPosition.getColumn();

            while (row > 8 && col > 1) {
                row++;
                col--;

                ChessPosition newPosition = new ChessPosition(row, col);

                moves.add(new ChessMove(myPosition, newPosition, null));

            }

            row = myPosition.getRow();
            col = myPosition.getColumn();

            while (row > 8 && col > 8) {
                row++;
                col++;

                ChessPosition newPosition = new ChessPosition(row, col);

                moves.add(new ChessMove(myPosition, newPosition, null));

            }


            return moves;
        }
        if (piece.getPieceType() == PieceType.PAWN) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        if (piece.getPieceType() == PieceType.KING) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        if (piece.getPieceType() == PieceType.ROOK) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        if (piece.getPieceType() == PieceType.QUEEN) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        if (piece.getPieceType() == PieceType.KNIGHT) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        return List.of();
    }
}
