package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Boolean colour, int x, int y) {
        super(colour, x, y);
    }

    public Pawn(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves() {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        int i = 1;
        if (colour) {
            i = -i;
        }
        if (!this.hasMoved) {
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY + 2 * i]);
        }
        if (posY + i < 8 && posY + i > -1) {
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY + i]);
        }
        if (super.getSquare().getBoard().en_passant[1] != null && super.getSquare().getBoard().en_passant[1] == super.getSquare().y) {
            if (super.getSquare().getBoard().en_passant[0] == super.getSquare().x - 1 && this.colour != super.getSquare().getBoard().getSquares()[super.getSquare().getBoard().en_passant[0]][super.getSquare().y].getPiece().colour) {
                if (super.getSquare().getBoard().getSquares()[super.getSquare().x - 1][posY + i].getPiece() == null) {
                    possibleMoves.add(super.getSquare().getBoard().getSquares()[posX - 1][posY + i]);
                }
            }
            if (super.getSquare().getBoard().en_passant[0] == super.getSquare().x + 1 && this.colour != super.getSquare().getBoard().getSquares()[super.getSquare().getBoard().en_passant[0]][super.getSquare().y].getPiece().colour) {
                if (super.getSquare().getBoard().getSquares()[super.getSquare().x + 1][posY + i].getPiece() == null) {
                    possibleMoves.add(super.getSquare().getBoard().getSquares()[posX + 1][posY + i]);
                }
            }
        }
        if(posX - 1 > -1 && posY + i > -1 && posY + i < 8) {
            if (super.getSquare().getBoard().getSquares()[posX - 1][posY + i].getPiece() != null && super.getSquare().getBoard().getSquares()[posX - 1][posY + i].getPiece().colour != super.getSquare().getPiece().colour) { // can take left
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX - 1][posY + i]);
            }
        }
        if(posX + 1 < 8 && posY + i > -1 && posY + i < 8) {
            if (super.getSquare().getBoard().getSquares()[posX + 1][posY + i].getPiece() != null && super.getSquare().getBoard().getSquares()[posX + 1][posY + i].getPiece().colour != super.getSquare().getPiece().colour) { // can take right
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX + 1][posY + i]);
            }
        }
        for (Square square : squares) {
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            }
        }
        return possibleMoves;
    }

    public String toString() {
        if (colour) {
            return "P";
        }
        return "p";
    }
}
