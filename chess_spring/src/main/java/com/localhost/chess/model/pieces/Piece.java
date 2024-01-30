package com.localhost.chess.model.pieces;

import com.localhost.chess.service.movestrategy.MoveStrategy;

// Abstract class for the pieces in chess
public abstract class Piece {
    protected int xposition;
    protected int yposition;
    protected Boolean white;
    protected Boolean moved = false;
    protected MoveStrategy moveStrategy;

    public Piece(String colour) {
        moved = false;
        String x = colour.toLowerCase();
        if (x.equals("white")) {
            white = true;
        }
        if (x.equals("black")) {
            white = false;
        }
    }

    public Piece(Boolean colour) {
        white = colour;
        moved = false;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoved() {
        moved = true;
    }

    // EFFECT: true if piece can move to given location
    public abstract Boolean canMove(int currentx, int curruenty, int x, int y);

    // MODIFIES: this
    // EFFECT: sets current coordinates for piece
    public void setPositions(int x, int y) {
        xposition = x;
        yposition = y;
    }

    public Boolean removed() {
        return xposition == -1 || yposition == -1 || xposition == 8 || yposition == 8;
    }

    public abstract String getType();

    public Boolean isWhite() {
        return white;
    }

    public Boolean getMoved() {
        return moved;
    }

    public int getXposition() {
        return xposition;
    }

    public int getYposition() {
        return yposition;
    }

}

