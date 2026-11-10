package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class BoardRenderer {

    public static void draw(ChessBoard board, String color) {
        boolean blackPerspective = "BLACK".equals(color);

        System.out.println(blackPerspective ? "   h  g  f  e  d  c  b  a" : "   a  b  c  d  e  f  g  h");

        for (int row = 0; row < 8; row++) {
            int rank = blackPerspective ? row + 1 : 8 - row;
            System.out.print(rank + " ");

            for (int col = 0; col < 8; col++) {
                int file = blackPerspective ? 8 - col : col + 1;
                ChessPiece piece = board.getPiece(new ChessPosition(rank, file));
                String background = (rank + file) % 2 == 0
                        ? "\033[48;5;245m"  // Dark square
                        : "\033[48;5;252m"; // Light square

                System.out.print(background + "\033[30m" + " " + pieceSymbol(piece) + " " + "\033[0m");
            }

            System.out.println();
        }
    }

    private static char pieceSymbol(ChessPiece piece) {
        if (piece == null) {
            return ' ';
        }

        char symbol = switch (piece.getPieceType()) {
            case KNIGHT -> 'N';
            case BISHOP -> 'B';
            case KING -> 'K';
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case PAWN -> 'P';
        };
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            Character.toLowerCase(symbol);
        }


        return symbol;
    }
}
