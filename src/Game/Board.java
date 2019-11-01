package Game;

import Pieces.*;

public class Board {
    private Square[][] squares;
    public Integer[] lastMove = new Integer[4];
    public Integer[] en_passant = new Integer[2];
    public boolean turn = true;
    public Square[][] nextMove = new Square[8][8];


    public Promotions promotion = Promotions.Queen;


    public Board() {
        this.squares = new Square[8][8];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                squares[i][j] = new Square();
                if (j == 0 || j == 1) {
                    placeBlackPiece(i, j);
                }
                if (j == 6 || j == 7) {
                    placeWhitePiece(i, j);
                }
            }
        }
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                squares[i][j].setBoard(this);
                if (squares[i][j].getPiece() != null) {
                    squares[i][j].getPiece().setSquare(squares[i][j]);
                    squares[i][j].getPiece().setMoves( squares[i][j].getPiece().possibleMoves());

                }
            }
        }
    }

    public void placeBlackPiece(int i, int j) {
        if (j == 1) {
            squares[i][j].setPiece(new Pawn(false, i, j));
        }
        if (j == 0) {
            if (i == 0 || i == 7) {
                squares[i][j].setPiece(new Rook(false, i, j));
            } else {
                if (i == 1 || i == 6) {
                    squares[i][j].setPiece(new Knight(false, i, j));
                } else {
                    if (i == 2 || i == 5) {
                        squares[i][j].setPiece(new Bishop(false, i, j));
                    } else {
                        if (i == 3) {
                            squares[i][j].setPiece(new Queen(false, i, j));
                        } else {
                            if (i == 4) {
                                squares[i][j].setPiece(new King(false, i, j));
                            }
                        }
                    }
                }
            }
        }
    }

    public void placeWhitePiece(int i, int j) {
        if (j == 6) {
            squares[i][j].setPiece(new Pawn(true, i, j));
        } else {
            if (j == 7) {
                if (i == 0 || i == 7) {
                    squares[i][j].setPiece(new Rook(true, i, j));
                } else {
                    if (i == 1 || i == 6) {
                        squares[i][j].setPiece(new Knight(true, i, j));
                    } else {
                        if (i == 2 || i == 5) {
                            squares[i][j].setPiece(new Bishop(true, i, j));
                        } else {
                            if (i == 3) {
                                squares[i][j].setPiece(new Queen(true, i, j));
                            } else {
                                if (i == 4) {
                                    squares[i][j].setPiece(new King(true, i, j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Square[][] getSquares() {
        return this.squares;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                if (squares[l][k].getPiece() == null) {
                    output.append("[" + squares[l][k] + "0]");
                } else {
                    output.append("[" + squares[l][k].getPiece().colour.toString().substring(0, 1) + squares[l][k].getPiece() + "]");
                }
            }
            output.append("\r\n");
        }
        return output.toString();
    }
}

//    public void makeMove(int curX, int curY, int finX, int finY) {
//        if (validMove(curX, curY, finX, finY)) {
//            doMove(curX,curY,finX,finY);
//        }
//    }
//
//    public void doMove(int curX, int curY, int finX, int finY){
//        doMove(curX, curY, finX, finY);
//        if (squares[curX][curY].getPiece().colour == turn) {
//            if (squares[curX][curY].getPiece() instanceof Pawn && en_passant[0] != null) {
//                if (en_passant[0] == finX && Math.abs(curX - en_passant[0]) == 1 && en_passant[1] == curY) {
//                    squares[en_passant[0]][en_passant[1]].setPiece(null);
//                }
//            }
//            if (squares[curX][curY].getPiece() instanceof Pawn && Math.abs(curY - finY) == 2) {
//                en_passant[0] = finX;
//                en_passant[1] = finY;
//            } else {
//                en_passant[0] = null;
//                en_passant[1] = null;
//            }
//            if (squares[curX][curY].getPiece() instanceof King && Math.abs(curX - finX) == 2) {
//                Integer[] contacts = squares[curX][curY].getPiece().getContacts(finX, finY);
//                squares[contacts[0] / 10][contacts[0] % 10].setPiece(squares[contacts[contacts.length - 1] / 10][contacts[contacts.length - 1] % 10].getPiece());
//                squares[contacts[contacts.length - 1] / 10][contacts[contacts.length - 1] % 10].setPiece(null);
//                squares[contacts[0] / 10][contacts[0] % 10].getPiece().updatePiece(contacts[0] / 10, contacts[0] % 10);
//            }
//            if (squares[curX][curY].getPiece() instanceof Pawn && (finY == 7 || finY == 0)) {
//                boolean colour = squares[curX][curY].getPiece().colour;
//                switch (promotion) {
//                    case Queen:
//                        squares[curX][curY].setPiece(new Queen(colour, curX, curY));
//                        break;
//                    case Rook:
//                        squares[curX][curY].setPiece(new Rook(colour, curX, curY));
//                        break;
//                    case Bishop:
//                        squares[curX][curY].setPiece(new Bishop(colour, curX, curY));
//                        break;
//                    case Knight:
//                        squares[curX][curY].setPiece(new Knight(colour, curX, curY));
//                        break;
//                }
//            }
//            squares[finX][finY].setPiece(squares[curX][curY].getPiece());
//            squares[curX][curY].setPiece(null);
//            squares[finX][finY].getPiece().updatePiece(finX, finY);
//            lastMove[0] = curX;
//            lastMove[1] = curY;
//            lastMove[2] = finX;
//            lastMove[3] = finY;
//            turn = !turn;
//        }
//    }
//
//    public Boolean validMove(int curX, int curY, int finX, int finY) {
//        boolean result = false;
//        Piece piece = squares[curX][curY].getPiece();
//        updatePiece(piece);
//        if (piece != null) {
//            if (piece.legalMove(finX, finY)) {
//                Integer[] positions = piece.getContacts(finX, finY);
//                result = true;
//                for (int i = 0; i < positions.length - 1; i++) {
//                    int j = positions[i];
//                    if (squares[j / 10][j % 10].getPiece() != null) {
//                        result = false;
//                    }
//                }
//                Piece pieceI = squares[(positions[positions.length - 1]) / 10][(positions[positions.length - 1]) % 10].getPiece();
//                if (pieceI != null) {
//                    if (squares[curX][curY].getPiece() instanceof Pawn && curX == finX) {
//                        result = false;
//                    }
//                    if (!(squares[curX][curY].getPiece() instanceof King && pieceI instanceof Rook && !pieceI.hasMoved && !squares[curX][curY].getPiece().hasMoved)) {
//                        if (pieceI.colour == piece.colour) {
//                            result = false;
//                        }
//                    }
//                }
//            }
//            if (result) {
//                checkBoard.squares = squares;
//                checkBoard.doMove(curX,curY,finX,finY);
//                if(checkBoard.inCheck(squares[curX][curY].getPiece().colour)){
//                    result = false;
//                }
//            }
//        }
//        return result;
//    }
//
//    public boolean inCheck(boolean turn) {
//        boolean result = false;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (squares[i][j].getPiece() instanceof King && squares[i][j].getPiece().colour == turn) {
//                    int kingX = i;
//                    int kingY = j;
//                    for (i = 0; i < 8; i++) {
//                        for (j = 0; j < 8; j++) {
//                            if (squares[i][j].getPiece() != null) {
//                                result = result || validMove(i, j, kingX, kingY);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return result;
//    }
//
//    public void updatePiece(Piece piece) {
//        if (piece instanceof Pawn) {
//            boolean CTL = false;
//            boolean CTR = false;
//            if (piece.colour) {
//                if (piece.posX != 0 && piece.posY != 0) {
//                    if (squares[piece.posX - 1][piece.posY - 1].getPiece() != null) {
//                        CTL = true;
//                    }
//                    if (en_passant[0] != null) {
//                        if ((piece.posX - 1) == en_passant[0] && (piece.posY == en_passant[1])) {
//                            CTL = true;
//                        }
//                    }
//                }
//                if (piece.posX != 7) {
//                    if (squares[piece.posX + 1][piece.posY - 1].getPiece() != null) {
//                        CTR = true;
//                    }
//                    if (en_passant[0] != null) {
//                        if ((piece.posX + 1) == en_passant[0] && (piece.posY == en_passant[1])) {
//                            CTR = true;
//                        }
//                    }
//                }
//            } else {
//                if (piece.posX != 0 && piece.posY != 7) {
//                    if (squares[piece.posX - 1][piece.posY + 1].getPiece() != null) {
//                        CTL = true;
//                    }
//                    if (en_passant[0] != null) {
//                        if ((piece.posX - 1) == en_passant[0] && (piece.posY == en_passant[1])) {
//                            CTL = true;
//                        }
//                    }
//                }
//                if (piece.posX != 7) {
//                    if (squares[piece.posX + 1][piece.posY + 1].getPiece() != null) {
//                        CTR = true;
//                    }
//                    if (en_passant[0] != null) {
//                        if ((piece.posX + 1) == en_passant[0] && (piece.posY == en_passant[1])) {
//                            CTR = true;
//                        }
//                    }
//                }
//            }
//            ((Pawn) piece).setCanTake(CTL, CTR);
//        }
//    }