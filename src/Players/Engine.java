package Players;

import Game.Board;
import Game.ChessGame;
import Game.Square;
import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;

public class Engine extends Player {

    private int depth;

    public Engine(boolean colour, int depth) {
        super(colour);
        this.depth = depth;
    }

    public int positionalValue() {
        int positionalValue = 0;
        for (int i = 0; i < 64; i++) {
            Piece piece = chessGame.getBoard().getSquares()[i / 8][i % 8].getPiece();
            if (piece != null) {
                if (piece.colour) {
                    positionalValue += piece.getValue();
                } else {
                    positionalValue -= piece.getValue();
                }
            }
        }
        return positionalValue * 10000;
    }


    public int mobilityValue() {
        int whiteMoves = 0;
        int blackMoves = 0;
        for (int i = 0; i < 64; i++) {
            Piece piece = chessGame.getBoard().getSquares()[i / 8][i % 8].getPiece();
            if (piece != null) {
                if (piece.colour) {
                    whiteMoves += piece.moves().size();
                } else {
                    blackMoves += piece.moves().size();
                }
            }
        }
        if (whiteMoves == 0) {
            if (chessGame.inCheck(true)) {
                return -2000000;
            } else {
                return -1000;
            }
        }
        if (blackMoves == 0) {
            if (chessGame.inCheck(false)) {
                return 2000000;
            } else {
                return 1000;
            }
        }
        return whiteMoves - blackMoves;
    }

