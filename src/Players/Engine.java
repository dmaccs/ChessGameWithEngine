package Players;

import Game.Board;
import Game.ChessGame;
import Game.Square;
import Pieces.Piece;

public class Engine extends Player {

    private int depth;

    public Engine(boolean colour, int depth) {
        super(colour);
        this.depth = depth;
    }

    public double positionalValue(Board board) {
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


    public double mobilityValue(Board board) {
        double whiteMoves = 0;
        double blackMoves = 0;
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
                return -2147483647;
            } else {
                return 0;
            }
        }
        if (blackMoves == 0) {
            if (chessGame.legalMove(board)) {
                return 2147483647;
            } else {
                return 0;
            }
        }
        return whiteMoves - blackMoves;
    }

    public double totalValue(int depth, double alpha, double beta, Board board) {
        double max = -2147483647;
        double min = +2147483647;
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
        double max = -2147483647;
        double min = 2147483647;
        int[] currentBest = new int[2];
        if (board.getTurn()) {
            for (int i = 0; i < 64; i++) {
                Piece piece = board.getSquares().get(i).getPiece();
                if (piece != null) {
                    if (piece.colour == board.getTurn()) {
                        for (Square square : piece.possibleMoves(board, board.getSquares().get(i))) {
                            Board newBoard = chessGame.engineMove(i,square.x + square.y * 8, board);
                            if (chessGame.legalMove(newBoard)) {
                                double test = totalValue(depth - 1, -2147483648, 2147483647, newBoard);
                                if (test > max) {
                                    max = test;
                                    currentBest[0] = i;
                                    currentBest[1] = square.x + square.y * 8;
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
                                double test = totalValue(depth - 1, -2147483648, 2147483647, newBoard);
                                if (test < min) {
                                    min = test;
                                    currentBest[0] = i;
                                    currentBest[1] = square.x + square.y * 8;
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