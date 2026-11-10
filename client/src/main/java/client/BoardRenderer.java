package client;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

public class BoardRenderer {

    public static void draw(ChessBoard board, String color) {
        boolean blackPerspective = "BLACK".equals(color);

        System.out.println(blackPerspective ? "  h g f e d c b a" : "  a b c d e f g h");

        for (int row = 0; row < 8; row++) {
            int rank = blackPerspective ? row + 1 : 8 - row;
            System.out.print(rank + " ");

            for (int col = 0; col < 8; col++) {
                int file = blackPerspective ? 8 - col : col + 1;
                ChessPiece piece = board.getPiece(new ChessPosition(rank, file));
                System.out.print(piece == null ? ". " : "X ");
            }

            System.out.println();
        }
    }
}
