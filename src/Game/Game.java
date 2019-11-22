package Game;

import Players.Engine;
import Players.Human;
import Players.Player;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
//        Player player1 = new Engine(true, 2);
        Player player2 = new Engine(false, 5);
        Player player1 = new Human(true);
//        Player player2 = new Human(false);
        ChessGame chessGame = new ChessGame(board, player1, player2);
        board.setChessGame(chessGame);
        player1.setChessGame(chessGame);
        player2.setChessGame(chessGame);
        JFrame window = new JFrame("Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI ui = new UI(chessGame);
        window.add(ui);
        window.setSize(496, 575);
        window.setVisible(true);
//        chessGame.makeMove(3,6,3,4);
//        chessGame.makeMove(4,1,4,3);
//        chessGame.makeMove(6,6,6,5);
//        chessGame.makeMove(4,3,3,4);
//        chessGame.makeMove(4,6,4,4);
//        chessGame.makeMove(3,4,4,5);
//        chessGame.makeMove(6,5,6,4);
//        chessGame.makeMove(5,1,5,2);
//        chessGame.makeMove(4,7,4,6);
//        chessGame.makeMove(3,0,4,1);
//        chessGame.makeMove(6,4,6,3);
//        chessGame.makeMove(4,5,5,6);
//        chessGame.makeMove(4,6,3,6);
//        int[] bestMoves = chessGame.getBlackPlayer().bestMove(chessGame);
//        System.out.println(bestMoves[0] + "," + bestMoves[1] + "," + bestMoves[2] + "," + bestMoves[3]);
//        chessGame.makeMove(bestMoves[0],bestMoves[1],bestMoves[2],bestMoves[3]);
    }
}