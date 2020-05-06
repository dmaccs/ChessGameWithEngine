package Game;

import Pieces.*;
import Players.Engine;
import Players.Player;

import java.util.List;

public class ChessGame {

    private Board board;
    public final Player whitePlayer;
    public final Player blackPlayer;
    private Boolean checkMate;
    private Boolean turn;
    private Boolean check;

    // add timer functionality
    public ChessGame(Player player1, Player player2) {
        this.board = new Board();
        if (player1.getColour()) {
            whitePlayer = player1;
            blackPlayer = player2;
        } else {
            whitePlayer = player2;
            blackPlayer = player1;
        }
        this.checkMate = false;
        this.turn = true;
        this.check = false;
    }

    public void makeMove(int curI, int finI) {
        Piece curPiece = board.getSquares().get(curI).getPiece();
        if (curPiece != null && curPiece.colour == turn) {
            if (curPiece.possibleMoves(board, board.getSquares().get(curI)).contains(board.getSquares().get(finI))) {
                Board newBoard = new Board(board, curI, finI);
                newBoard = kingMoves(curI, finI, curPiece, newBoard);
                newBoard = pawnMoves(curI, finI, curPiece, newBoard);
                if (legalMove(newBoard)) {
                    this.board = newBoard;
                    this.turn = !turn;
                    System.out.println("chessGame.makeMove(" + curI + ", " + finI + ");");
                }
            }
        }
    }

    private Board kingMoves(int curI, int finI, Piece curPiece, Board newBoard) {
        if (curPiece instanceof King && Math.abs(curI - finI) == 2) { // castling
            System.out.println("huh");//castling
            if (curI - finI > 0) {
                newBoard = new Board(newBoard, curI - 4, finI + 1); // left rooks
            } else {
                newBoard = new Board(newBoard, curI + 3, finI - 1); // right rooks
            }
        }
        return newBoard;
    }

    private Board pawnMoves(int curI, int finI, Piece curPiece, Board newBoard) {
        if (curPiece instanceof Pawn) {
            if (Math.abs(curI - finI) == 7 || Math.abs(curI - finI) == 9) {// en passant
                if (board.getSquares().get(finI).getPiece() == null) {
                    if ((curI - finI) == -9 || (curI - finI) == 7) {
                        newBoard = new Board(newBoard, curI + 1, null);
                    } else if ((curI - finI) == 9 || (curI - finI) == -7) {
                        newBoard = new Board(newBoard, curI - 1, null);
                    }
                }
            }
            if (finI < 8 || finI > 55) {//promotion
                switch (board.promotion) {
                    case Queen:
                        newBoard = new Board(newBoard, finI, new Queen(turn, finI));
                        break;
                    case Rook:
                        newBoard = new Board(newBoard, finI, new Rook(turn, finI));
                        break;
                    case Bishop:
                        newBoard = new Board(newBoard, finI, new Bishop(turn, finI));
                        break;
                    case Knight:
                        newBoard = new Board(newBoard, finI, new Knight(turn, finI));
                        break;
                }
            }
        }
        return newBoard;
    }

    public boolean legalMove(Board newBoard) {
        int kinPos = findKing(newBoard);
        if (kinPos == 65) {
            System.out.println("no king, something is broken in chessGame");
            return false;
        }
        for (int i = 0; i < 64; i++) {
            Piece curPiece = newBoard.getSquares().get(i).getPiece();
            if (curPiece != null) {
                if (curPiece.colour != turn) {
                    if (curPiece.possibleMoves(newBoard, newBoard.getSquares().get(i)).contains(newBoard.getSquares().get(kinPos))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int findKing(Board board) {
        for (int i = 0; i < 64; i++) {
            if (board.getSquares().get(i).getPiece() instanceof King && board.getSquares().get(i).getPiece().colour == turn) {
                return i;
            }
        }
        return 65;
    }

    public Board getBoard() {
        return this.board;
    }

    public Boolean getTurn() {
        return turn;
    }

    public Boolean getCheck() {
        return check;
    }
    public Boolean isWhiteEngine(){
        return (whitePlayer instanceof Engine);
    }
    public Boolean isBlackEngine(){
        return (blackPlayer instanceof Engine);
    }
}
