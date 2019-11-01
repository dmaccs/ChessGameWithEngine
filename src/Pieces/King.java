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
            if(super.getSquare().getBoard().getSquares()[posX + 1][posY].getPiece() == null && super.getSquare().getBoard().getSquares()[posX + 3][posY].getPiece() instanceof Rook && !super.getSquare().getBoard().getSquares()[posX + 3][posY].getPiece().hasMoved) {
                squares.add(super.getSquare().getBoard().getSquares()[posX + 2][posY]);
            }
            if(super.getSquare().getBoard().getSquares()[posX - 1][posY].getPiece() == null && super.getSquare().getBoard().getSquares()[posX - 3][posY].getPiece() == null &&super.getSquare().getBoard().getSquares()[posX - 4][posY].getPiece() instanceof Rook && !super.getSquare().getBoard().getSquares()[posX - 4][posY].getPiece().hasMoved) {
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

    @Override
    public Boolean legalMove(int x, int y) {
        if (!hasMoved) {
            return ((super.legalMove(x, y) && (Math.abs(posX - x) <= 1 && Math.abs(posY - y) <= 1)) || ((posY - y) == 0 && Math.abs(posX - x) == 2));
        }
        return (super.legalMove(x, y) && (Math.abs(posX - x) <= 1 && Math.abs(posY - y) <= 1));
    }

    @Override
    public Integer[] getContacts(int x, int y) {
        if (!hasMoved) {
            if ((posX - x) == -2) {
                Integer[] positions = new Integer[3];
                positions[0] = (posX + 1) * 10 + y;
                positions[1] = (posX + 2) * 10 + y;
                positions[2] = (posX + 3) * 10 + y;
                return positions;
            }
            if ((posX - x) == 2) {
                Integer[] positions = new Integer[4];
                positions[0] = (posX - 1) * 10 + y;
                positions[1] = (posX - 2) * 10 + y;
                positions[2] = (posX - 3) * 10 + y;
                positions[3] = (posX - 4) * 10 + y;
                return positions;
            }
        }
        Integer[] positions = new Integer[1];
        positions[0] = x * 10 + y;
        return positions;
    }

    public String toString() {
        if (colour) {
            return "K";
        }
        return "k";
    }
}