    public int totalValue(int depth, int alpha, int beta) {
        int max = -3000000;
        int min = +3000000;
        int inX, inY, finX, finY;
        Piece kingRook = null;
        Piece promotionPiece = null;
        Board board = chessGame.getBoard();
        if (depth == 0) {
            return positionalValue() + mobilityValue();
        } else {
            if (chessGame.getTurn()) {
                for (int i = 0; i < 64; i++) {
                    Piece piece = board.getSquares()[i / 8][i % 8].getPiece();
                    if (piece != null) {
                        if (piece.colour) {
                            for (Square square : piece.moves()) {
                                promotionPiece = null;
                                kingRook = null;
                                Piece finalPiece = chessGame.createPiece(chessGame.getBoard().getSquares()[square.x][square.y].getPiece());
                                inX = piece.posX;
                                inY = piece.posY;
                                finX = square.x;
                                finY = square.y;
                                boolean hasMoved = piece.hasMoved;
                                Integer[] enPassant = new Integer[2];
                                Integer x = chessGame.getBoard().en_passant[0];
                                Integer y = chessGame.getBoard().en_passant[1];
                                enPassant[0] = x;
                                enPassant[1] = y;
                                Piece enPassantPiece = null;
                                if(enPassant[0] != null){
                                    enPassantPiece = chessGame.createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                                }
                                if(piece instanceof King && Math.abs(inX - finX) == 2){
                                    if(finX < 4) {
                                        kingRook = chessGame.createPiece(board.getSquares()[0][finY].getPiece());
                                    } else {
                                        kingRook = chessGame.createPiece(board.getSquares()[7][finY].getPiece());
                                    }
                                }
                                    if (piece instanceof Pawn && (inY + finY == 13 || inY + finY == 1)) {
                                        promotionPiece = chessGame.createPiece(piece);
                                }
                                if (chessGame.validMove(piece.posX, piece.posY, square.x, square.y)) {
                                    max = Math.max(max, totalValue(depth - 1, alpha, beta));
                                    alpha = Math.max(alpha,max);
                                    chessGame.revertMove(inX, inY, finX, finY, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                                    chessGame.getBoard().getSquares()[inX][inY].getPiece().hasMoved = hasMoved;
                                    chessGame.updatePieces();
                                    if(alpha >= beta){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < 64; i++) {
                    Piece piece = board.getSquares()[i / 8][i % 8].getPiece();
                    if (piece != null) {
                        if (!piece.colour) {
                            for (Square square : piece.moves()) {
                                kingRook = null;
                                promotionPiece = null;
                                Piece finalPiece = chessGame.createPiece(chessGame.getBoard().getSquares()[square.x][square.y].getPiece());
                                inX = piece.posX;
                                inY = piece.posY;
                                finX = square.x;
                                finY = square.y;
                                boolean hasMoved = piece.hasMoved;
                                Integer[] enPassant = new Integer[2];
                                Integer x = chessGame.getBoard().en_passant[0];
                                Integer y = chessGame.getBoard().en_passant[1];
                                enPassant[0] = x;
                                enPassant[1] = y;
                                Piece enPassantPiece = null;
                                if(enPassant[0] != null){
                                    enPassantPiece = chessGame.createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                                }
                                if(piece instanceof King && Math.abs(inX - finX) == 2){
                                    if(finX < 4) {
                                        kingRook = chessGame.createPiece(board.getSquares()[0][finY].getPiece());
                                    } else {
                                        kingRook = chessGame.createPiece(board.getSquares()[7][finY].getPiece());
                                    }
                                }
                                if(piece instanceof Pawn && (inY + finY == 13 || inY + finY == 1)){
                                    promotionPiece = chessGame.createPiece(piece);
                                }
                                if (chessGame.validMove(piece.posX, piece.posY, square.x, square.y)) {
                                    min = Math.min(min, totalValue(depth - 1, alpha, beta));
                                    beta = Math.min(beta, min);
                                    chessGame.revertMove(inX, inY, finX, finY, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                                    chessGame.getBoard().getSquares()[inX][inY].getPiece().hasMoved = hasMoved;
                                    chessGame.updatePieces();
                                    if(alpha >= beta){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (chessGame.getTurn()) {
            return max;
        } else {
            return min;
        }
    }

    @Override
    public int[] bestMove(ChessGame chessGame) {
        Board board = chessGame.getBoard();
        int max = -30000000;
        int min = 3000000;
        int inX, inY, finX, finY;
        int[] currentBest = new int[4];
        Piece kingRook = null;
        Piece promotionPiece = null;
        if (chessGame.getTurn()) {
            for (int i = 0; i < 64; i++) {
                Piece piece = board.getSquares()[i / 8][i % 8].getPiece();
                if (piece != null) {
                    if (piece.colour == chessGame.getTurn()) {
                        for (Square square : piece.moves()) {
                            kingRook = null;
                            promotionPiece = null;
                            Piece finalPiece = chessGame.createPiece(board.getSquares()[square.x][square.y].getPiece());
                            inX = piece.posX;
                            inY = piece.posY;
                            finX = square.x;
                            finY = square.y;
                            boolean hasMoved = piece.hasMoved;
                            Integer[] enPassant = new Integer[2];
                            Integer x = board.en_passant[0];
                            Integer y = board.en_passant[1];
                            enPassant[0] = x;
                            enPassant[1] = y;
                            Piece enPassantPiece = null;
                            if(enPassant[0] != null){
                                enPassantPiece = chessGame.createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                            }
                            if(piece instanceof King && Math.abs(inX - finX) == 2){
                                if(finX < 4) {
                                    kingRook = chessGame.createPiece(board.getSquares()[0][finY].getPiece());
                                } else {
                                    kingRook = chessGame.createPiece(board.getSquares()[7][finY].getPiece());
                                }
                            }
                            if(inY == 1 && piece instanceof Pawn) {
                                if (piece instanceof Pawn && (inY + finY == 13 || inY + finY == 1)) {
                                    promotionPiece = chessGame.createPiece(piece);
                                }
                            }
                            if (chessGame.validMove(piece.posX, piece.posY, square.x, square.y)) {
                                int test = totalValue(depth - 1, -2147483648,2147483647);
                                chessGame.revertMove(inX, inY, finX, finY, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                                board.getSquares()[inX][inY].getPiece().hasMoved = hasMoved;
                                chessGame.updatePieces();
                                if (test > max) {
                                    max = test;
                                    currentBest[0] = inX;
                                    currentBest[1] = inY;
                                    currentBest[2] = finX;
                                    currentBest[3] = finY;
                                    //System.out.println(max);
                                    //System.out.println(currentBest[0] + "," + currentBest[1] + "," + currentBest[2] + "," + currentBest[3]);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 64; i++) {
                Piece piece = board.getSquares()[i / 8][i % 8].getPiece();
                if (piece != null) {
                    if (piece.colour == chessGame.getTurn()) {
                        for (Square square : piece.moves()) {
                            kingRook = null;
                            promotionPiece = null;
                            Piece finalPiece = chessGame.createPiece(board.getSquares()[square.x][square.y].getPiece());
                            inX = piece.posX;
                            inY = piece.posY;
                            finX = square.x;
                            finY = square.y;
                            boolean hasMoved = piece.hasMoved;
                            Integer[] enPassant = new Integer[2];
                            Integer x = board.en_passant[0];
                            Integer y = board.en_passant[1];
                            enPassant[0] = x;
                            enPassant[1] = y;
                            Piece enPassantPiece = null;
                            if(enPassant[0] != null){
                                enPassantPiece = chessGame.createPiece(board.getSquares()[enPassant[0]][enPassant[1]].getPiece());
                            }
                            if(piece instanceof King && Math.abs(inX - finX) == 2){
                                if(finX < 4) {
                                    kingRook = chessGame.createPiece(board.getSquares()[0][finY].getPiece());
                                } else {
                                    kingRook = chessGame.createPiece(board.getSquares()[7][finY].getPiece());
                                }
                            }
                            if(inY == 6 && piece instanceof Pawn) {
                                if (piece instanceof Pawn && (inY + finY == 13 || inY + finY == 1)) {
                                    promotionPiece = chessGame.createPiece(piece);
                                }
                            }
                            if (chessGame.validMove(piece.posX, piece.posY, square.x, square.y)) {
                                int test = totalValue(depth - 1,-2147483648,2147483647);
                                chessGame.revertMove(inX, inY, finX, finY, finalPiece, enPassant, enPassantPiece, kingRook, promotionPiece);
                                board.getSquares()[inX][inY].getPiece().hasMoved = hasMoved;
                                chessGame.updatePieces();
                                if (test < min) {
                                    min = test;
                                    currentBest[0] = inX;
                                    currentBest[1] = inY;
                                    currentBest[2] = finX;
                                    currentBest[3] = finY;
                                    //System.out.println(min);
                                    //System.out.println(currentBest[0] + "," + currentBest[1] + "," + currentBest[2] + "," + currentBest[3]);
                                }
                            }
                        }
                    }
                }
            }
        }
        return currentBest;
    }
}