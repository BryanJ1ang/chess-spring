package com.localhost.chess.model;

import com.localhost.chess.model.pieces.*;

import static java.lang.Math.abs;

// represents chess board with squares and pieces
public class Board {
    private final Piece[][] bd = new Piece[8][8];
    // FIRST DIMENSION = COLUMNS
    // SECOND DIMENSION = ROWS

    // REQUIRES: Piece is one of (King, Queen, Rook, Bishop, Knight, Pawn)
    //           x and y are values in between [0,7] inclusive
    // METHOD: this, Piece
    // EFFECT: Adds piece to location on board
    public void addPiece(Piece piece, int x, int y) {
        bd[x][y] = piece;
        if (piece != null) {
            piece.setPositions(x, y);
        }
    }

    // MODIFIES: this, Piece
    // EFFECT: Moves a piece to given square. Removes original piece if square already occupied
    public void movePiece(Piece p, int nextx, int nexty) {
        this.removePiece(p.getXposition(), p.getYposition());
        bd[nextx][nexty] = null;
        bd[nextx][nexty] = p;
        p.setPositions(nextx, nexty);
    }


    // REQUIRE: X and Y are between 0 and 7
    // EFFECT: returns piece from given a square
    public Piece getPiece(int x, int y) {
        return bd[x][y];
    }

    // METHOD: this
    // EFFECT: removes a piece from a given square
    public void removePiece(int x, int y) {
        bd[x][y] = null;
    }




    //EFFECT: determines if piece can move to selected square
    //        (piece is not blocked, square is not already occupied)
    public Boolean validMove(Piece p, int nextx, int nexty) {
        Boolean b = false;
        if (p.removed()) {
            return false;
        } else if (this.getPiece(nextx, nexty) != null && p.isWhite() == this.getPiece(nextx, nexty).isWhite()) {
            return false;
        }
        if (p instanceof Bishop) {
            b = canMoveBishop(p, nextx, nexty);
        }
        if (p instanceof Queen) {
            b = canMoveQueen(p, nextx, nexty);
        }
        if (p instanceof Knight) {
            b = canMoveKnight(p, nextx, nexty);
        }
        if (p instanceof Rook) {
            b = canMoveRook(p, nextx, nexty);
        }
        if (p instanceof Pawn) {
            b = canMovePawn(p, nextx, nexty);
        }
        if (p instanceof King) {
            b = p.canMove(p.getXposition(), p.getYposition(), nextx, nexty);
        }
        return b;
    }

    //EFFECTS: if given bishop can move to next position
    private Boolean canMoveBishop(Piece b, int nextx, int nexty) {
        return b.canMove(b.getXposition(), b.getYposition(), nextx, nexty)
                && visionDiagonal(b, nextx, nexty);
    }

    //EFFECTS: if given queen can move to next position
    private Boolean canMoveQueen(Piece q, int nextx, int nexty) {
        Boolean b = false;
        if (q.canMove(q.getXposition(), q.getYposition(), nextx, nexty)) {
            if ((nextx == q.getXposition() || nexty == q.getYposition())
                    && this.visionStraight(q, nextx, nexty)) {
                b = true;
            }
            if (this.visionDiagonal(q, nextx, nexty)
                    && (abs(nextx - q.getXposition()) == abs((nexty - q.getYposition())))) {
                b = true;
            }
        }

        return b;
    }

    //EFFECTS: if given rook can move to next position
    private Boolean canMoveRook(Piece r, int nextx, int nexty) {
        return r.canMove(r.getXposition(), r.getYposition(), nextx, nexty)
                && visionStraight(r, nextx, nexty);
    }

    //EFFECTS: if given knight can move to next position
    private Boolean canMoveKnight(Piece k, int nextx, int nexty) {
        Piece piece = this.getPiece(nextx, nexty);
        if (this.getPiece(nextx, nexty) != null && piece.isWhite() == k.isWhite()) {
            return false;
        }
        return k.canMove(k.getXposition(), k.getYposition(), nextx, nexty);
    }

    //EFFECTS: if given pawn can move to next position
    private Boolean canMovePawn(Piece p, int nextx, int nexty) {
        Boolean b = false;
        if (p.canMove(p.getXposition(), p.getYposition(), nextx, nexty)
                && this.getPiece(nextx, nexty) == null) {
            b = true;
        }
        if (p.isWhite()) {
            if (abs(nextx - p.getXposition()) == 1
                    && nexty == p.getYposition() - 1
                    && this.getPiece(nextx, nexty) != null) {
                b = true;
            }
        }
        if (!p.isWhite()) {
            if (abs(nextx - p.getXposition()) == 1
                    && nexty == p.getYposition() + 1
                    && this.getPiece(nextx, nexty) != null) {
                b = true;
            }
        }
        return b;
    }

    // EFFECT: return true if given king is in check
    public Boolean check(King x) {
        Boolean b = false;
        int kx = x.getXposition();
        int ky = x.getYposition();

        for (int xcord = 0; xcord < 7; xcord++) {
            for (int ycord = 0; ycord < 7; ycord++) {
                if (this.getPiece(xcord, ycord) != null) {
                    if (validMove(this.getPiece(xcord, ycord), kx, ky)) {
                        b = true;
                    }
                }
            }
        }
        return b;
    }


    // REQUIRES: x and y is not location of given piece and is diagonal to it
    // EFFECTS: Returns true if piece can see given square diagonally
    public Boolean visionDiagonal(Piece p, int x, int y) {
        Boolean b = true;
        if (x > p.getXposition() && y > p.getYposition()) {
            b = diagonalXGreaterYGreater(p, x, y);
        }
        if (x > p.getXposition() && y < p.getYposition()) {
            b = diagonalXGreaterYLesser(p,x,y);
        }

        if (x < p.getXposition() && y > p.getYposition()) {
            b = diagonalXLesserYGreater(p,x,y);
        }

        if (x < p.getXposition() && y < p.getYposition()) {
            b = diagonalXLesserYLesser(p, x, y);
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXGreaterYGreater(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
                if ((x - xcord == y - ycord) && (this.getPiece(xcord, ycord) != null)) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXLesserYGreater(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
                if ((xcord - x == y - ycord && (this.getPiece(xcord, ycord) != null))) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXGreaterYLesser(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
                if ((x - xcord == ycord - y && (this.getPiece(xcord, ycord) != null))) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXLesserYLesser(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
                if ((xcord - x == ycord - y && this.getPiece(xcord,ycord) != null)) {
                    b = false;
                }
            }
        }
        return b;
    }



    //EFFECTS: Returns true if piece can see given square vertically/horizontally
    public Boolean visionStraight(Piece p, int x, int y) {
        Boolean b = true;
        if (x == p.getXposition() && y > p.getYposition()) {
            b = straightYGreater(p, x, y);
        }
        if (x == p.getXposition() && y < p.getYposition()) {
            b = straightYLesser(p, x, y);
        }
        if (y == p.getYposition() && x > p.getXposition()) {
            b = straightXGreater(p, x, y);
        }
        if (y == p.getYposition() && x < p.getXposition()) {
            b = straightXLesser(p, x, y);
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightXLesser(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            if (this.getPiece(xcord, y) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightXGreater(Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            if (this.getPiece(xcord, y) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightYGreater(Piece p, int x, int y) {
        Boolean b = true;
        for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
            if (this.getPiece(x, ycord) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightYLesser(Piece p, int x, int y) {
        Boolean b = true;
        for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
            if (this.getPiece(x, ycord) != null) {
                b = false;
            }
        }
        return b;
    }
}


