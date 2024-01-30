package com.localhost.chess.model;

import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.*;
import com.localhost.chess.service.Game;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

// Class representing check/mate
public class Check {
    Game game;
    King kingWhite; // white king
    King kingBlack; // black king

    // EFFECTS: Constructor for checkmate
    public Check(Game g) {
        game = g;
        for (Piece p : g.getPlayer1().getPieces()) {
            if (p instanceof King) {
                kingWhite = (King) p;
                break;
            }
        }

        for (Piece p : g.getPlayer2().getPieces()) {
            if (p instanceof King) {
                kingBlack = (King) p;
                break;
            }
        }
    }

    // EFFECTS: return true if one side is checkmated
    //          (only one side can be in check mate at any given time)
    public Boolean isCheckMate() {
        return ((whiteCheck() && whiteCheckMate()) || (blackCheckMate() && blackCheck()));
    }

    // EFFECTS: true if moving player of given piece is in check after moving to (x,y)
    public Boolean moveIntoCheck(Piece piece, int nextX, int nextY) {
        Piece capturedPiece = null;
        int originalX = piece.getXposition();
        int originalY = piece.getYposition();
        boolean check;
        boolean enpassant = false;
        if (piece instanceof King) {
            if (piece.isWhite() && (0 == abs(nextX - kingBlack.getXposition()) || abs(nextX - kingBlack.getXposition()) == 1)
                    && (0 == abs(nextY - kingBlack.getYposition()) || abs(nextY - kingBlack.getYposition()) == 1)) {
                return true;
            } else if (!piece.isWhite() && (0 == abs(nextX - kingWhite.getXposition()) || abs(nextX - kingWhite.getXposition()) == 1)
                    && (0 == abs(nextY - kingWhite.getYposition()) || abs(nextY - kingWhite.getYposition()) == 1)) {
                return true;
            }
        }
        if (game.getBd().getPiece(nextX, nextY) != null) {
            capturedPiece = game.getBd().getPiece(nextX, nextY);
            if (!(capturedPiece instanceof King)) {
                capturedPiece.setPositions(-1,-1);
            }
        } else if (piece instanceof Pawn) {
            //stub for retrieving enpassant
        }

        game.getBd().movePiece(piece, nextX, nextY);
        if (piece.isWhite()) {
            check = whiteCheck();
        } else {
            check = blackCheck();
        }
        game.getBd().movePiece(piece, originalX, originalY);
        if (capturedPiece != null) {
            game.getBd().addPiece(capturedPiece, nextX, nextY);
            if (!(capturedPiece instanceof King)) {
                capturedPiece.setPositions(nextX, nextY);
            }
        }
        return check;
    }

