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
    }

}
//TODO: Engine