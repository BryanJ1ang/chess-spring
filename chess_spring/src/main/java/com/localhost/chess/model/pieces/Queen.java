package com.localhost.chess.model.pieces;
import com.localhost.chess.service.movestrategy.QueenMoves;

import static java.lang.Math.abs;

// Represents queen piece
public class Queen extends Piece {
    private final String type = "Queen";

    // EFFECTS: Constructor for Queen
    public Queen(String colour) {
        super(colour);
        moveStrategy = new QueenMoves();
    }

    // EFFECTS: Constructor for Queen
    public Queen(Boolean colour) {
        super(colour);
        moveStrategy = new QueenMoves();
    }

    // REQUIRES: parameters are all values between [0,7] inclusive
    // EFFECT: return true if piece can move to specified position
    public Boolean canMove(int currentx, int currenty, int nextx, int nexty) {
        if (nextx < 0 || nextx > 7 || nexty < 0 || nexty > 7) {
            return false;
        }
        return (abs(currentx - nextx) == abs(currenty - nexty)) || ((currentx == nextx) || (currenty == nexty));
    }

    public String getType() {
        return type;
    }
}
