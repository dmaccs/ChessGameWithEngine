package Players;

import Game.Board;
import Pieces.*;

public abstract class Player {
    public Board board;

    public Player() {
        board = null;
    }

    public Player(Board board) {
        this.board = board;
    }

    public void makeMove(int curX, int curY, int finX, int finY) {
        if (curX == finX && curY == finY) {
            return;
        }
        validMove(curX, curY, finX, finY);
        updatePieces();
    }

    public void doMove(int curX, int curY, int finX, int finY) {
        if (board.getSquares()[curX][curY].getPiece().colour == board.turn) {
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && board.en_passant[0] != null) {
                if (board.en_passant[0] == finX && Math.abs(curX - board.en_passant[0]) == 1 && board.en_passant[1] == curY) {
                    board.getSquares()[board.en_passant[0]][board.en_passant[1]].setPiece(null);
                }
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && Math.abs(curY - finY) == 2) {
                board.en_passant[0] = finX;
                board.en_passant[1] = finY;
            } else {
                board.en_passant[0] = null;
                board.en_passant[1] = null;
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof King && Math.abs(curX - finX) == 2) {
                if(curX - finX > 0){
                    board.getSquares()[3][curY].setPiece(board.getSquares()[0][curY].getPiece());
                    board.getSquares()[3][curY].getPiece().setSquare(board.getSquares()[3][curY]);
                    board.getSquares()[3][curY].getPiece().updatePiece(3, curY);
                    board.getSquares()[0][curY].setPiece(null);
                } else {
                    board.getSquares()[5][curY].setPiece(board.getSquares()[7][curY].getPiece());
                    board.getSquares()[5][curY].getPiece().setSquare(board.getSquares()[5][curY]);
                    board.getSquares()[5][curY].getPiece().updatePiece(5, curY);
                    board.getSquares()[7][curY].setPiece(null);
                }
            }
            if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && (finY == 7 || finY == 0)) {
                boolean colour = board.getSquares()[curX][curY].getPiece().colour;
                switch (board.promotion) {
                    case Queen:
                        board.getSquares()[curX][curY].setPiece(new Queen(colour, curX, curY));
                        break;
                    case Rook:
                        board.getSquares()[curX][curY].setPiece(new Rook(colour, curX, curY));
                        break;
                    case Bishop:
                        board.getSquares()[curX][curY].setPiece(new Bishop(colour, curX, curY));
                        break;
                    case Knight:
                        board.getSquares()[curX][curY].setPiece(new Knight(colour, curX, curY));
                        break;
                }
            }
            board.getSquares()[finX][finY].setPiece(board.getSquares()[curX][curY].getPiece());
            board.getSquares()[finX][finY].getPiece().setSquare(board.getSquares()[finX][finY]);
            board.getSquares()[finX][finY].getPiece().updatePiece(finX, finY);
            board.getSquares()[curX][curY].setPiece(null);
            board.turn = !board.turn;
            updatePieces();
        }
    }

    public Boolean validMove(int curX, int curY, int finX, int finY) {
        updatePieces();
        boolean result = false;
        Piece piece = board.getSquares()[curX][curY].getPiece();
        updatePiece(piece);
        if (piece != null && piece.colour == board.turn) {
            if (piece.moves().contains(board.getSquares()[finX][finY])) {
                result = true;
            }
//            if (piece.legalMove(finX, finY)) {
//                Integer[] positions = piece.getContacts(finX, finY);
//                result = true;
//                for (int i = 0; i < positions.length - 1; i++) {
//                    int j = positions[i];
//                    if (board.getSquares()[j / 10][j % 10].getPiece() != null) {
//                        result = false;
//                    }
//                }
//                Piece pieceI = board.getSquares()[(positions[positions.length - 1]) / 10][(positions[positions.length - 1]) % 10].getPiece();
//                if (pieceI != null) {
//                    if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && curX == finX) {
//                        result = false;
//                    }
//                }
//            }
            if (result) {
                piece = createPiece(board.getSquares()[curX][finY].getPiece());
                doMove(curX, curY, finX, finY);
                if (inCheck(board.getSquares()[finX][finY].getPiece().colour)) {
                    result = false;
                    revertMove(curX, curY, finX, finY, piece);
                }
            }
        }
        return result;
    }

    public void revertMove(int curX, int curY, int finX, int finY, Piece piece) {
        board.getSquares()[curX][curY].setPiece(board.getSquares()[finX][finY].getPiece());
        board.getSquares()[curX][curY].getPiece().setSquare(board.getSquares()[curX][curY]);
        board.getSquares()[finX][finY].setPiece(piece);
        if (piece != null) {
            piece.setSquare(board.getSquares()[finX][finY]);
        }
        board.getSquares()[curX][curY].getPiece().updatePiece(curX, finY);
        board.turn = !board.turn;
        updatePieces();
    }

    public Piece createPiece(Piece piece) {
        if (piece != null) {
            switch (piece.toString()) {
                case "R":
                    return new Rook(piece);
                case "r":
                    return new Rook(piece);
                case "N":
                    return new Knight(piece);
                case "n":
                    return new Knight(piece);
                case "B":
                    return new Bishop(piece);
                case "b":
                    return new Bishop(piece);
                case "Q":
                    return new Queen(piece);
                case "q":
                    return new Queen(piece);
                case "K":
                    return new King(piece);
                case "k":
                    return new King(piece);
                case "P":
                    return new Pawn(piece);
                case "p":
                    return new Pawn(piece);
            }
        }
        return null;
    }

    public Boolean validMoveCheckBoard(int curX, int curY, int finX, int finY) {
        boolean result = false;
        Piece piece = board.getSquares()[curX][curY].getPiece();
        updatePiece(piece);
        if (piece != null && piece.colour == board.turn) {
            if (piece.legalMove(finX, finY)) {
                Integer[] positions = piece.getContacts(finX, finY);
                result = true;
                for (int i = 0; i < positions.length - 1; i++) {
                    int j = positions[i];
                    if (board.getSquares()[j / 10][j % 10].getPiece() != null) {
                        result = false;
                    }
                }
                Piece pieceI = board.getSquares()[(positions[positions.length - 1]) / 10][(positions[positions.length - 1]) % 10].getPiece();
                if (pieceI != null) {
                    if (board.getSquares()[curX][curY].getPiece() instanceof Pawn && curX == finX) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    public boolean inCheck(boolean turn) {
        int kingX = 10;
        int kingY = 10;
        boolean result = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() instanceof King && board.getSquares()[i][j].getPiece().colour == turn) {
                    kingX = i;
                    kingY = j;
                    i = j = 8;
                    break;
                }
            }
        }
        if (kingX == 10 || kingY == 10) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() != null) {
                    result = result || validMoveCheckBoard(i, j, kingX, kingY);
                }
            }
        }
        return result;
    }

    public void updatePiece(Piece piece) {
        if (piece instanceof Pawn) {
            boolean CTL = false;
            boolean CTR = false;
            if (piece.colour) {
                if (piece.posX != 0 && piece.posY != 0) {
                    if (board.getSquares()[piece.posX - 1][piece.posY - 1].getPiece() != null) {
                        CTL = true;
                    }
                    if (board.en_passant[0] != null) {
                        if ((piece.posX - 1) == board.en_passant[0] && (piece.posY == board.en_passant[1])) {
                            CTL = true;
                        }
                    }
                }
                if (piece.posX != 7) {
                    if (board.getSquares()[piece.posX + 1][piece.posY - 1].getPiece() != null) {
                        CTR = true;
                    }
                    if (board.en_passant[0] != null) {
                        if ((piece.posX + 1) == board.en_passant[0] && (piece.posY == board.en_passant[1])) {
                            CTR = true;
                        }
                    }
                }
            } else {
                if (piece.posX != 0 && piece.posY != 7) {
                    if (board.getSquares()[piece.posX - 1][piece.posY + 1].getPiece() != null) {
                        CTL = true;
                    }
                    if (board.en_passant[0] != null) {
                        if ((piece.posX - 1) == board.en_passant[0] && (piece.posY == board.en_passant[1])) {
                            CTL = true;
                        }
                    }
                }
                if (piece.posX != 7) {
                    if (board.getSquares()[piece.posX + 1][piece.posY + 1].getPiece() != null) {
                        CTR = true;
                    }
                    if (board.en_passant[0] != null) {
                        if ((piece.posX + 1) == board.en_passant[0] && (piece.posY == board.en_passant[1])) {
                            CTR = true;
                        }
                    }
                }
            }
            ((Pawn) piece).setCanTake(CTL, CTR);
        }
    }

    public void updatePieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSquares()[i][j].getPiece() != null) {
                    board.getSquares()[i][j].getPiece().setMoves(board.getSquares()[i][j].getPiece().possibleMoves());
                }
            }
        }
    }

    public void checkMate() {
        boolean result = true;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

            }
        }
    }
}
