package Pieces;

import Game.Board;
import Game.PieceType;
import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Boolean colour, int i) {
        super(colour, i);
        this.value = 1;
        this.type = PieceType.Pawn;
    }

    public Pawn(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves(Board board, Square square) {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        int posX = square.x;
        int posY = square.y;
        int i = 1;
        if (colour) {
            i = -i;
        }
        if ((posX + (posY + i) * 8) > -1 && (posX + (posY + i) * 8) < 64) {
            if ((posX + (posY + 2 * i) * 8) > -1 && (posX + (posY + 2 * i) * 8) < 64) {
                if(posY == 1 && !colour  && board.getSquares().get(posX + (posY + i) * 8).getPiece() == null ){
                    squares.add(board.getSquares().get(posX + (posY + 2 * i) * 8));
                }
                if(posY == 6 && colour  && board.getSquares().get(posX + (posY + i) * 8).getPiece() == null ){
                    squares.add(board.getSquares().get(posX + (posY + 2 * i) * 8));
                }
            }
        }
        if (posY + i < 8 && posY + i > -1) {
            squares.add(board.getSquares().get(posX + (posY + i) * 8));
        }
        if (board.en_passant != null) {
            if (board.en_passant / 8 == posY) {
                if (board.en_passant % 8 == posX - 1 && this.colour != board.getSquares().get(board.en_passant % 8 + posY * 8).getPiece().colour) {
                    if (board.getSquares().get(posX - 1 + (posY + i) * 8).getPiece() == null) {
                        possibleMoves.add(board.getSquares().get(posX - 1 + (posY + i) * 8));
                    }
                }
                if (board.en_passant % 8 == posX + 1 && this.colour != board.getSquares().get(board.en_passant % 8 + posY * 8).getPiece().colour) {
                    if (board.getSquares().get(posX + 1 + (posY + i) * 8).getPiece() == null) {
                        possibleMoves.add(board.getSquares().get(posX + 1 + (posY + i) * 8));
                    }
                }
            }
        }
        if (posX - 1 > -1 && posY + i > -1 && posY + i < 8) { // left
            if (board.getSquares().get(posX - 1 + (posY + i) * 8).getPiece() != null && board.getSquares().get(posX - 1 + (posY + i) * 8).getPiece().colour != colour) { // can take left
                possibleMoves.add(board.getSquares().get(posX - 1 + (posY + i) * 8));
            }
        }
        if (posX + 1 < 8 && posY + i > -1 && posY + i < 8) { // right
            if (board.getSquares().get(posX + 1 + (posY + i) * 8).getPiece() != null && board.getSquares().get(posX + 1 + (posY + i) * 8).getPiece().colour != colour) { // can take right
                possibleMoves.add(board.getSquares().get(posX + 1 + (posY + i) * 8));
            }
        }
        for (Square curSquare : squares) {
            if (curSquare.getPiece() == null) {
                possibleMoves.add(curSquare);
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
