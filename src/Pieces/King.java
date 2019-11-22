package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(boolean colour, int x, int y) {
        super(colour, x, y);
    }

    public King(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves() {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        if(!hasMoved){
            if(super.getSquare().getBoard().getSquares()[posX + 1][posY].getPiece() == null && super.getSquare().getBoard().getSquares()[posX + 3][posY].getPiece() instanceof Rook && !super.getSquare().getBoard().getSquares()[posX + 3][posY].getPiece().hasMoved && !super.getSquare().getBoard().chessGame.getCheck()) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 2][posY]);
            }
            if(super.getSquare().getBoard().getSquares()[posX - 1][posY].getPiece() == null && super.getSquare().getBoard().getSquares()[posX - 3][posY].getPiece() == null &&super.getSquare().getBoard().getSquares()[posX - 4][posY].getPiece() instanceof Rook && !super.getSquare().getBoard().getSquares()[posX - 4][posY].getPiece().hasMoved && !super.getSquare().getBoard().chessGame.getCheck()) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 2][posY]);
            }
        }
        if (posX - 1 > -1) {
            squares.add(super.getSquare().getBoard().getSquares()[posX - 1][posY]);
            if (posY - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 1][posY - 1]);
            }
            if (posY + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX - 1][posY + 1]);
            }
        }
        if (posX + 1 < 8) {
            squares.add(super.getSquare().getBoard().getSquares()[posX + 1][posY]);
            if (posY - 1 > -1) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 1][posY - 1]);
            }
            if (posY + 1 < 8) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 1][posY + 1]);
            }
        }
        if (posY + 1 < 8) {
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY + 1]);
        }
        if (posY - 1 > -1) {
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY - 1]);
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
            return "K";
        }
        return "k";
    }
}
