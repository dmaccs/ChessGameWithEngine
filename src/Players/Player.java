package Players;

import Game.ChessGame;
import Pieces.Piece;

import java.util.List;

public abstract class Player {
    public ChessGame chessGame;
    private String name;
    private List<Piece> pieces;
    private boolean colour;

    public Player(boolean colour) {
        this.colour = colour;
    }

    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;
    }

    public boolean getColour() {
        return this.colour;
    }

    public int[] bestMove(ChessGame chessGame){
        return null;
    }
}


