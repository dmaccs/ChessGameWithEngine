package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI extends JPanel implements MouseListener, MouseMotionListener {
    int firstX, firstY, finX, finY;
    private ChessGame chessGame;
    private int squareSize = 60;

    public UI(ChessGame game) {
        super();
        this.chessGame = game;
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
        for (int i = 0; i < 64; i++) {
            int k = -1;
            int l = -1;
            if (chessGame.getBoard().getSquares().get(i).getPiece() != null) {
                switch (chessGame.getBoard().getSquares().get(i).getPiece().toString()) {
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
                graphics.drawImage(chessPieces, i % 8 * squareSize, i / 8 * squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1) * squareSize, l * squareSize, k * squareSize, l * squareSize + 60, k * squareSize + 60, this);
            }
        }
        graphics.drawImage(chessPieces, 120, 480, 180, 540, 0, 1 * squareSize, 1 * squareSize, 2 * squareSize, this);//queen
        graphics.drawImage(chessPieces, 180, 480, 240, 540, 180, 1 * squareSize, 4 * squareSize, 2 * squareSize, this);//knight
        graphics.drawImage(chessPieces, 240, 480, 300, 540, 120, 1 * squareSize, 3 * squareSize, 2 * squareSize, this);//rook
        graphics.drawImage(chessPieces, 300, 480, 360, 540, 240, 1 * squareSize, 5 * squareSize, 2 * squareSize, this);//bishop
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
       if(chessGame.isBlackEngine() && !chessGame.getTurn()){
           int[] nextMove = chessGame.blackPlayer.bestMove(chessGame);
           chessGame.makeMove(nextMove[0], nextMove[1]);
           repaint();
       } else if(chessGame.isWhiteEngine() && chessGame.getTurn()){
           int[] nextMove = chessGame.blackPlayer.bestMove(chessGame);
           chessGame.makeMove(nextMove[0], nextMove[1]);
           repaint();
       } else if (e.getX() < 480 && e.getY() < 480) {
            finX = e.getX();
            finY = e.getY();
            int curI = (firstX / 60) + (firstY / 60 * 8);
            int finI = (finX / 60) + (finY / 60 * 8);
            if (chessGame.getBoard().getSquares().get(curI).getPiece() != null) {
                chessGame.makeMove(curI, finI);
                repaint();
            }
        } else {
            setPromotions(e);
        }
    }

    public void setPromotions(MouseEvent e) {
        if (120 < e.getX() && e.getX() < 180 && e.getY() > 480 && e.getY() < 540 && 120 < firstX && firstX < 180 && firstY > 480 && firstY < 540) {
            chessGame.getBoard().promotion = Promotions.Queen;
        } else {
            if (180 < e.getX() && e.getX() < 240 && e.getY() > 480 && e.getY() < 540 && 180 < firstX && firstX < 240 && firstY > 480 && firstY < 540) {
                chessGame.getBoard().promotion = Promotions.Knight;
            } else {
                if (240 < e.getX() && e.getX() < 300 && e.getY() > 480 && e.getY() < 540 && 240 < firstX && firstX < 300 && firstY > 480 && firstY < 540) {
                    chessGame.getBoard().promotion = Promotions.Rook;
                } else {
                    if (300 < e.getX() && e.getX() < 360 && e.getY() > 480 && e.getY() < 540 && 300 < firstX && firstX < 360 && firstY > 480 && firstY < 540) {
                        chessGame.getBoard().promotion = Promotions.Bishop;
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
    }
}
