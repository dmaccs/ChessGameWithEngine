package Game;

import Players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UI extends JPanel implements MouseListener, MouseMotionListener {
    int firstX, firstY, finX, finY;
    private Player player;
    private int squareSize = 60;

    public UI(Player player) {
        super();
        this.player = player;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Color chessGreen = new Color(118, 150, 86);
        Color chessCream = new Color(238, 238, 210);
        this.setBackground(chessGreen);
        graphics.setColor(chessCream);
        boolean offset = true;
        for (int k = 0; k < 8; k = k + 2) {
            for (int j = 0; j < 8; j++) {
                offset = !offset;
                if (offset) {
                    graphics.fillRect(k * 60 + 60, j * 60, 60, 60);
                } else {
                    graphics.fillRect(k * 60, j * 60, 60, 60);
                }
            }
        }
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        Image chessPieces = new ImageIcon("ChessPiecesArray.png").getImage();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int k = -1;
                int l = -1;
                if (player.board.getSquares()[i][j].getPiece() != null) {
                    switch (player.board.getSquares()[i][j].getPiece().toString()) {
                        case "R":
                            l = 2;
                            k = 1;
                            break;
                        case "r":
                            l = 2;
                            k = 0;
                            break;
                        case "N":
                            l = 3;
                            k = 1;
                            break;
                        case "n":
                            l = 3;
                            k = 0;
                            break;
                        case "B":
                            l = 4;
                            k = 1;
                            break;
                        case "b":
                            l = 4;
                            k = 0;
                            break;
                        case "Q":
                            l = 0;
                            k = 1;
                            break;
                        case "q":
                            l = 0;
                            k = 0;
                            break;
                        case "K":
                            l = 1;
                            k = 1;
                            break;
                        case "k":
                            l = 1;
                            k = 0;
                            break;
                        case "P":
                            l = 5;
                            k = 1;
                            break;
                        case "p":
                            l = 5;
                            k = 0;
                            break;
                    }
                    graphics.drawImage(chessPieces, i * squareSize, j * squareSize, (i + 1) * squareSize, (j + 1) * squareSize, l * squareSize, k * squareSize, l * squareSize + 60, k * squareSize + 60, this);
                }
            }
        }
        graphics.drawImage(chessPieces,120,480,180,540, 0 ,1*squareSize, 1*squareSize,2*squareSize,this);//queen
        graphics.drawImage(chessPieces,180,480,240,540, 180,1*squareSize, 4*squareSize,2*squareSize,this);//knight
        graphics.drawImage(chessPieces,240,480,300,540, 120,1*squareSize, 3*squareSize,2*squareSize,this);//rook
        graphics.drawImage(chessPieces,300,480,360,540, 240,1*squareSize, 5*squareSize,2*squareSize,this);//bishop
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() < 480 && e.getY() < 540) {
            firstX = e.getX();
            firstY = e.getY();
            repaint();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX() < 480 && e.getY() < 480) {
            finX = e.getX();
            finY = e.getY();
            if (player.board.getSquares()[firstX / 60][firstY / 60].getPiece() != null) {
                player.makeMove(firstX / 60, firstY / 60, finX / 60, finY / 60);
                repaint();
            }
        } else {
            if (120 < e.getX() && e.getX() < 180 && e.getY() > 480 && e.getY() < 540 && 120 < firstX && firstX < 180 && firstY > 480 && firstY < 540) {
                player.board.promotion = Promotions.Queen;
            } else {
                if (180 < e.getX() && e.getX() < 240 && e.getY() > 480 && e.getY() < 540 && 180 < firstX && firstX < 240 && firstY > 480 && firstY < 540) {
                    player.board.promotion = Promotions.Knight;
                } else {
                    if (240 < e.getX() && e.getX() < 300 && e.getY() > 480 && e.getY() < 540 && 240 < firstX && firstX < 300 && firstY > 480 && firstY < 540) {
                        player.board.promotion = Promotions.Rook;
                    } else {
                        if (300 < e.getX() && e.getX() < 360 && e.getY() > 480 && e.getY() < 540 && 300 < firstX && firstX < 360 && firstY > 480 && firstY < 540) {
                            player.board.promotion = Promotions.Bishop;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        repaint();
    }
}
