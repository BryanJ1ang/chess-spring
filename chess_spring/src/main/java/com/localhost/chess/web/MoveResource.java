package com.localhost.chess.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Resource;

// Moves are conveyed in format of 6 characters
// (Colour, Type of Piece, prevX, prevY, nextX, nextY)
// e.g. (BQ2031) moves the black queen from (2,0) to square (3,1)
@Resource
public class MoveResource {
    private String move;
    private String gameId;

    public MoveResource() {

    }
    public MoveResource(String move, String gameId) {
        this.move = move;
        this.gameId = gameId;
    }

    public String getMove() {
        return move;
    }

    @JsonProperty("move")
    public void setMove(String move) {
        this.move = move;
    }

    @JsonProperty("gameId")
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
