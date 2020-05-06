package Pieces;

import Game.Board;
import Game.PieceType;
import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean colour, int i) {
        super(colour, i);
        this.value = 3;
        this.type = PieceType.Knight;
    }

    public Knight(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves(Board board, Square square) {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        int posX = square.x;
        int posY = square.y;
        if (posX - 2 > -1) {
            if (posY + 1 < 8) {
                squares.add(board.getSquares().get(posX - 2 + (posY + 1)* 8));
            }
            if (posY - 1 > -1) {
                squares.add(board.getSquares().get(posX - 2 + (posY - 1)* 8));
            }
        }
        if (posX + 2 < 8) {
            if (posY + 1 < 8) {
                squares.add(board.getSquares().get(posX + 2 + (posY + 1)* 8));
            }
            if (posY - 1 > -1) {
                squares.add(board.getSquares().get(posX + 2 + (posY - 1)* 8));
            }
        }
        if (posY + 2 < 8) {
            if (posX + 1 < 8) {
                squares.add(board.getSquares().get(posX + 1 + (posY + 2)* 8));
            }
            if (posX - 1 > -1) {
                squares.add(board.getSquares().get(posX - 1 + (posY + 2)* 8));
            }
        }
        if (posY - 2 > -1) {
            if (posX + 1 < 8) {
                squares.add(board.getSquares().get(posX + 1 + (posY - 2)* 8));
            }
            if (posX - 1 > -1) {
                squares.add(board.getSquares().get(posX - 1 + (posY - 2)* 8));
            }
        }
        for (Square curSquare : squares) {
            if (curSquare.getPiece() == null || curSquare.getPiece().colour != this.colour) {
                possibleMoves.add(curSquare);
            }
        }
        return possibleMoves;
    }

    public String toString() {
        if (colour) {
            return "N";
        }
        return "n";
    }
}
