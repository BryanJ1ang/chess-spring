package com.localhost.chess.service.movestrategy;

import com.localhost.chess.service.Game;
import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;

import java.util.List;

import static java.lang.Math.abs;

public class PawnMoves implements MoveStrategy {
    @Override
    public void legalMoves(Game game, Piece pawn, List<Triplet<Piece, Integer, Integer>> list) {
        int pawnX = pawn.getXposition();
        int pawnY = pawn.getYposition();
        if (pawn.isWhite()) {
            if (game.validMove(pawn, pawnX - 1, pawnY - 1)) {
                list.add(new Triplet<>(pawn, pawnX - 1, pawnY - 1));
            }
            if (game.validMove(pawn, pawnX + 1, pawnY - 1)) {
                list.add(new Triplet<>(pawn, pawnX + 1, pawnY - 1));
            }
            if (game.validMove(pawn, pawnX, pawnY - 1)) {
                list.add(new Triplet<>(pawn, pawnX, pawnY - 1));
                if (pawnY == 6 && game.validMove(pawn, pawnX, 4)) { // 2 square move
                    list.add(new Triplet<>(pawn, pawnX, 4));
                }
            }
        } else {
            if (game.validMove(pawn, pawnX,pawnY + 1)) {
                list.add(new Triplet<>(pawn, pawnX, pawnY + 1));

                if (pawnY == 1 && game.validMove(pawn, pawnX, 3)) {  // 2 square move
                    list.add(new Triplet<>(pawn, pawnX, 3));
                }
            }
                if (game.validMove(pawn, pawnX - 1, pawnY + 1)) {
                    list.add(new Triplet<>(pawn, pawnX - 1, pawnY + 1));
                }
                if (game.validMove(pawn, pawnX + 1, pawnY + 1)) {
                    list.add(new Triplet<>(pawn, pawnX + 1, pawnY + 1));
                }
        }
    }


    @Override
    public Boolean canMove(Game game, Piece p, int nextX, int nextY) {
        Boolean b = false;
        if (p.canMove(p.getXposition(), p.getYposition(), nextX, nextY)
                && game.getBd().getPiece(nextX, nextY) == null) {
            b = true;
        }
        if (p.isWhite()) {
            if (abs(nextX - p.getXposition()) == 1
                    && nextY == p.getYposition() - 1
                    && game.getBd().getPiece(nextX, nextY) != null) {
                b = true;
            }
        }
        if (!p.isWhite()) {
            if (abs(nextX - p.getXposition()) == 1
                    && nextY == p.getYposition() + 1
                    && game.getBd().getPiece(nextX, nextY) != null) {
                b = true;
            }
        }
        return b;    }
}
