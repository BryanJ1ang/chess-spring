package com.localhost.chess.service.movestrategy;

import com.localhost.chess.service.Game;
import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;

import java.util.List;

public class RookMoves implements MoveStrategy {

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

    @Override
    public void legalMoves(Game game, Piece rook, List<Triplet<Piece, Integer, Integer>> list) {
        int posX = rook.getXposition();
        int posY = rook.getYposition();
        if (posX < 0 || posX > 7 || posY < 0 || posY > 7) {
            return;
        }
        perpendicularMoves(game, rook, list);
    }

    // EFFECTS: Returns true if Rook can move to given square
    public Boolean canMove(Game game, Piece r, int nextX, int nextY) {
        return r.canMove(r.getXposition(), r.getYposition(), nextX, nextY)
                && visionStraight(game, r, nextX, nextY);
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

}
