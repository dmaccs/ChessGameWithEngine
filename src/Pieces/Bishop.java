package Pieces;

import Game.Board;
import Game.PieceType;
import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(boolean colour, int i) {
        super(colour, i);
        this.value = 3.2;
        this.type = PieceType.Bishop;
    }

    public Bishop(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves(Board board, Square square) {
        List<Square> possibleMoves = new ArrayList<>();
        int posX = square.x;
        int posY = square.y;
        for (int i = posX + 1, j = posY + 1; i < 8 && j < 8; i++, j++) { //Down and Right Move
            Square curSquare = board.getSquares().get(i + j * 8);
            if (curSquare.getPiece() == null) {
                possibleMoves.add(curSquare);
            } else if (curSquare.getPiece().colour != this.colour) {
                possibleMoves.add(curSquare);
                break;
            } else {
                break;
            }

        }
        for (int i = posX - 1, j = posY + 1; i > -1 && j < 8; i--, j++) { // Down and Left Move
            Square curSquare = board.getSquares().get(i + j * 8);
            if (curSquare.getPiece() == null) {
                possibleMoves.add(curSquare);
            } else if (curSquare.getPiece().colour != this.colour) {
                possibleMoves.add(curSquare);
                break;
            } else {
                break;
            }

        }
        for (int i = posX + 1, j = posY - 1; i < 8 && j > -1; i++, j--) { // Up and Right Move
            Square curSquare = board.getSquares().get(i + j * 8);
            if (curSquare.getPiece() == null) {
                possibleMoves.add(curSquare);
            } else if (curSquare.getPiece().colour != this.colour) {
                possibleMoves.add(curSquare);
                break;
            } else {
                break;
            }

        }
        for (int i = posX - 1, j = posY - 1; i > -1 && j > -1; i--, j--) { // Up and Left Move
            Square curSquare = board.getSquares().get(i + j * 8);
            if (curSquare.getPiece() == null) {
                possibleMoves.add(curSquare);
            } else if (curSquare.getPiece().colour != this.colour) {
                possibleMoves.add(curSquare);
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
