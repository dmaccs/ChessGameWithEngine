package Game;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Square[][] squares;
    public Integer[] en_passant = new Integer[2];
    public List<Piece> whitePieces;
    public List<Piece> blackPieces;
    public List<Piece> allPieces;
    public ChessGame chessGame;

    public Promotions promotion = Promotions.Queen;

    public Board() {
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.allPieces = new ArrayList<>();
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
                    if(squares[i][j].getPiece().colour){
                        whitePieces.add(squares[i][j].getPiece());
                        allPieces.add(squares[i][j].getPiece());
                    } else {
                        blackPieces.add(squares[i][j].getPiece());
                        allPieces.add(squares[i][j].getPiece());
                    }
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

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    //  public void makeMove(int curX, int curY, int finX, int finY) {
//        if(getSquares()[curX][curY].getPiece() != null && getSquares()[curX][curY].getPiece().colour == turn) {
//            if (!checkMate) {
//                if (curX == finX && curY == finY) {
//                    return;
//                }
//                if (validMove(curX, curY, finX, finY)) {
//                    System.out.println("player.makeMove(" + curX + "," + curY + "," + finX + "," + finY + ");");
//                    // System.out.println(en_passant[0] +  "," + en_passant[1]);
//                    if (inCheck(turn)) {
//                        checkMate();
//                    }
//                }
//            }
//            if (checkMate) {
//                System.out.println("Checkmate!");
//            }
//        }
//    }
//
//    public void doMove(int curX, int curY, int finX, int finY) {
//        if (getSquares()[curX][curY].getPiece() instanceof Pawn && en_passant[0] != null) {
//            if (en_passant[0] == finX && Math.abs(curX - en_passant[0]) == 1 && en_passant[1] == curY) {
//                getSquares()[en_passant[0]][en_passant[1]].setPiece(null);
//            }
//        }
//        if (getSquares()[curX][curY].getPiece() instanceof Pawn && Math.abs(curY - finY) == 2) {
//            en_passant[0] = finX;
//            en_passant[1] = finY;
//        } else {
//            en_passant[0] = null;
//            en_passant[1] = null;
//        }
//        if (getSquares()[curX][curY].getPiece() instanceof King && Math.abs(curX - finX) == 2) { //castling
//            if (curX - finX > 0) {
//                getSquares()[3][curY].setPiece(getSquares()[0][curY].getPiece());
//                getSquares()[3][curY].getPiece().setSquare(getSquares()[3][curY]);
//                getSquares()[3][curY].getPiece().updatePiece(3, curY);
//                getSquares()[0][curY].setPiece(null);
//            } else {
//                getSquares()[5][curY].setPiece(getSquares()[7][curY].getPiece());
//                getSquares()[5][curY].getPiece().setSquare(getSquares()[5][curY]);
//                getSquares()[5][curY].getPiece().updatePiece(5, curY);
//                getSquares()[7][curY].setPiece(null);
//            }
//        }
//        if (getSquares()[curX][curY].getPiece() instanceof Pawn && (finY == 7 || finY == 0)) { //promotion
//            boolean colour = getSquares()[curX][curY].getPiece().colour;
//            switch (promotion) {
//                case Queen:
//                    getSquares()[curX][curY].setPiece(new Queen(colour, curX, curY));
//                    break;
//                case Rook:
//                    getSquares()[curX][curY].setPiece(new Rook(colour, curX, curY));
//                    break;
//                case Bishop:
//                    getSquares()[curX][curY].setPiece(new Bishop(colour, curX, curY));
//                    break;
//                case Knight:
//                    getSquares()[curX][curY].setPiece(new Knight(colour, curX, curY));
//                    break;
//            }
//        }
//        getSquares()[finX][finY].setPiece(getSquares()[curX][curY].getPiece()); // update board
//        getSquares()[finX][finY].getPiece().setSquare(getSquares()[finX][finY]);
//        getSquares()[finX][finY].getPiece().updatePiece(finX, finY);
//        getSquares()[curX][curY].setPiece(null);
//        turn = !turn;
//        updatePieces();
//
//    }
//
//    public boolean validMove(int curX, int curY, int finX, int finY) {
//        Piece piece = getSquares()[curX][curY].getPiece();
//        boolean result = false;
//        if (piece != null) {
//            if (piece.moves().contains(getSquares()[finX][finY])) {
//                result = true;
//            }
//            if (result) {
//                piece = createPiece(getSquares()[finX][finY].getPiece());
//                Integer[] enPassant = new Integer[2];
//                Integer x = en_passant[0];
//                Integer y = en_passant[1];
//                enPassant[0] = x;
//                enPassant[1] = y;
//                boolean hasMove = piece.hasMoved;
//                doMove(curX, curY, finX, finY);
//                if (inCheck(getSquares()[finX][finY].getPiece().colour)) {
//                    result = false;
//                    revertMove(curX, curY, finX, finY, piece, enPassant);
//                    board.getSquares()[curX][curY].getPiece().hasMoved = hasMoved;
//                }
//            }
//        }
//        return result;
//    }
//
//    public void revertMove(int curX, int curY, int finX, int finY, Piece piece, Integer[] enPassant) {
//        getSquares()[curX][curY].setPiece(getSquares()[finX][finY].getPiece());
//        getSquares()[curX][curY].getPiece().setSquare(getSquares()[curX][curY]);
//        getSquares()[finX][finY].setPiece(piece);
//        if (piece != null) {
//            piece.setSquare(getSquares()[finX][finY]);
//        }
//        getSquares()[curX][curY].getPiece().updatePiece(curX, curY);
//        turn = !turn;
//        en_passant = enPassant;
//    }
//
//    public Piece createPiece(Piece piece) {
//        if (piece != null) {
//            switch (piece.toString()) {
//                case "R":
//                    return new Rook(piece);
//                case "r":
//                    return new Rook(piece);
//                case "N":
//                    return new Knight(piece);
//                case "n":
//                    return new Knight(piece);
//                case "B":
//                    return new Bishop(piece);
//                case "b":
//                    return new Bishop(piece);
//                case "Q":
//                    return new Queen(piece);
//                case "q":
//                    return new Queen(piece);
//                case "K":
//                    return new King(piece);
//                case "k":
//                    return new King(piece);
//                case "P":
//                    return new Pawn(piece);
//                case "p":
//                    return new Pawn(piece);
//            }
//        }
//        return null;
//    }
//
//    public Boolean validMoveCheckBoard(int curX, int curY, int finX, int finY) {
//        updatePieces();
//        Piece piece = getSquares()[curX][curY].getPiece();
//        if (piece != null) {
//            return piece.moves().contains(getSquares()[finX][finY]);
//        }
//        return false;
//    }
//
//    public boolean inCheck(boolean turn) {
//        int kingX = 10;
//        int kingY = 10;
//        boolean result = false;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (getSquares()[i][j].getPiece() instanceof King && getSquares()[i][j].getPiece().colour == turn) {
//                    kingX = i;
//                    kingY = j;
//                    i = j = 8;
//                    break;
//                }
//            }
//        }
//        if (kingX == 10 || kingY == 10) {
//            throw new IndexOutOfBoundsException();
//        }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (getSquares()[i][j].getPiece() != null) {
//                    result = result || validMoveCheckBoard(i, j, kingX, kingY);
//                }
//            }
//        }
//        return result;
//    }
//
//    public void updatePieces() {
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (getSquares()[i][j].getPiece() != null) {
//                    getSquares()[i][j].getPiece().setMoves(getSquares()[i][j].getPiece().possibleMoves());
//                }
//            }
//        }
//    }
//
//    public void checkMate() {
//        updatePieces();
//        for (int i = 0; i < 7; i++) {
//            for (int j = 0; j < 7; j++) {
//                if (getSquares()[i][j].getPiece() != null && getSquares()[i][j].getPiece().colour == turn) {
//                    List<Square> squares = getSquares()[i][j].getPiece().possibleMoves();
//                    for (Square square : squares) {
//                        Piece piece = createPiece(getSquares()[square.x][square.y].getPiece());
//                        Integer[] enPassant = new Integer[2];
//                        Integer x = en_passant[0];
//                        Integer y = en_passant[1];
//                        enPassant[0] = x;
//                        enPassant[1] = y;
//                        updatePieces();
//                        if (validMove(i, j, square.x, square.y)) {
//                            revertMove(i, j, square.x, square.y, piece, enPassant);
//                            return;
//                        }
//                    }
//                }
//            }
//        }
//        checkMate = true;
//    }
}