package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(boolean colour, int x, int y) {
        super(colour, x, y);
        this.value = 32;
    }

    public Bishop(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves() {
        List<Square> possibleMoves = new ArrayList<>();
        for (int i = posX + 1, j = posY + 1; i < 8 && j < 8; i++, j++) { //Down and Right Move
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour) {
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        for (int i = posX - 1, j = posY + 1; i > -1 && j < 8; i--, j++) { // Down and Left Move
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour) {
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        for (int i = posX + 1, j = posY - 1; i < 8 && j > -1; i++, j--) { // Up and Right Move
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour) {
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        for (int i = posX - 1, j = posY - 1; i > -1 && j > -1; i--, j--) { // Up and Left Move
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour) {
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        return possibleMoves;
    }


    public String toString() {
        if (colour) {
            return "B";
        }
        return "b";
    }

}
