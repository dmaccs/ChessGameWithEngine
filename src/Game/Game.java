package Game;

//import Players.Engine;
import Players.Engine;
import Players.Human;
import Players.Player;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
//        Player player1 = new Engine(true, 2);
        Player player2 = new Engine(false,3);
        Player player1 = new Human(true);
//        Player player2 = new Human(false);
        ChessGame chessGame = new ChessGame(player1, player2);
        player1.setChessGame(chessGame);
        player2.setChessGame(chessGame);
        JFrame window = new JFrame("Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI ui = new UI(chessGame);
        window.add(ui);//
        window.setSize(496, 575);
        window.setVisible(true);
        chessGame.makeMove(51, 35);
        int[] nextMove = chessGame.blackPlayer.bestMove(chessGame);
        chessGame.makeMove(nextMove[0], nextMove[1]);
    }
}