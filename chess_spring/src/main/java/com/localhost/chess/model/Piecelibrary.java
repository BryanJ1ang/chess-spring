package com.localhost.chess.model;

import com.localhost.chess.model.pieces.*;

import java.util.ArrayList;

// represents library of pieces available to add to chess board
public class Piecelibrary {

    private final ArrayList<Piece> whitepawns = customWhitePawns();
    private final ArrayList<Piece> blackpawns = customBlackPawns();
    private final ArrayList<Piece> whitequeens = customWhiteQueens();
    private final ArrayList<Piece> blackqueens = customBlackQueens();
    private final ArrayList<Piece> whitebishops = customWhiteBishops();
    private final ArrayList<Piece> blackbishops = customBlackBishops();
    private final ArrayList<Piece> whiterooks = customWhiteRooks();
    private final ArrayList<Piece> blackrooks = customBlackRooks();
    private final ArrayList<Piece> whiteknights = customWhiteKnights();
    private final ArrayList<Piece> blackknights = customBlackKnights();


    private Piece whiteking = new King("White");
    private Piece blackking = new King("Black");

    private Piece piece = null;


    // REQUIRES: colour is one of ("WHITE" "BLACK")
    //           type is one of ("KING" "QUEEN" "ROOK" "BISHOP" "PAWN" "KNIGHT")
    // MODIFIES: this
    // EFFECTS: returns a specified piece from library
    public Piece retrievePieceFromLibrary(String type, String colour) {
        if (colour.equals("WHITE")) {
            retrieveWhitePieceFromList(type);
        }
        if (colour.equals("BLACK")) {
            retrieveBlackPieceFromList(type);
        }
        return piece;
    }

    // REQUIRES: type is one of ("KING" "QUEEN" "ROOK" "BISHOP" "PAWN" "KNIGHT")
    // MODIFIES: this
    //EFFECTS: returns a specified white piece from library
    private void retrieveWhitePieceFromList(String type) {
        if (type.equals("KING")) {
            piece = whiteking;
        }
        if (type.equals("QUEEN")) {
            piece = whitequeens.get(0);
            whitequeens.remove(0);
        }
        if (type.equals("ROOK")) {
            piece = whiterooks.get(0);
            whiterooks.remove(0);
        }
        if (type.equals("KNIGHT")) {
            piece = whiteknights.get(0);
            whiteknights.remove(0);
        }
        if (type.equals("BISHOP")) {
            piece = whitebishops.get(0);
            whitebishops.remove(0);
        }
        if (type.equals("PAWN")) {
            piece = whitepawns.get(0);
            whitepawns.remove(0);
        }
    }

    // REQUIRES: type is one of ("KING" "QUEEN" "ROOK" "BISHOP" "PAWN" "KNIGHT")
    // MODIFIES: this
    //EFFECTS: returns a specified black piece from library
    private void retrieveBlackPieceFromList(String type) {
        if (type.equals("KING")) {
            piece = blackking;
        }
        if (type.equals("QUEEN")) {
            piece = blackqueens.get(0);
            blackqueens.remove(0);
        }
        if (type.equals("ROOK")) {
            piece = blackrooks.get(0);
            blackrooks.remove(0);
        }
        if (type.equals("KNIGHT")) {
            piece = blackknights.get(0);
            blackknights.remove(0);
        }
        if (type.equals("BISHOP")) {
            piece = blackbishops.get(0);
            blackbishops.remove(0);
        }
        if (type.equals("PAWN")) {
            piece = blackpawns.get(0);
            blackpawns.remove(0);
        }
    }


    // EFFECT: returns list of 8 distinct white pawns
    private ArrayList<Piece> customWhitePawns() {
        ArrayList<Piece> pawns = new ArrayList<Piece>();
        Pawn p1 = new Pawn("White");
        Pawn p2 = new Pawn("White");
        Pawn p3 = new Pawn("White");
        Pawn p4 = new Pawn("White");
        Pawn p5 = new Pawn("White");
        Pawn p6 = new Pawn("White");
        Pawn p7 = new Pawn("White");
        Pawn p8 = new Pawn("White");
        pawns.add(p1);
        pawns.add(p2);
        pawns.add(p3);
        pawns.add(p4);
        pawns.add(p5);
        pawns.add(p6);
        pawns.add(p7);
        pawns.add(p8);
        return pawns;
    }

    // EFFECT: returns list of 8 distinct black pawns
    private ArrayList<Piece> customBlackPawns() {
        ArrayList<Piece> pawns = new ArrayList<Piece>();
        Pawn p9 = new Pawn("Black");
        Pawn p10 = new Pawn("Black");
        Pawn p11 = new Pawn("Black");
        Pawn p12 = new Pawn("Black");
        Pawn p13 = new Pawn("Black");
        Pawn p14 = new Pawn("black");
        Pawn p15 = new Pawn("Black");
        Pawn p16 = new Pawn("Black");
        pawns.add(p9);
        pawns.add(p10);
        pawns.add(p11);
        pawns.add(p12);
        pawns.add(p13);
        pawns.add(p14);
        pawns.add(p15);
        pawns.add(p16);
        return pawns;
    }

    // EFFECT: returns list of 8 distinct white queens
    private ArrayList<Piece> customWhiteQueens() {
        ArrayList<Piece> queens = new ArrayList<Piece>();
        Queen q1 = new Queen("White");
        Queen q2 = new Queen("White");
        Queen q3 = new Queen("White");
        Queen q4 = new Queen("White");
        Queen q5 = new Queen("White");
        Queen q6 = new Queen("White");
        Queen q7 = new Queen("White");
        Queen q8 = new Queen("White");
        queens.add(q1);
        queens.add(q2);
        queens.add(q3);
        queens.add(q4);
        queens.add(q5);
        queens.add(q6);
        queens.add(q7);
        queens.add(q8);
        return queens;
    }

