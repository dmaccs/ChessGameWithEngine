package Game;

import Pieces.Piece;

public class Square {
    private Piece currentPiece;
    private Board board;
    public int x;
    public int y;

    Square() {
        currentPiece = null;
    }

    public Square(Piece piece) {
        this.currentPiece = piece;
    }

    public Square(Square square){
        this.currentPiece = square.currentPiece;
    }

    public void setPiece(Piece piece) { this.currentPiece = piece; }

    public Piece getPiece() { return this.currentPiece; }

    public void setBoard(Board board){
        this.board = board;
    }

    public Board getBoard(){ return this.board; }

    @Override
    public String toString() {
        return "S";
    }
}
