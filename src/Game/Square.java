package Game;

import Pieces.Piece;

public class Square {
    private final Piece piece;
    public final int x;
    public final int y;
    private final Board board;

    public Square(int x, int y, Piece piece, Board board) {
        this.piece = piece;
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public Square(Square square, Piece newPiece, Board newBoard){
        this.piece = newPiece;
        this.board = newBoard;
        this.x = square.x;
        this.y = square.y;
    }

    public Piece getPiece() { return this.piece; }

    public Board getBoard(){ return this.board;}

    @Override
    public String toString() {
        return "S";
    }
}
