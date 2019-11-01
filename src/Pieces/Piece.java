package Pieces;

import Game.Square;

import java.util.List;

public abstract class Piece {
    public Boolean colour; // White is True Black is False
    public int posX;
    public int posY;
    protected int posInitX;
    protected int posInitY;
    public Boolean hasMoved;
    private Square square;
    protected List<Square> moves;

    public Piece(Boolean colour, int x, int y) {
        this.colour = colour;
        this.posX = x;
        this.posY = y;
        this.posInitX = x;
        this.posInitY = y;
        this.hasMoved = false;
    }
    public Piece(Piece piece){
        this.colour = piece.colour;
        this.posX = piece.posX;
        this.posY = piece.posY;
        this.posInitX = piece.posInitX;
        this.posInitY = piece.posInitY;
        this.hasMoved = piece.hasMoved;
    }

    public abstract Integer[] getContacts(int x, int y);

    public Boolean legalMove(int x, int y){
        if(x == posX && y == posY || x < 0 || x > 7 || y < 0 || y > 7){
            return false;
        }
        return true;
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

    @Override
    public abstract String toString();
}
