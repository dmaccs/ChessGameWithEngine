package Game;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private final ArrayList<Square> squares;
    public final Integer en_passant;
    private int check; //0 no checks, 1 white in check, 2 black in check
    public Promotions promotion = Promotions.Queen;
    public ChessGame chessGame;

    public Board() {
        this.squares = new ArrayList<Square>();
        en_passant = null;
        for (int i = 0; i < 64; i++) {
            if (i < 16) {
                placeBlackPiece(i);
            } else if (i > 47) {
                placeWhitePiece(i);
            } else {
                squares.add(new Square(i % 8, i / 8,null,this));
            }
        }
    }


    public Board(Board board, int curI, int finI) {
        Piece selectedPiece = board.getSquares().get(curI).getPiece();
        this.squares = new ArrayList<Square>();
        for(int i = 0; i < 64; i++){
            if(i == curI){
                squares.add(new Square(board.getSquares().get(curI), null, this));
            } else if(i == finI){
                switch (selectedPiece.getType()){
                    case King:
                        squares.add(new Square(board.getSquares().get(finI), new King(selectedPiece), this));
                        break;
                    case Queen:
                        squares.add(new Square(board.getSquares().get(finI), new Queen(selectedPiece), this));
                        break;
                    case Rook:
                        squares.add(new Square(board.getSquares().get(finI), new Rook(selectedPiece), this));
                        break;
                    case Knight:
                        squares.add(new Square(board.getSquares().get(finI), new Knight(selectedPiece), this));
                        break;
                    case Bishop:
                        squares.add(new Square(board.getSquares().get(finI), new Bishop(selectedPiece), this));
                        break;
                    case Pawn:
                        squares.add(new Square(board.getSquares().get(finI), new Pawn(selectedPiece), this));
                        break;
                    default:
                        System.out.println("something is wrong in board constructor");
                        break;
                }
            } else {
                squares.add(new Square(board.getSquares().get(i),board.getSquares().get(i).getPiece(),this));
            }
        }
        if (selectedPiece instanceof Pawn && Math.abs(curI - finI) == 16) {
            this.en_passant = finI;
        } else {
            this.en_passant = null;
        }
    }

    public Board(Board board, int piecePos, Piece newPiece){
        Piece selectedPiece = board.getSquares().get(piecePos).getPiece();
        this.squares = new ArrayList<Square>();
        this.en_passant = board.en_passant;
        for(int i = 0; i < 64; i++){
            if(i == piecePos){
                squares.add(new Square(board.getSquares().get(piecePos), newPiece, this));
            } else {
                squares.add(new Square(board.getSquares().get(i), board.getSquares().get(i).getPiece(), this));
            }
        }
    }

    public void placeBlackPiece(int i) {
        switch (i) {
            case 0:
            case 7:
                squares.add(new Square(i % 8, i / 8,new Rook(false, i), this));
                break;
            case 1:
            case 6:
                squares.add(new Square(i % 8, i / 8,new Knight(false, i), this));
                break;
            case 2:
            case 5:
                squares.add(new Square(i % 8, i / 8,new Bishop(false, i), this));
                break;
            case 3:
                squares.add(new Square(i % 8, i / 8,new Queen(false, i), this));
                break;
            case 4:
                squares.add(new Square(i % 8, i / 8,new King(false, i), this));
                break;
            default:
                squares.add(new Square(i % 8, i / 8,new Pawn(false, i), this));
                break;

        }
    }


    public void placeWhitePiece(int i) {
        switch (i) {
            case 56:
            case 63:
                squares.add(new Square(i % 8, i / 8,new Rook(true, i), this));
                break;
            case 57:
            case 62:
                squares.add(new Square(i % 8, i / 8,new Knight(true, i), this));
                break;
            case 58:
            case 61:
                squares.add(new Square(i % 8, i / 8,new Bishop(true, i), this));
                break;
            case 59:
                squares.add(new Square(i % 8, i / 8,new Queen(true, i), this));
                break;
            case 60:
                squares.add(new Square(i % 8, i / 8,new King(true, i), this));
                break;
            default:
                squares.add(new Square(i % 8, i / 8,new Pawn(true, i), this));
                break;
        }
    }

    public ArrayList<Square> getSquares() {
        return this.squares;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < 64; i++){
                if (squares.get(i).getPiece() == null) {
                    output.append("[" + squares.get(i) + "0]");
                } else {
                    output.append("[" + squares.get(i).getPiece().colour.toString().substring(0, 1) + squares.get(i).getPiece() + "]");
                }
            }
            output.append("\r\n");
        return output.toString();
    }
}