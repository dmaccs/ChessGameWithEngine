package Pieces;

import Game.Board;
import Game.PieceType;
import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(boolean colour, int i) {
        super(colour, i);
        this.type = PieceType.King;
        this.value = 0;
    }

    public King(Piece piece) {
        super(piece);
    }

    public List<Square> possibleMoves(Board board, Square square) {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        int posX = square.x;
        int posY = square.y;
        if (!hasMoved) {
            int colourInt = 0;
            if (colour) {
                colourInt = 56;
            }
            if (board.getSquares().get(5 + colourInt).getPiece() == null) {
                if (board.getSquares().get(6 + colourInt).getPiece() == null) {
                    if (board.getSquares().get(7 + colourInt).getPiece() instanceof Rook) {
                        if (!board.getSquares().get(7 + colourInt).getPiece().hasMoved) {
                            squares.add(board.getSquares().get(6 + colourInt));
                        }
                    }
                }
            }
            if (board.getSquares().get(1 + colourInt).getPiece() == null) {
                if (board.getSquares().get(2 + colourInt).getPiece() == null) {
                    if (board.getSquares().get(3 + colourInt).getPiece() == null) {
                        if (board.getSquares().get(colourInt).getPiece() instanceof Rook) {
                            if (!board.getSquares().get(colourInt).getPiece().hasMoved) {
                                squares.add(board.getSquares().get(2 + colourInt));
                            }
                        }
                    }
                }
            }
        }
        if (posX - 1 > -1) {
            squares.add(board.getSquares().get(posX - 1 + posY * 8));
            if (posY - 1 > -1) {
                squares.add(board.getSquares().get(posX - 1 + (posY - 1)* 8));
            }
            if (posY + 1 < 8) {
                squares.add(board.getSquares().get(posX - 1 + (posY + 1)* 8));
            }
        }
        if (posX + 1 < 8) {
            squares.add(board.getSquares().get(posX + 1 + posY * 8));
            if (posY - 1 > -1) {
                squares.add(board.getSquares().get(posX + 1 + (posY - 1)* 8));
            }
            if (posY + 1 < 8) {
                squares.add(board.getSquares().get(posX + 1 + (posY + 1)* 8));
            }
        }
        if (posY + 1 < 8) {
            squares.add(board.getSquares().get(posX + (posY + 1)* 8));
        }
        if (posY - 1 > -1) {
            squares.add(board.getSquares().get(posX + (posY - 1)* 8));
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
            return "K";
        }
        return "k";
    }
}
