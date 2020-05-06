package Pieces;

import Game.Board;
import Game.PieceType;
import Game.Square;

import java.util.List;

public abstract class Piece {
    public Boolean colour; // White is True Black is False
    public Boolean hasMoved;
    private Board board;
    protected double value;
    protected PieceType type;

    public Piece(Boolean colour, int i) {
        this.colour = colour;
        this.hasMoved = false;
    }
    public Piece(Piece piece){
        this.colour = piece.colour;
        this.hasMoved = true;
        this.value = piece.value;
        this.type = piece.type;
    }

    public void setBoard (Board board){
        this.board = board;
    }

    public Board getBoard(){
        return this.board;
    }

    public abstract List<Square> possibleMoves(Board board, Square square);

    public double getValue(){
        return this.value;
    }

    public PieceType getType(){
        return this.type;
    }

    @Override
    public abstract String toString();
}