    // REQUIRES: White is in check
    // EFFECTS: return true if no valid moves for white/player1
    public Boolean whiteCheckMate() {
        List<Piece> pieces = game.getPlayer1().getPieces();
        List<Triplet<Piece, Integer, Integer>> legalMoves = new LinkedList<>();
        for (Piece p : pieces) {
            p.getMoveStrategy().legalMoves(game,p,legalMoves);
            if (legalMoves.size() != 0) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: Black is in check
    // EFFECTS: return true if no valid moves for black/player2
    public Boolean blackCheckMate() {
        List<Piece> pieces = game.getPlayer2().getPieces();
        List<Triplet<Piece, Integer, Integer>> legalMoves = new LinkedList<>();
        for (Piece p : pieces) {
            p.getMoveStrategy().legalMoves(game,p,legalMoves);
            if (legalMoves.size() > 0) {
                return false;
            }
        }
        return true;
    }


    // EFFECTS: return true if king is in check from above
    private Boolean searchUp(Piece king) {
        for (int y = king.getYposition() - 1; y >= 0; y--) {
            if (game.getBd().getPiece(king.getXposition(), y) != null) {
                Piece piece = game.getBd().getPiece(king.getXposition(), y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Rook || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check from below
    private Boolean searchDown(Piece king) {
        for (int y = king.getYposition() + 1; y <= 7; y++) {
            if (game.getBd().getPiece(king.getXposition(), y) != null) {
                Piece piece = game.getBd().getPiece(king.getXposition(), y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Rook || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check from left
    private Boolean searchLeft(Piece king) {
        for (int x = king.getXposition() - 1; x >= 0; x--) {
            if (game.getBd().getPiece(x, king.getYposition()) != null) {
                Piece piece = game.getBd().getPiece(x, king.getYposition());
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Rook || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check from right
    private Boolean searchRight(Piece king) {
        for (int x = king.getXposition() + 1; x <= 7; x++) {
            if (game.getBd().getPiece(x, king.getYposition()) != null) {
                Piece piece = game.getBd().getPiece(x, king.getYposition());
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Rook || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check diagonally from bottom right
    private Boolean searchDiagonalBottomRight(Piece king) {
        int y = king.getYposition();
        for (int x = king.getXposition() + 1; x <= 7; x++) {
            y++;
            if (x > 7 || y > 7) {
                break; // out of bounds
            }
            if (game.getBd().getPiece(x, y) != null) {
                Piece piece = game.getBd().getPiece(x, y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Bishop || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check diagonally from top right
    private Boolean searchDiagonalTopRight(Piece king) {
        int y = king.getYposition();
        for (int x = king.getXposition() + 1; x <= 7; x++) {
            y--;
            if (x > 7 || y < 0) {
                break; // out of bounds
            }
            if (game.getBd().getPiece(x, y) != null) {
                Piece piece = game.getBd().getPiece(x, y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Bishop || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }


    // EFFECTS: return true if king is in check diagonally from top left
    private Boolean searchDiagonalTopLeft(Piece king) {
        int y = king.getYposition();
        for (int x = king.getXposition() - 1; x >= 0; x--) {
            y--;
            if (x < 0 || y < 0) {
                break; // out of bounds
            }
            if (game.getBd().getPiece(x, y) != null) {
                Piece piece = game.getBd().getPiece(x, y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Bishop || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }

    // EFFECTS: return true if king is in check diagonally from top left
    private Boolean searchDiagonalBottomLeft(Piece king) {
        int y = king.getYposition();
        for (int x = king.getXposition() - 1; x >= 0; x--) {
            y++;
            if (x < 0 || y > 7) {
                break; // out of bounds
            }
            if (game.getBd().getPiece(x, y) != null) {
                Piece piece = game.getBd().getPiece(x, y);
                if (piece.isWhite() == king.isWhite()) {
                    return false; // Same color piece
                } else if (piece instanceof Bishop || piece instanceof Queen) {
                    return true; // Rook or Queen has line of sight to King
                } else {
                    return false; // Any other piece cannot put king in check
                }
            }
        }
        return false;
    }


    // EFFECTS: Returns true if white king is in check
    public Boolean whiteCheck() {
        for (int x = kingWhite.getXposition() - 1; x <= kingWhite.getXposition() + 1; x++) {
            for (int y = kingWhite.getYposition() - 1; y <= kingWhite.getYposition() + 1; y++) {
                if (x >= 0 && x < 8 && y >= 0 && y < 8) { //checks if in bound
                    if (x == kingWhite.getXposition() && y == kingWhite.getYposition()) {
                        continue; // same square as king
                    }
                    Piece piece = game.getBd().getPiece(x, y);
                    if (piece != null) {
                        if (piece.isWhite()) {
                            continue;
                        } else if (game.validMove(piece, kingWhite.getXposition(), kingWhite.getYposition())) { // covers pawn check
                            return true;
                        }
                    } else { // piece null, king not protected
                        if (x == kingWhite.getXposition() && y == kingWhite.getYposition() + 1) {
                            if (searchDown(kingWhite)) {
                                return true;
                            }
                        } else if (x == kingWhite.getXposition() && y == kingWhite.getYposition() - 1) {
                            if (searchUp(kingWhite)) {
                                return true;
                            } // search above  king
                        } else if (x == kingWhite.getXposition() + 1 && y == kingWhite.getYposition()) {
                            if (searchRight(kingWhite)) {
                                return true;
                            } // search to the right of king
                        } else if (x == kingWhite.getXposition() - 1 && y == kingWhite.getYposition()) {
                            if (searchLeft(kingWhite)) {
                                return true;
                            } // search to left of king
                        } else if (x == kingWhite.getXposition() + 1 && y == kingWhite.getYposition() + 1) {
                            if (searchDiagonalBottomRight(kingWhite)) {
                                return true;
                            } // search bottom right diagonal
                        } else if (x == kingWhite.getXposition() + 1 && y == kingWhite.getYposition() - 1) {
                            if (searchDiagonalTopRight(kingWhite)) {
                                return true;
                            }
                        } else if (x == kingWhite.getXposition() - 1 && y == kingWhite.getYposition() - 1) {
                            if (searchDiagonalTopLeft(kingWhite)) {
                                return true;
                            }   // search top left diagonal
                        } else if (x == kingWhite.getXposition() - 1 && y == kingWhite.getYposition() + 1) {
                            if (searchDiagonalBottomLeft(kingWhite)) {
                                return true;
                            }   // search bottom left diagonal
                        }
                    }
                }
            }
        }

        for (Piece knight : game.getPlayer2().getKnights()) {
            if (game.validMove(knight, kingWhite.getXposition(), kingWhite.getYposition())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns true if white king is in check
    public Boolean blackCheck() {
        for (int x = kingBlack.getXposition() - 1; x <= kingBlack.getXposition() + 1; x++) {
            for (int y = kingBlack.getYposition() - 1; y <= kingBlack.getYposition() + 1; y++) {
                if (x >= 0 && x < 8 && y >= 0 && y < 8) { //checks if in bound
                    if (x == kingBlack.getXposition() && y == kingBlack.getYposition()) {
                        continue; // same square as king
                    }
                    Piece piece = game.getBd().getPiece(x, y);
                    if (piece != null) {
                        if (!piece.isWhite()) {
                            continue;
                        } else if (game.validMove(piece, kingBlack.getXposition(), kingBlack.getYposition())) { // covers pawn check
                            return true;
                        }
                    } else { // piece null, king not protected
                        if (x == kingBlack.getXposition() && y == kingBlack.getYposition() + 1) {
                            if (searchDown(kingBlack)) {
                                return true;
                            }
                        } else if (x == kingBlack.getXposition() && y == kingBlack.getYposition() - 1) {
                            if (searchUp(kingBlack)) {
                                return true;
                            } // search above  king
                        } else if (x == kingBlack.getXposition() + 1 && y == kingBlack.getYposition()) {
                            if (searchRight(kingBlack)) {
                                return true;
                            } // search to the right of king
                        } else if (x == kingBlack.getXposition() - 1 && y == kingBlack.getYposition()) {
                            if (searchLeft(kingBlack)) {
                                return true;
                            } // search to left of king
                        } else if (x == kingBlack.getXposition() + 1 && y == kingBlack.getYposition() + 1) {
                            if (searchDiagonalBottomRight(kingBlack)) {
                                return true;
                            } // search bottom right diagonal
                        } else if (x == kingBlack.getXposition() + 1 && y == kingBlack.getYposition() - 1) {
                            if (searchDiagonalTopRight(kingBlack)) {
                                return true;
                            }
                        } else if (x == kingBlack.getXposition() - 1 && y == kingBlack.getYposition() - 1) {
                            if (searchDiagonalTopLeft(kingBlack)) {
                                return true;
                            }   // search top left diagonal
                        } else if (x == kingBlack.getXposition() - 1 && y == kingBlack.getYposition() + 1) {
                            if (searchDiagonalBottomLeft(kingBlack)) {
                                return true;
                            }   // search bottom left diagonal
                        }
                    }
                }
            }
        }

        for (Piece knight : game.getPlayer1().getKnights()) {
            if (game.validMove(knight, kingBlack.getXposition(), kingBlack.getYposition())) {
                return true;
            }
        }
        return false;
    }
}
