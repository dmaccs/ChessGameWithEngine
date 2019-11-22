package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(boolean colour, int x, int y) {
        super(colour, x, y);
        this.value = 90;
    }

    public Queen(Piece piece) {
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
        for (int i = posX, j = posY + 1; j < 8; j++) { // Down
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
        for (int i = posX, j = posY - 1; j > -1; j--) { // Up
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
        for (int i = posX + 1, j = posY; i < 8; i++) { // Right
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
        for (int i = posX - 1, j = posY; i > -1; i--) { // Left
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
            return "Q";
        }
        return "q";
    }
}
