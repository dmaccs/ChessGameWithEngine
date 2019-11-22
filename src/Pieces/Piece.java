package Pieces;

import Game.Square;

import java.util.List;

public abstract class Piece {
    public Boolean colour; // White is True Black is False
    public int posX;
    public int posY;
    public Boolean hasMoved;
    private Square square;
    protected List<Square> moves;
    protected int value;

    public Piece(Boolean colour, int x, int y) {
        this.colour = colour;
        this.posX = x;
        this.posY = y;
        this.hasMoved = false;
    }
    public Piece(Piece piece){
        this.colour = piece.colour;
        this.posX = piece.posX;
        this.posY = piece.posY;
        this.hasMoved = piece.hasMoved;
        this.value = piece.value;
        this.moves = piece.moves;
        this.square = piece.square;
    }

    public void updatePiece(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
        this.hasMoved = true;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square){
        this.square = square;
    }

    public List<Square> moves(){
        return this.moves;
    }

    public abstract List<Square> possibleMoves();

    public void setMoves(List<Square> moves){
        this.moves = moves;
    }

    public int getValue(){
        return this.value;
    }

    @Override
    public abstract String toString();
}
