package com.localhost.chess.service.movestrategy;

import com.localhost.chess.service.Game;
import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;

import java.util.List;

import static java.lang.Math.abs;

public class QueenMoves implements MoveStrategy {


    // REQUIRES: piece is actual type Queen or Bishop
    // EFFECTS: Adds legal diagonal moves to list
    private void diagonalMoves(Game game, Piece piece, List<Triplet<Piece, Integer, Integer>> list) {
        int posX = piece.getXposition();
        int posY = piece.getYposition();
        int y = posY;
        for (int x = posX + 1; x < 8; x++) {
            y += 1;
            if (game.validMove(piece, x, y)) {
                if (game.getBd().getPiece(x,y) != null) {
                    list.add(0,new Triplet<>(piece, x, y));
                } else {
                    list.add(new Triplet<>(piece, x, y));
                }
            } else if (x < 8 && x > -1 && y < 7 && y > -1 && game.getBd().getPiece(x,y) != null) {
                break;
            }
        }
        y = posY;
        for (int x = posX + 1; x < 8; x++) {
            y -= 1;
            if (game.validMove(piece, x, y)) {
                if (game.getBd().getPiece(x, y) != null) {
                    list.add(0, new Triplet<>(piece, x, y));
                } else {
                    list.add(new Triplet<>(piece, x, y));
                }
            } else if (x < 8 && x > -1 && y < 7 && y > -1 && game.getBd().getPiece(x,y) != null) {
                break;
            }
        }
        y = posY;
        for (int x = posX - 1; x > -1; x--) {
            y += 1;
            if (game.validMove(piece, x, y)) {
                if (game.getBd().getPiece(x, y) != null) {
                    list.add(0, new Triplet<>(piece, x, y));
                } else {
                    list.add(new Triplet<>(piece, x, y));
                }
            } else if (x < 8 && x > -1 && y < 7 && y > -1 && game.getBd().getPiece(x,y) != null) {
                break;
            }
        }
        y = posY;
        for (int x = posX - 1; x > -1; x--) {
            y -= 1;
            if (game.validMove(piece, x, y)) {
                if (game.getBd().getPiece(x, y) != null) {
                    list.add(0, new Triplet<>(piece, x, y));
                } else {
                    list.add(new Triplet<>(piece, x, y));
                }
            } else if (x < 8 && x > -1 && y < 7 && y > -1 && game.getBd().getPiece(x,y) != null) {
                break;
            }
        }
    }

    // REQUIRES: Piece is actual type Queen or Rook
    // EFFECTS: Adds legal up/down/left/right moves to list
    public void perpendicularMoves(Game game, Piece piece, List<Triplet<Piece, Integer, Integer>> list) {
        int posX = piece.getXposition();
        int posY = piece.getYposition();
        for (int x = posX + 1; x < 8; x++) {
            if (game.validMove(piece, x, posY)) {
                if (game.getBd().getPiece(x, posY) != null) {
                    list.add(0, new Triplet<>(piece, x, posY));
                } else {
                    list.add(new Triplet<>(piece, x, posY));
                }
            } else if (game.getBd().getPiece(x,posY) != null) {
                break;
            }
        }
        for (int y = posY + 1; y < 8; y++) {
            if (game.validMove(piece, posX, y)) {
                if (game.getBd().getPiece(posX, y) != null) {
                    list.add(0, new Triplet<>(piece, posX, y));
                } else {
                    list.add(new Triplet<>(piece, posX, y));
                }
            } else if (game.getBd().getPiece(posX,y) != null) {
                break;
            }
        }
        for (int x = posX - 1; x > -1; x--) {
            if (game.validMove(piece, x, posY)) {
                if (game.getBd().getPiece(x, posY) != null) {
                    list.add(0, new Triplet<>(piece, x, posY));
                } else {
                    list.add(new Triplet<>(piece, x, posY));
                }
            } else if (game.getBd().getPiece(x,posY) != null) {
                break;
            }
        }
        for (int y = posY - 1; y > -1; y--) {
            if (game.validMove(piece, posX, y)) {
                if (game.getBd().getPiece(posX, y) != null) {
                    list.add(0, new Triplet<>(piece, posX, y));
                } else {
                    list.add(new Triplet<>(piece, posX, y));
                }
            } else if (game.getBd().getPiece(posX,y) != null) {
                break;
            }
        }
    }

