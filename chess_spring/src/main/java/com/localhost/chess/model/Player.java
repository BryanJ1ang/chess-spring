package com.localhost.chess.model;

import com.localhost.chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

// Represents a player in a game of chess
public class Player {

    private Boolean white;

    private final List<Piece> pieces = new ArrayList<Piece>();
    private final List<Piece> rooks = new ArrayList<Piece>();
    private final List<Piece> queens = new ArrayList<Piece>();
    private final List<Piece> bishops = new ArrayList<Piece>();
    private final List<Piece> knights = new ArrayList<Piece>();
    private final List<Piece> pawns = new ArrayList<Piece>();


    // EFFECTS: Constructor for Player
    public Player(Boolean color) {
        white = color;
    }

    // MODIFIES: this
    // EFFECTS: adds piece to list of pieces belonging to player
    public void addPiece(Piece p) {
        pieces.add(p);
        if (p instanceof Rook) {
            rooks.add(p);
        } else if (p instanceof Bishop) {
            bishops.add(p);
        } else if (p instanceof Queen) {
            queens.add(p);
        } else if (p instanceof Knight) {
            knights.add(p);
        } else if (p instanceof Pawn) {
            pawns.add(p);
        }
    }

    // EFFECTS: Removes player from list
    public void removePiece(Piece p) {
        pieces.remove(p);
        if (p instanceof Rook) {
            rooks.remove(p);
        } else if (p instanceof Bishop) {
            bishops.remove(p);
        } else if (p instanceof Queen) {
            queens.remove(p);
        } else if (p instanceof Knight) {
            knights.remove(p);
        }
    }
    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Piece> getQueens() {
        return queens;
    }

    public List<Piece> getBishops() {
        return bishops;
    }

    public List<Piece> getKnights() {
        return knights;
    }

    public List<Piece> getRooks() {
        return rooks;
    }
}
