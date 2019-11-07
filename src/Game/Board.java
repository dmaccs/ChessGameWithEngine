package Game;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Square[][] squares;
    public Integer[] en_passant = new Integer[2];
    public boolean turn = true;
    public List<Piece> whitePieces;
    public List<Piece> blackPieces;


    public Promotions promotion = Promotions.Queen;


    public Board() {
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.squares = new Square[8][8];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                squares[i][j] = new Square();
                if (j == 0 || j == 1) {
                    placeBlackPiece(i, j);
                }
                if (j == 6 || j == 7) {
                    placeWhitePiece(i, j);
                }
            }
        }
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                squares[i][j].setBoard(this);
                squares[i][j].x = i;
                squares[i][j].y = j;
                if (squares[i][j].getPiece() != null) {
                    squares[i][j].getPiece().setSquare(squares[i][j]);
                    squares[i][j].getPiece().setMoves( squares[i][j].getPiece().possibleMoves());
                }
            }
        }
    }

    public void placeBlackPiece(int i, int j) {
        if (j == 1) {
            squares[i][j].setPiece(new Pawn(false, i, j));
        }
        if (j == 0) {
            if (i == 0 || i == 7) {
                squares[i][j].setPiece(new Rook(false, i, j));
            } else {
                if (i == 1 || i == 6) {
                    squares[i][j].setPiece(new Knight(false, i, j));
                } else {
                    if (i == 2 || i == 5) {
                        squares[i][j].setPiece(new Bishop(false, i, j));
                    } else {
                        if (i == 3) {
                            squares[i][j].setPiece(new Queen(false, i, j));
                        } else {
                            if (i == 4) {
                                squares[i][j].setPiece(new King(false, i, j));
                            }
                        }
                    }
                }
            }
        }
    }

    public void placeWhitePiece(int i, int j) {
        if (j == 6) {
            squares[i][j].setPiece(new Pawn(true, i, j));
        } else {
            if (j == 7) {
                if (i == 0 || i == 7) {
                    squares[i][j].setPiece(new Rook(true, i, j));
                } else {
                    if (i == 1 || i == 6) {
                        squares[i][j].setPiece(new Knight(true, i, j));
                    } else {
                        if (i == 2 || i == 5) {
                            squares[i][j].setPiece(new Bishop(true, i, j));
                        } else {
                            if (i == 3) {
                                squares[i][j].setPiece(new Queen(true, i, j));
                            } else {
                                if (i == 4) {
                                    squares[i][j].setPiece(new King(true, i, j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Square[][] getSquares() {
        return this.squares;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                if (squares[l][k].getPiece() == null) {
                    output.append("[" + squares[l][k] + "0]");
                } else {
                    output.append("[" + squares[l][k].getPiece().colour.toString().substring(0, 1) + squares[l][k].getPiece() + "]");
                }
            }
            output.append("\r\n");
        }
        return output.toString();
    }
}