package com.localhost.chess.model.pieces;

import com.localhost.chess.service.Game;
import com.localhost.chess.service.movestrategy.PawnMoves;

import java.util.ArrayList;
import java.util.List;

// Represents pawn piece
public class Pawn extends Piece {
    private final String type = "Pawn";
    private Boolean enPassant = false; // true if this piece vulnerable to en passant
    private Game game = null;
    static List<Pawn> pieces = new ArrayList<>();

    // REQUIRES: Colour is one of: "White" "Black"
    // EFFECTS: Constructor for pawn


    public Pawn(String colour) {
        super(colour);
        moveStrategy = new PawnMoves();
    }

    public Pawn(Boolean colour) {
        super(colour);
        moveStrategy = new PawnMoves();
    }

    // REQUIRES: parameters are all values between [0,7] inclusive
    // EFFECT: return true if piece can move to specified position
    public Boolean canMove(int currentx, int currenty, int nextx, int nexty) {
        if (nextx < 0 || nextx > 7 || nexty < 0 || nexty > 7) {
            return false;
        }
        if (white == false) {
            if (((currenty == 1))) {
                if ((currentx == nextx) && nexty == currenty + 2) {
                    return true;
                }
                return ((currentx == nextx) && ((nexty == currenty + 1)));
            } else {
                return (currentx == nextx) && ((nexty == currenty + 1));
            }

        } else {
            if (((currenty == 6))) {
                if ((currentx == nextx) && nexty == currenty - 2) {
                    return true;
                }
                return (currentx == nextx) && ((nexty == currenty - 1) || (nexty == currenty - 2));
            } else {
                return (currentx == nextx) && ((nexty == currenty - 1));
            }
        }
    }

    @Override
    // EFFECTS: Changes position of this Pawn
    public void setPositions(int x, int y) {
        xposition = x;
        yposition = y;
        if (this.isWhite()) {
            if (y == 4 && !getMoved()) {
                markEnPassant();
            }
        } else if (y == 3 && !getMoved()) {
            markEnPassant();
        }
    }

    public String getType() {
        return type;
    }

    // REQUIRES: canEnPassant is true for x/y
    // EFFECTS: captures opponent piece by En Passant
    public void EnPassant(int x, int y) {
        if (white) {
            game.getBd().movePiece(this, x, y + 1);
        } else {
            game.getBd().movePiece(this, x, y - 1);
        }
    }

    // EFFECTS: return true if piece can en passant to given square
    public Boolean canEnPassant(Game g, int x, int y) {
        game = g;

        if (white) {
            return whiteEnPassant(x, y);
        } else {
            return blackEnPassant(x, y);
        }
    }

    // REQUIRES: this is white
    // EFFECTS: return true if piece can en passant to given square
    private Boolean whiteEnPassant(int x, int y) {
        if (!(this.getYposition() == 3)) {
            return false;
        } else if (game.getBd().getPiece(x,y) != null) {
            return false;
        } else if (!isBlackPawn(x, y + 1)) {
            return false;
        } else {
            Pawn p = (Pawn) game.getBd().getPiece(x, y + 1);
            return p.enPassant;
        }
    }

    // REQUIRES: this is black
    // EFFECTS: return true if piece can en passant to given square
    private Boolean blackEnPassant(int x, int y) {
        if (!(this.getYposition() == 4)) {
            return false;
        } else if (game.getBd().getPiece(x,y) != null) {
            return false;
        } else if (!isWhitePawn(x, y - 1)) {
            return false;
        } else {
            Pawn p = (Pawn) game.getBd().getPiece(x, y - 1);
            return p.enPassant;
        }
    }


    // EFFECT: return true if selected square has a white pawn
    private Boolean isWhitePawn(int x, int y) {
        Piece piece = game.getBd().getPiece(x, y);
        if (piece == null) {
            return false;
        }
        return piece instanceof Pawn && piece.white;
    }


    // EFFECT: return true if selected square has a black pawn
    private Boolean isBlackPawn(int x, int y) {
        Piece piece = game.getBd().getPiece(x, y);
        if (piece == null) {
            return false;
        }
        return piece instanceof Pawn && !piece.white;
    }

    // EFFECT: sets en passant to false for all instances of Pawn
    public static void updatePiece() {
        for (Pawn p : pieces) {
            p.enPassant = false;
        }
        pieces = new ArrayList<>();
    }

    // EFFECTS: Adds
    public void addPiece() {
        pieces.add(this);
    }

    public void markEnPassant() {
        pieces.add(this);
        enPassant = true;
    }

    public Boolean getEnPassant() {
        return enPassant;
    }

    // EFFECTS: return true if Pawn can be promoted
    public Boolean canPromote() {
        if (white && getYposition() == 0) {
            return true;
        } else if (!white && getYposition() == 7) {
            return true;
        } else {
            return false;
        }
    }
}
