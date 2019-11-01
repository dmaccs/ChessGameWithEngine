package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(boolean colour, int x, int y) {
        super(colour, x, y);
    }
    public Bishop(Piece piece){
        super(piece);
    }
    public List<Square> possibleMoves(){
        List<Square> possibleMoves = new ArrayList<>();
        for(int i = posX + 1, j = posY + 1; i < 8 && j < 8; i++, j++){ //Down and Right Moves
                Square square = super.getSquare().getBoard().getSquares()[i][j];
                if(square.getPiece() == null){
                    possibleMoves.add(square);
                } else if (square.getPiece().colour != this.colour){
                    possibleMoves.add(square);
                    break;
                } else {
                    break;
                }

            }
        for(int i = posX - 1, j = posY + 1; i > -1 && j < 8; i--, j++){ // Down and Left Moves
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if(square.getPiece() == null){
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour){
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        for(int i = posX + 1, j = posY - 1; i < 8 && j > - 1; i++, j--){ // Up and Right Moves
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if(square.getPiece() == null){
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour){
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        for(int i = posX - 1, j = posY - 1; i > -1 && j > -1; i--, j--){ // Up and Left Moves
            Square square = super.getSquare().getBoard().getSquares()[i][j];
            if(square.getPiece() == null){
                possibleMoves.add(square);
            } else if (square.getPiece().colour != this.colour){
                possibleMoves.add(square);
                break;
            } else {
                break;
            }

        }
        return possibleMoves;
        }

    @Override
    public Integer[] getContacts(int x, int y) {
        Integer[] positions;
        int i = x;
        int j = y;
        int k = 0;
        positions = new Integer[Math.abs(posX - x)];
        if (x < posX && y < posY) {
            while (posX - i > 0) {
                positions[positions.length - 1 - k] = i * 10 + j;
                i++;
                j++;
                k++;
            }
        } else if (x < posX && y > posY) {
            while (posX - i > 0) {
                positions[positions.length - 1 - k] = i * 10 + j;
                i++;
                j--;
                k++;
            }
        } else if (x > posX && y < posY) {
            while (posX - i < 0) {
                positions[positions.length - 1 - k] = i * 10 + j;
                i--;
                j++;
                k++;
            }
        } else if (x > posX && y > posY) {
            while (posX - i < 0) {
                positions[positions.length - 1 - k] = i * 10 + j;
                i--;
                j--;
                k++;
            }
        }
        return positions;
    }

    @Override
    public Boolean legalMove(int x, int y) {
        return (super.legalMove(x,y) && Math.abs(posX - x) == Math.abs(posY - y));
    }

    public String toString() {
        if (colour) {
            return "B";
        }
        return "b";
    }

}
