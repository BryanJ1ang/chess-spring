package com.localhost.chess.service.movestrategy;

import com.localhost.chess.service.Game;
import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;

import java.util.List;

public class BishopMoves implements MoveStrategy {

    @Override
    public void legalMoves(Game game, Piece king, List<Triplet<Piece, Integer, Integer>> list) {
        int posX = king.getXposition();
        int posY = king.getYposition();
        if (posX < 0 || posX > 7 || posY < 0 || posY > 7) {
            return;
        }
        diagonalMoves(game, king, list);
    }

    @Override
    public Boolean canMove(Game game, Piece b, int nextX, int nextY) {
        return b.canMove(b.getXposition(), b.getYposition(), nextX, nextY)
                && visionDiagonal(game, b, nextX, nextY);
    }

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
