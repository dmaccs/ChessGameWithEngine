package Game;

import Pieces.*;
import Players.Player;

import java.util.List;

public class ChessGame {

    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Boolean checkMate;
    private Boolean turn = true;
    private Boolean check = false;

    // add timer functionality
    public ChessGame(Board board, Player player1, Player player2){
        this.board = board;
        if(player1.getColour()){
            whitePlayer = player1;
            blackPlayer = player2;
        } else {
            whitePlayer = player2;
            blackPlayer = player1;
        }
        this.checkMate = false;
    }


    public void makeMove(int curX, int curY, int finX, int finY) {
        if(board.getSquares()[curX][curY].getPiece() != null && board.getSquares()[curX][curY].getPiece().colour == turn) {
            if (!checkMate) {
                if (curX == finX && curY == finY) {
                    return;
                }
                updatePieces();
                if(inCheck(turn)){
                    check = true;
                } else {
                    check = false;
                }
                if (validMove(curX, curY, finX, finY)) {
                    System.out.println("chessGame.makeMove(" + curX + "," + curY + "," + finX + "," + finY + ");");
                }
                if (inCheck(turn)) {

                    checkMate();
                }
            }
            if (checkMate) {
                System.out.println("Checkmate!");
            }
        }
        board.promotion = Promotions.Queen;
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
        turn = !turn;

    }

    public boolean validMove(int curX, int curY, int finX, int finY) {
        updatePieces();
        Piece piece = board.getSquares()[curX][curY].getPiece();
        boolean result = false;
        Piece kingRook = null;
        Piece promotionPiece = null;
        if (piece != null) {
            if (piece.moves().contains(board.getSquares()[finX][finY])) {
                result = true;
            }
            if (result) {
                Piece finalPiece = createPiece(board.getSquares()[finX][finY].getPiece());
                Integer[] enPassant = new Integer[2];
                Integer x = board.en_passant[0];
                Integer y = board.en_passant[1];
                enPassant[0] = x;
                enPassant[1] = y;
                Piece enPassantPiece = null;
                if(enPassant[0] != null){
                    enPassantPiece = createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                }
                boolean hasMoved = piece.hasMoved;
                if(piece instanceof King && Math.abs(curX - finX) == 2){
                    if(finX < 4) {
                        kingRook = createPiece(board.getSquares()[0][finY].getPiece());
                    } else {
                        kingRook = createPiece(board.getSquares()[7][finY].getPiece());
                    }
                }
                if(piece instanceof Pawn && (curY + finY == 13 || curY + finY == 1)){
                    promotionPiece = createPiece(piece);
                }
                doMove(curX, curY, finX, finY);
                if (inCheck(board.getSquares()[finX][finY].getPiece().colour)) {
                    result = false;
                    revertMove(curX, curY, finX, finY, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                    board.getSquares()[curX][curY].getPiece().hasMoved = hasMoved;
                }
            }
        }
        return result;
    }

    public void revertMove(int curX, int curY, int finX, int finY, Piece finalPiece, Integer[] enPassant, Piece enPassantPiece, Piece kingRook, Piece promotionPiece) {
        board.getSquares()[curX][curY].setPiece(board.getSquares()[finX][finY].getPiece());
        board.getSquares()[curX][curY].getPiece().setSquare(board.getSquares()[curX][curY]);
        board.getSquares()[finX][finY].setPiece(finalPiece);
        if (finalPiece != null) {
            finalPiece.setSquare(board.getSquares()[finX][finY]);
        }
        board.getSquares()[curX][curY].getPiece().updatePiece(curX, curY);
        turn = !turn;
        board.en_passant = enPassant;
        if(enPassantPiece != null){
            board.getSquares()[enPassant[0]][enPassant[1]].setPiece(enPassantPiece);
        }
       if(kingRook != null){
           if(finX < 4){
               board.getSquares()[0][finY].setPiece(kingRook);
               board.getSquares()[3][finY].setPiece(null);
           } else {
               board.getSquares()[7][finY].setPiece(kingRook);
               board.getSquares()[5][finY].setPiece(null);
           }
       }
       if(promotionPiece != null){
           board.getSquares()[curX][curY].setPiece(promotionPiece);
       }
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
        Piece promotionPiece = null;
        Piece kingRook = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() != null && board.getSquares()[i][j].getPiece().colour == turn) {
                    List<Square> squares = board.getSquares()[i][j].getPiece().possibleMoves();
                    for (Square square : squares) {
                        kingRook = null;
                        promotionPiece = null;
                        Piece finalPiece = createPiece(board.getSquares()[square.x][square.y].getPiece());
                        Integer[] enPassant = new Integer[2];
                        Integer x = board.en_passant[0];
                        Integer y = board.en_passant[1];
                        enPassant[0] = x;
                        enPassant[1] = y;
                        Piece enPassantPiece = null;
                        if(enPassant[0] != null){
                            enPassantPiece = createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                        }
                        if(board.getSquares()[i][j].getPiece() instanceof King && Math.abs(i - square.x) == 2){
                            if(square.x < 4) {
                                kingRook = createPiece(board.getSquares()[0][square.y].getPiece());
                            } else {
                                kingRook = createPiece(board.getSquares()[7][square.y].getPiece());
                            }
                        }
                        if(board.getSquares()[i][j].getPiece() instanceof Pawn && (j + square.y == 13 || j + square.y == 1)){
                            promotionPiece = createPiece(board.getSquares()[i][j].getPiece());
                        }
                        boolean hasMoved = board.getSquares()[i][j].getPiece().hasMoved;
                        updatePieces();
                        if (validMove(i, j, square.x, square.y)) {
                            revertMove(i, j, square.x, square.y, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                            board.getSquares()[i][j].getPiece().hasMoved = hasMoved;
                            return;
                        }
                    }
                }
            }
        }
        checkMate = true;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }
    public Player getWhitePlayer() {
        return whitePlayer;
    }
    public Board getBoard(){
        return this.board;
    }

    public Boolean getTurn() {
        return turn;
    }
    public Boolean getCheck(){
        return check;
    }
}
