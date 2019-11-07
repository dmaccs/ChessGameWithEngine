package Players;

import Game.Board;
import Game.Square;
import Pieces.*;

import java.util.List;

public abstract class Player {
    public Board board;
    public boolean checkMate = false;

    public Player() {
        board = null;
    }

    public Player(Board board) {
        this.board = board;
    }

    public void makeMove(int curX, int curY, int finX, int finY) {
        if(board.getSquares()[curX][curY].getPiece() != null && board.getSquares()[curX][curY].getPiece().colour == board.turn) {
            if (!checkMate) {
                if (curX == finX && curY == finY) {
                    return;
                }
                updatePieces();
                if (validMove(curX, curY, finX, finY)) {
                    System.out.println("player.makeMove(" + curX + "," + curY + "," + finX + "," + finY + ");");
                    // System.out.println(board.en_passant[0] +  "," + board.en_passant[1]);
                }
                if (inCheck(board.turn)) {
                    checkMate();
                }
            }
            if (checkMate) {
                System.out.println("Checkmate!");
            }
        }
    }

    public void doMove(int curX, int curY, int finX, int finY) {
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && board.en_passant[0] != null) {
                if (board.en_passant[0] == finX && Math.abs(curX - board.en_passant[0]) == 1 && board.en_passant[1] == curY) {
                    board.getSquares()[board.en_passant[0]][board.en_passant[1]].setPiece(null);
                }
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && Math.abs(curY - finY) == 2) {
                board.en_passant[0] = finX;
                board.en_passant[1] = finY;
            } else {
                board.en_passant[0] = null;
                board.en_passant[1] = null;
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof King && Math.abs(curX - finX) == 2) { //castling
                if (curX - finX > 0) {
                    board.getSquares()[3][curY].setPiece(board.getSquares()[0][curY].getPiece());
                    board.getSquares()[3][curY].getPiece().setSquare(board.getSquares()[3][curY]);
                    board.getSquares()[3][curY].getPiece().updatePiece(3, curY);
                    board.getSquares()[0][curY].setPiece(null);
                } else {
                    board.getSquares()[5][curY].setPiece(board.getSquares()[7][curY].getPiece());
                    board.getSquares()[5][curY].getPiece().setSquare(board.getSquares()[5][curY]);
                    board.getSquares()[5][curY].getPiece().updatePiece(5, curY);
                    board.getSquares()[7][curY].setPiece(null);
                }
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && (finY == 7 || finY == 0)) { //promotion
                boolean colour = board.getSquares()[curX][curY].getPiece().colour;
                switch (board.promotion) {
                    case Queen:
                        board.getSquares()[curX][curY].setPiece(new Queen(colour, curX, curY));
                        break;
                    case Rook:
                        board.getSquares()[curX][curY].setPiece(new Rook(colour, curX, curY));
                        break;
                    case Bishop:
                        board.getSquares()[curX][curY].setPiece(new Bishop(colour, curX, curY));
                        break;
                    case Knight:
                        board.getSquares()[curX][curY].setPiece(new Knight(colour, curX, curY));
                        break;
                }
            }
            board.getSquares()[finX][finY].setPiece(board.getSquares()[curX][curY].getPiece()); // update board
            board.getSquares()[finX][finY].getPiece().setSquare(board.getSquares()[finX][finY]);
            board.getSquares()[finX][finY].getPiece().updatePiece(finX, finY);
            board.getSquares()[curX][curY].setPiece(null);
            board.turn = !board.turn;


        }

    public boolean validMove(int curX, int curY, int finX, int finY) {
        Piece piece = board.getSquares()[curX][curY].getPiece();
        boolean result = false;
        if (piece != null) {
            if (piece.moves().contains(board.getSquares()[finX][finY])) {
                result = true;
            }
            if (result) {
                piece = createPiece(board.getSquares()[finX][finY].getPiece());
                Integer[] enPassant = new Integer[2];
                Integer x = board.en_passant[0];
                Integer y = board.en_passant[1];
                enPassant[0] = x;
                enPassant[1] = y;
                doMove(curX, curY, finX, finY);
                if (inCheck(board.getSquares()[finX][finY].getPiece().colour)) {
                    result = false;
                    revertMove(curX, curY, finX, finY, piece, enPassant);
                }
            }
        }
        return result;
    }

    public void revertMove(int curX, int curY, int finX, int finY, Piece piece, Integer[] enPassant) {
        board.getSquares()[curX][curY].setPiece(board.getSquares()[finX][finY].getPiece());
        board.getSquares()[curX][curY].getPiece().setSquare(board.getSquares()[curX][curY]);
        board.getSquares()[finX][finY].setPiece(piece);
        if (piece != null) {
            piece.setSquare(board.getSquares()[finX][finY]);
        }
        board.getSquares()[curX][curY].getPiece().updatePiece(curX, curY);
        board.turn = !board.turn;
        board.en_passant = enPassant;
    }

    public Piece createPiece(Piece piece) {
        if (piece != null) {
            switch (piece.toString()) {
                case "R":
                    return new Rook(piece);
                case "r":
                    return new Rook(piece);
                case "N":
                    return new Knight(piece);
                case "n":
                    return new Knight(piece);
                case "B":
                    return new Bishop(piece);
                case "b":
                    return new Bishop(piece);
                case "Q":
                    return new Queen(piece);
                case "q":
                    return new Queen(piece);
                case "K":
                    return new King(piece);
                case "k":
                    return new King(piece);
                case "P":
                    return new Pawn(piece);
                case "p":
                    return new Pawn(piece);
            }
        }
        return null;
    }

    public Boolean validMoveCheckBoard(int curX, int curY, int finX, int finY) {
        updatePieces();
        Piece piece = board.getSquares()[curX][curY].getPiece();
        if (piece != null) {
            if (piece.moves().contains(board.getSquares()[finX][finY])) {
                return true;
            }
        }
        return false;
    }

    public boolean inCheck(boolean turn) {
        int kingX = 10;
        int kingY = 10;
        boolean result = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() instanceof King && board.getSquares()[i][j].getPiece().colour == turn) {
                    kingX = i;
                    kingY = j;
                    i = j = 8;
                    break;
                }
            }
        }
        if (kingX == 10 || kingY == 10) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() != null) {
                    result = result || validMoveCheckBoard(i, j, kingX, kingY);
                }
            }
        }
        return result;
    }

    public void updatePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() != null) {
                    board.getSquares()[i][j].getPiece().setMoves(board.getSquares()[i][j].getPiece().possibleMoves());
                }
            }
        }
    }

    public void checkMate() {
        updatePieces();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (board.getSquares()[i][j].getPiece() != null && board.getSquares()[i][j].getPiece().colour == board.turn) {
                    List<Square> squares = board.getSquares()[i][j].getPiece().possibleMoves();
                    for (Square square : squares) {
                        Piece piece = createPiece(board.getSquares()[square.x][square.y].getPiece());
                        Integer[] enPassant = new Integer[2];
                        Integer x = board.en_passant[0];
                        Integer y = board.en_passant[1];
                        enPassant[0] = x;
                        enPassant[1] = y;
                        updatePieces();
                        if (validMove(i, j, square.x, square.y)) {
                            revertMove(i, j, square.x, square.y, piece, enPassant);
                            return;
                        }
                    }
                }
            }
        }
        checkMate = true;
    }
}
