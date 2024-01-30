package com.localhost.chess.service.movestrategy;

import com.localhost.chess.service.Game;
import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;

import java.util.List;

public class KingMoves implements MoveStrategy {
    public void legalMoves(Game game, Piece king, List<Triplet<Piece, Integer, Integer>> list) {
        int posX = king.getXposition();
        int posY = king.getYposition();
        for (int x = posX - 1; x < posX + 2; x++) {
            for (int y = posY - 1; y < posY + 2; y++) {
                if (x == posX && y == posY) {
                    continue;
                } else if (game.validMove(king, x, y)) {
                    list.add(new Triplet<>((Piece) king, x, y));
                }
            }
        }
        if (posX == 3) {
            if (game.validMove(king, posX - 2, posY))    {
                list.add(new Triplet<>((Piece) king, posX - 2, posY));
            }
            if (game.validMove(king, posX + 2, posY)) {
                list.add(new Triplet<>((Piece) king, posX + 2, posY));
            }
        }
    }

    @Override
    public Boolean canMove(Game game, Piece p, int nextX, int nextY) {
        return  p.canMove(p.getXposition(), p.getYposition(), nextX, nextY);
    }

}
