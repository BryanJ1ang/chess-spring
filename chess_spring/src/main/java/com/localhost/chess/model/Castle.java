package com.localhost.chess.model;

import com.localhost.chess.model.pieces.King;
import com.localhost.chess.model.pieces.Piece;
import com.localhost.chess.model.pieces.Rook;
import com.localhost.chess.service.Game;

// Class handling castle movement
public class Castle {
    Game game;

    public Castle(Game g) {
        game = g;
    }

    // REQUIRES: Conditions for castling are met
    // EFFECTS: Moves rook to appropriate position
    public void castle(int x, int y) {
        if (x == 2 && y == 7) {
            Piece p = game.getBd().getPiece(0, 7);
            game.movePiece(p, 3, 7);
        } else if (x == 6 && y == 7) {
            Piece p = game.getBd().getPiece(7, 7);
            game.movePiece(p, 5, 7);
        } else if (x == 2 && y == 0) {
            Piece p = game.getBd().getPiece(0, 0);
            game.movePiece(p, 3, 0);
        } else if (x == 6 && y == 0) {
            Piece p = game.getBd().getPiece(7, 0);
            game.movePiece(p, 5, 0);
        }
    }

    // EFFECTS: return true if castling available and x/y are appropriate position
    //          conditions for castling: King and Rook has not moved
    //                                   No pieces in between them
    //                                   King does not leave/cross/finish a threatened square
    public Boolean canCastle(King k, int x, int y) {
        if (k.isWhite() && !k.getMoved()) {
            Check check = new Check(game);
            if (check.whiteCheck()) {
                return false;
            } else if (x == 2 && y == 7) {
                return whiteKingLeftCastle();
            } else if (x == 6 && y == 7) {
                return whiteKingRightCastle();
            }
        } else if (!k.isWhite() && !k.getMoved()) {
            Check check = new Check(game);
            if (check.blackCheck()) {
                return false;
            } else if (x == 6 && y == 0) {
                return blackKingRightCastle();
            } else if (x == 2 && y == 0) {
                return blackKingLeftCastle();
            }
        }
        return false;
    }


    // REQUIRES: King has not moved and not in check
    // EFFECTS: Returns true if conditions met for white king to castle left
    private Boolean whiteKingLeftCastle() {
        // Rook has not moved
        if (!isWhiteRook(0, 7)) {
            return false;
        }

        // No pieces in between king and rook
        for (int j = 3; j > 0; j--) {
            if (!(game.getBd().getPiece(j, 7) == null)) {
                return false;
            }
        }

        // No pieces threatening king current, final and in between squares
        for (Piece p : game.getPlayer2().getPieces()) {
            if (game.getBd().validMove(p, 2, 7) || game.getBd().validMove(p, 3, 7)) {
                return false;
            }
        }

        return true;
    }


    // REQUIRES: King has not moved
    // EFFECTS: Returns true if conditions met for white king to castle right
    private Boolean whiteKingRightCastle() {
        // Rook has not moved
        if (!isWhiteRook(7, 7)) {
            return false;
        }

        // No pieces in between king and rook
        for (int j = 5; j < 7; j++) {
            if (!(game.getBd().getPiece(j, 7) == null)) {
                return false;
            }
        }

        // No pieces threatening king current, final and in between squares
        for (Piece p : game.getPlayer2().getPieces()) {
            if (game.getBd().validMove(p, 5, 7) || game.getBd().validMove(p, 6, 7)) {
                return false;
            }
        }

        return true;
    }

    // REQUIRES: King has not moved
    // EFFECTS: Returns true if conditions met for black king to castle left
    private Boolean blackKingLeftCastle() {
        // Rook has not moved
        if (!isBlackRook(0, 0)) {
            return false;
        }

        // No pieces in between king and rook
        for (int j = 3; j > 0; j--) {
            if (!(game.getBd().getPiece(j, 0) == null)) {
                return false;
            }
        }

        // No pieces threatening king current, final and in between squares
        for (Piece p : game.getPlayer1().getPieces()) {
            if (game.getBd().validMove(p, 2, 0) || game.getBd().validMove(p, 3, 0)) {
                return false;
            }
        }

        return true;
    }


    // REQUIRES: King has not moved
    // EFFECTS: Returns true if conditions met for black king to castle right
    private Boolean blackKingRightCastle() {
        // Rook has not moved
        if (!isBlackRook(7, 0)) {
            return false;
        }

        // No pieces in between king and rook
        for (int j = 5; j < 7; j++) {
            if (!(game.getBd().getPiece(j, 0) == null)) {
                return false;
            }
        }

        // No pieces threatening king current, final and in between squares
        for (Piece p : game.getPlayer1().getPieces()) {
            if (game.getBd().validMove(p, 5, 0) || game.getBd().validMove(p, 6, 0)) {
                return false;
            }
        }

        return true;
    }

    // EFFECTS: Piece at selected square is a white rook and has not moved
    private Boolean isWhiteRook(int x, int y) {
        Piece p = game.getBd().getPiece(x, y);
        if (p == null) {
            return false;
        }

        return p instanceof Rook && !p.getMoved() && p.isWhite();
    }


    // EFFECTS: Piece at selected square is a white rook and has not moved
    private Boolean isBlackRook(int x, int y) {
        Piece p = game.getBd().getPiece(x, y);
        if (p == null) {
            return false;
        }

        return p instanceof Rook && !p.getMoved() && !p.isWhite();
    }
}

