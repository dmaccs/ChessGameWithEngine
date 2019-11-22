package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean colour, int x, int y) {
        super(colour, x, y);
        this.value = 30;
    }

    public Knight(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves() {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        if (posX - 2 > -1) {
            if (posY + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 2][posY + 1]);
            }
            if (posY - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 2][posY - 1]);
            }
        }
        if (posX + 2 < 8) {
            if (posY + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 2][posY + 1]);
            }
            if (posY - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 2][posY - 1]);
            }
        }
        if (posY + 2 < 8) {
            if (posX + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 1][posY + 2]);
            }
            if (posX - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 1][posY + 2]);
            }
        }
        if (posY - 2 > -1) {
            if (posX + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 1][posY - 2]);
            }
            if (posX - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 1][posY - 2]);
            }
        }
        for (Square square : squares) {
            if (square.getPiece() == null || square.getPiece().colour != this.colour) {
                possibleMoves.add(square);
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
