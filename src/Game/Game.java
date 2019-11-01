package Game;

import Players.Human;
import Players.Player;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
        Player player = new Human(board);
        JFrame window = new JFrame("Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UI ui = new UI(player);
        window.add(ui);
        window.setSize(496, 575);
        window.setVisible(true);
//        System.out.println(board);
//        player.makeMove(6,6,6,5);
//        System.out.println(board);
//        player.makeMove(6,1,6,2);
//        System.out.println(board);
//        player.makeMove(5,7,6,6);
//        System.out.println(board);
//        player.makeMove(5,0,6,1);
//        System.out.println(board);
//        player.makeMove(6,7,5,5);
//        System.out.println(board);
//        player.makeMove(6,0,5,2);
//        System.out.println(board);
//        player.makeMove(4,7,6,7);
//        System.out.println(board);
//        player.makeMove(4,0,6,0);
//        System.out.println(board);
//        player.makeMove(5,7,4,7);
//        System.out.println(board);
//        player.makeMove(7,1,7,2);
//        System.out.println(board);
//        player.makeMove(6,0,7,2);
//        System.out.println(board);
    }

}
//TODO: check(not working) - checkmate