    public void legalMoves(Game game, Piece queen, List<Triplet<Piece, Integer, Integer>> list) {
        diagonalMoves(game, queen, list);
        perpendicularMoves(game, queen, list);
    }

    @Override
    public Boolean canMove(Game game, Piece q, int nextX, int nextY) {
        Boolean b = false;
        if (q.canMove(q.getXposition(), q.getYposition(), nextX, nextY)) {
            if ((nextX == q.getXposition() || nextY == q.getYposition())
                    && visionStraight(game,q, nextX, nextY)) {
                b = true;
            } else if (this.visionDiagonal(game, q, nextX, nextY)
                    && (abs(nextX - q.getXposition()) == abs((nextY - q.getYposition())))) {
                b = true;
            }
        }
        return b;
    }

    //EFFECTS: Returns true if piece can see given square vertically/horizontally
    public Boolean visionStraight(Game game, Piece p, int x, int y) {
        Boolean b = true;
        if (x == p.getXposition() && y > p.getYposition()) {
            b = straightYGreater(game, p, x, y);
        }
        if (x == p.getXposition() && y < p.getYposition()) {
            b = straightYLesser(game, p, x, y);
        }
        if (y == p.getYposition() && x > p.getXposition()) {
            b = straightXGreater(game, p, x, y);
        }
        if (y == p.getYposition() && x < p.getXposition()) {
            b = straightXLesser(game, p, x, y);
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightXLesser(Game game,Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            if (game.getBd().getPiece(xcord, y) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightXGreater(Game game,Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            if (game.getBd().getPiece(xcord, y) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightYGreater(Game game,Piece p, int x, int y) {
        Boolean b = true;
        for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
            if (game.getBd().getPiece(x, ycord) != null) {
                b = false;
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean straightYLesser(Game game,Piece p, int x, int y) {
        Boolean b = true;
        for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
            if (game.getBd().getPiece(x, ycord) != null) {
                b = false;
            }
        }
        return b;
    }

    // REQUIRES: x and y is not location of given piece and is diagonal to it
    // EFFECTS: Returns true if piece can see given square diagonally
    private Boolean visionDiagonal(Game game, Piece p, int x, int y) {
        Boolean b = true;
        if (x > p.getXposition() && y > p.getYposition()) {
            b = diagonalXGreaterYGreater(game, p, x, y);
        }
        if (x > p.getXposition() && y < p.getYposition()) {
            b = diagonalXGreaterYLesser(game,p,x,y);
        }

        if (x < p.getXposition() && y > p.getYposition()) {
            b = diagonalXLesserYGreater(game,p,x,y);
        }

        if (x < p.getXposition() && y < p.getYposition()) {
            b = diagonalXLesserYLesser(game,p, x, y);
        }
        return b;
    }


    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXGreaterYGreater(Game game, Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
                if ((x - xcord == y - ycord) && (game.getBd().getPiece(xcord, ycord) != null)) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXLesserYGreater(Game game, Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            for (int ycord = p.getYposition() + 1; ycord < y; ycord++) {
                if ((xcord - x == y - ycord && (game.getBd().getPiece(xcord, ycord) != null))) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXGreaterYLesser(Game game, Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() + 1; xcord < x; xcord++) {
            for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
                if ((x - xcord == ycord - y && (game.getBd().getPiece(xcord, ycord) != null))) {
                    b = false;
                }
            }
        }
        return b;
    }

    // EFFECTS: Returns true if piece can see given square
    private Boolean diagonalXLesserYLesser(Game game, Piece p, int x, int y) {
        Boolean b = true;
        for (int xcord = p.getXposition() - 1; xcord > x; xcord--) {
            for (int ycord = p.getYposition() - 1; ycord > y; ycord--) {
                if ((xcord - x == ycord - y && game.getBd().getPiece(xcord,ycord) != null)) {
                    b = false;
                }
            }
        }
        return b;
    }

}

