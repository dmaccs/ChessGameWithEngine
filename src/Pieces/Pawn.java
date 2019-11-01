package Pieces;

import Game.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    boolean canTakeRight = false;
    boolean canTakeLeft = false;

    public Pawn(Boolean colour, int x, int y) {
        super(colour, x, y);
    }

    public Pawn(Piece piece){
        super(piece);
    }

    public List<Square> possibleMoves() {
        List<Square> possibleMoves = new ArrayList<>();
        List<Square> squares = new ArrayList<>();
        if (!colour) {
            if (!this.hasMoved) {
                squares.add(super.getSquare().getBoard().getSquares()[posX][posY + 2]);
            }
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY + 1]);
            if (canTakeLeft) {
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX - 1][posY + 1]);
            }
            if (canTakeRight) {
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX + 1][posY + 1]);
            }
        } else {
            if (!this.hasMoved) {
                squares.add(super.getSquare().getBoard().getSquares()[posX][posY - 2]);
            }
            squares.add(super.getSquare().getBoard().getSquares()[posX][posY - 1]);
            if (canTakeLeft) {
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX - 1][posY - 1]);
            }
            if (canTakeRight) {
                possibleMoves.add(super.getSquare().getBoard().getSquares()[posX + 1][posY - 1]);
            }
        }
        for (Square square : squares) {
            if (square.getPiece() == null) {
                possibleMoves.add(square);
            }
        }
        return possibleMoves;
    }

    @Override
    public Integer[] getContacts(int x, int y) {
        Integer[] positions;
        int i = x;
        int j = y;
        int k = 0;
        positions = new Integer[Math.abs(y - posY)];
        if(posX != x){
            positions[0] = x * 10 + y;
            return positions;
        }
        while (posY - j < 0) {
            positions[positions.length - 1 - k] = x * 10 + j;
            j--;
            k++;
        }
        while (posY - j > 0) {
            positions[positions.length - 1 - k] = x * 10 + j;
            j++;
            k++;
        }
        positions[positions.length - 1] = x * 10 + y;
        return positions;
    }
    public Boolean legalMove(int finalX, int finalY) {
        if(canTakeLeft) {
            if (this.colour) {
                if ((posX - finalX) == 1 && posY - finalY == 1) {
                    return true;// white pawn take left
                }
            } else {
                if ((posX - finalX) == 1 && posY - finalY == -1) {
                    return true;// black pawn take left)
                }
            }
        }
        if(canTakeRight) {
            if (this.colour) {
                if ((posX - finalX) == -1 && posY - finalY == 1) {
                    return true;// white pawn take right
                }
            } else {
                if ((posX - finalX) == -1 && posY - finalY == -1) {
                    return true;// black pawn take right)
                }
            }
        }

        if (posX == finalX && posY == finalY) {
            return false;
        }
        if (!hasMoved) {
            if (this.colour) {
                if (posX == finalX && posY - finalY == 2) {
                    return true;
                }
            } else {
                if (posX == finalX && posY - finalY == -2) {
                    return true;
                }
            }
        }
        if (this.colour) {
            if (posX == finalX && posY - finalY == 1) {
                return true;
            }
        } else {
            if (posX == finalX && posY - finalY == -1) {
                return true;
            }
        }
        return false;
    }

    public void setCanTake(boolean canTakeLeft, boolean canTakeRight) {
        this.canTakeRight = canTakeRight;
        this.canTakeLeft = canTakeLeft;
    }

    public String toString() {
        if (colour) {
            return "P";
        }
        return "p";
    }
}
