package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    private ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = new ChessPiece[8][8];

        ChessPiece.PieceType[] pieceTypes = {
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK

        };

        // ====================== //
        // Black Pieces //
        // ====================== //
        int mainRowBlack  = 8;
        int mainColBlack = 1;
        for (ChessPiece.PieceType piece: pieceTypes) {
            ChessPosition mainPosition = new ChessPosition(mainRowBlack, mainColBlack);
            mainColBlack++;
            ChessPiece mainPiece = new ChessPiece(ChessGame.TeamColor.BLACK, piece);
            addPiece(mainPosition, mainPiece);
        }


        int pawnRowBlack = 7;
        for (int col = 1; col <= 8; col++) {
            ChessPosition pawnPosition = new ChessPosition(pawnRowBlack, col);
            ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            addPiece(pawnPosition, blackPawn);
        }

        // ====================== //
        // White Pieces //
        // ====================== //
        int mainRowWhite  = 1;
        int mainColWhite = 1;
        for (ChessPiece.PieceType piece: pieceTypes) {
            ChessPosition mainPosition = new ChessPosition(mainRowWhite, mainColWhite);
            mainColWhite++;
            ChessPiece mainPiece = new ChessPiece(ChessGame.TeamColor.WHITE, piece);
            addPiece(mainPosition, mainPiece);
        }


        int pawnRowWhite = 2;
        for (int col = 1; col <= 8; col++) {
            ChessPosition pawnPosition = new ChessPosition(pawnRowWhite, col);
            ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            addPiece(pawnPosition, whitePawn);
        }
    }
}