    // EFFECT: returns list of 8 distinct black queens
    private ArrayList<Piece> customBlackQueens() {
        ArrayList<Piece> queens = new ArrayList<Piece>();
        Queen q9 = new Queen("Black");
        Queen q10 = new Queen("Black");
        Queen q11 = new Queen("Black");
        Queen q12 = new Queen("Black");
        Queen q13 = new Queen("Black");
        Queen q14 = new Queen("Black");
        Queen q15 = new Queen("Black");
        Queen q16 = new Queen("Black");
        queens.add(q9);
        queens.add(q10);
        queens.add(q11);
        queens.add(q12);
        queens.add(q13);
        queens.add(q14);
        queens.add(q15);
        queens.add(q16);
        return queens;
    }

    // EFFECT: returns list of 8 distinct white rooks
    private ArrayList<Piece> customWhiteRooks() {
        ArrayList<Piece> rooks = new ArrayList<Piece>();
        Rook r1 = new Rook("White");
        Rook r2 = new Rook("White");
        Rook r3 = new Rook("White");
        Rook r4 = new Rook("White");
        Rook r5 = new Rook("White");
        Rook r6 = new Rook("White");
        Rook r7 = new Rook("White");
        Rook r8 = new Rook("White");
        rooks.add(r1);
        rooks.add(r2);
        rooks.add(r3);
        rooks.add(r4);
        rooks.add(r5);
        rooks.add(r6);
        rooks.add(r7);
        rooks.add(r8);
        return rooks;
    }

    // EFFECT: returns list of 8 distinct black rooks
    private ArrayList<Piece> customBlackRooks() {
        ArrayList<Piece> rooks = new ArrayList<Piece>();
        Rook r9 = new Rook("Black");
        Rook r10 = new Rook("Black");
        Rook r11 = new Rook("Black");
        Rook r12 = new Rook("Black");
        Rook r13 = new Rook("Black");
        Rook r14 = new Rook("Black");
        Rook r15 = new Rook("Black");
        Rook r16 = new Rook("Black");
        rooks.add(r9);
        rooks.add(r10);
        rooks.add(r11);
        rooks.add(r12);
        rooks.add(r13);
        rooks.add(r14);
        rooks.add(r15);
        rooks.add(r16);
        return rooks;
    }

    // EFFECT: returns list of 8 distinct white bishops
    private ArrayList<Piece> customWhiteBishops() {
        ArrayList<Piece> bishops = new ArrayList<Piece>();
        Bishop b1 = new Bishop("White");
        Bishop b2 = new Bishop("White");
        Bishop b3 = new Bishop("White");
        Bishop b4 = new Bishop("White");
        Bishop b5 = new Bishop("White");
        Bishop b6 = new Bishop("White");
        Bishop b7 = new Bishop("White");
        Bishop b8 = new Bishop("White");
        bishops.add(b1);
        bishops.add(b2);
        bishops.add(b3);
        bishops.add(b4);
        bishops.add(b5);
        bishops.add(b6);
        bishops.add(b7);
        bishops.add(b8);
        return bishops;
    }

    // EFFECT: returns list of 8 distinct black bishops
    private ArrayList<Piece> customBlackBishops() {
        ArrayList<Piece> bishops = new ArrayList<Piece>();
        Bishop b9 = new Bishop("Black");
        Bishop b10 = new Bishop("Black");
        Bishop b11 = new Bishop("Black");
        Bishop b12 = new Bishop("Black");
        Bishop b13 = new Bishop("Black");
        Bishop b14 = new Bishop("Black");
        Bishop b15 = new Bishop("Black");
        Bishop b16 = new Bishop("Black");
        bishops.add(b9);
        bishops.add(b10);
        bishops.add(b11);
        bishops.add(b12);
        bishops.add(b13);
        bishops.add(b14);
        bishops.add(b15);
        bishops.add(b16);
        return bishops;
    }


    // EFFECT: returns list of 8 distinct white knights
    private ArrayList<Piece> customWhiteKnights() {
        ArrayList<Piece> knights = new ArrayList<Piece>();
        Knight kn1 = new Knight("White");
        Knight kn2 = new Knight("White");
        Knight kn3 = new Knight("White");
        Knight kn4 = new Knight("White");
        Knight kn5 = new Knight("White");
        Knight kn6 = new Knight("White");
        Knight kn7 = new Knight("White");
        Knight kn8 = new Knight("White");
        knights.add(kn1);
        knights.add(kn2);
        knights.add(kn3);
        knights.add(kn4);
        knights.add(kn5);
        knights.add(kn6);
        knights.add(kn7);
        knights.add(kn8);
        return knights;
    }

    // EFFECT: returns list of 8 distinct black knights
    private ArrayList<Piece> customBlackKnights() {
        ArrayList<Piece> knights = new ArrayList<Piece>();
        Knight kn9 = new Knight("Black");
        Knight kn10 = new Knight("Black");
        Knight kn11 = new Knight("Black");
        Knight kn12 = new Knight("Black");
        Knight kn13 = new Knight("Black");
        Knight kn14 = new Knight("Black");
        Knight kn15 = new Knight("Black");
        Knight kn16 = new Knight("Black");
        knights.add(kn9);
        knights.add(kn10);
        knights.add(kn11);
        knights.add(kn12);
        knights.add(kn13);
        knights.add(kn14);
        knights.add(kn15);
        knights.add(kn16);
        return knights;
    }
}
