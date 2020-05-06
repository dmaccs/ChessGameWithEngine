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

    public int positionalValue(Board board) {
        int positionalValue = 0;
        for (int i = 0; i < 64; i++) {
            Piece piece = board.getSquares().get(i).getPiece();
            if (piece != null) {
                if (piece.colour) {
                    positionalValue += piece.getValue();
                } else {
                    positionalValue -= piece.getValue();
                }
            }
        }
        return positionalValue * 10;
    }


    public int mobilityValue(Board board) {
        int whiteMoves = 0;
        int blackMoves = 0;
        for (int i = 0; i < 64; i++) {
            Piece piece = board.getSquares().get(i).getPiece();
            if (piece != null) {
                if (piece.colour) {
                    whiteMoves += piece.possibleMoves(board, board.getSquares().get(i)).size();
                } else {
                    blackMoves += piece.possibleMoves(board, board.getSquares().get(i)).size();
                }
            }
        }
        if (whiteMoves == 0) {
            if (chessGame.legalMove(board)) {
                return -2000000;
            } else {
                return -1000;
            }
        }
        if (blackMoves == 0) {
            if (chessGame.legalMove(board)) {
                return 2000000;
            } else {
                return 1000;
            }
        }
        return whiteMoves - blackMoves;
    }

    public int totalValue(int depth, int alpha, int beta, Board board) {
        int max = -3000000;
        int min = +3000000;
        if (depth == 0) {
            return positionalValue(board) + mobilityValue(board);
        } else {
            if (board.getTurn()) {
                for (int i = 0; i < 64; i++) {
                    Piece piece = board.getSquares().get(i).getPiece();
                    if (piece != null) {
                        if (piece.colour) {
                            for (Square square : piece.possibleMoves(board, board.getSquares().get(i))) {
                                Board newBoard = chessGame.engineMove(i,square.x + square.y * 8, board);
                                if (chessGame.legalMove(newBoard)) {
                                    max = Math.max(max, totalValue(depth - 1, alpha, beta, newBoard));
                                    alpha = Math.max(alpha, max);
                                    if (alpha >= beta) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < 64; i++) {
                    Piece piece = board.getSquares().get(i).getPiece();
                    if (piece != null) {
                        if (!piece.colour) {
                            for (Square square : piece.possibleMoves(board, board.getSquares().get(i))) {
                                Board newBoard = chessGame.engineMove(i,square.x + square.y * 8, board);
                                if (chessGame.legalMove(newBoard)) {
                                    min = Math.min(min, totalValue(depth - 1, alpha, beta, newBoard));
                                    beta = Math.min(beta, min);
                                    if (alpha >= beta) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (board.getTurn()) {
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
        int[] currentBest = new int[2];
        Piece kingRook = null;
        Piece promotionPiece = null;
        if (board.getTurn()) {
            for (int i = 0; i < 64; i++) {
                Piece piece = board.getSquares().get(i).getPiece();
                if (piece != null) {
                    if (piece.colour == board.getTurn()) {
                        for (Square square : piece.possibleMoves(board, board.getSquares().get(i))) {
                            Board newBoard = chessGame.engineMove(i,square.x + square.y * 8, board);
                            if (chessGame.legalMove(newBoard)) {
                                int test = totalValue(depth - 1, -2147483648, 2147483647, newBoard);
                                if (test > max) {
                                    max = test;
                                    currentBest[0] = i;
                                    currentBest[1] = square.x + square.y * 8;
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
                Piece piece = board.getSquares().get(i).getPiece();
                if (piece != null) {
                    if (piece.colour == board.getTurn()) {
                        for (Square square : piece.possibleMoves(board, board.getSquares().get(i))) {
                            Board newBoard = chessGame.engineMove(i,square.x + square.y * 8, board);
                            if (chessGame.legalMove(newBoard)) {
                                int test = totalValue(depth - 1, -2147483648, 2147483647, newBoard);
                                if (test < min) {
                                    min = test;
                                    currentBest[0] = i;
                                    currentBest[1] = square.x + square.y * 8;
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