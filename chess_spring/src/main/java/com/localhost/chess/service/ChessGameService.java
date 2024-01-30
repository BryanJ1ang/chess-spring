package com.localhost.chess.service;

import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.model.pieces.Piece;
import com.localhost.chess.web.MoveResource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChessGameService {
    private Engine engine;
    private int depth = 4;
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private final Map<String, String> multiplayerGames = new ConcurrentHashMap<>();

    public String startNewGame() {
        Game newGame = new Game("default", null, true);
        String gameId = generateUniqueId();
        engine = new Engine();
        games.put(gameId, newGame);
        return gameId;
    }

    public Boolean validMove(MoveResource move) {
        Triplet<Piece, Integer, Integer> tripletMove = convertToTriplet(move);
        return games.get(move.getGameId()).validMove(tripletMove.getValue0(), tripletMove.getValue1(), tripletMove.getValue2());
    }

    public void updateGame(MoveResource moveResource) {
        Game game = games.get(moveResource.getGameId());
        Triplet<Piece, Integer, Integer> move = convertToTriplet(moveResource);
        game.movePiece(move.getValue0(), move.getValue1(), move.getValue2());
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public String runEngine(String gameId) {
        System.out.println("Running chess engine for game: " + gameId);
        Game game = games.get(gameId);
        Triplet<Piece, Integer, Integer> bestMove =  engine.returnBestMove(game, depth);
        String bestMoveString = convertToString(bestMove);
        game.movePiece(bestMove.getValue0(), bestMove.getValue1(), bestMove.getValue2());
        return bestMoveString;
    }

    // Converts Move to string in format of 6 characters
    // (Colour, Type of Piece, prevX, prevY, nextX, nextY)
    // e.g. (BQ2031) moves the black queen from (2,0) to square (3,1)
    public String convertToString(Triplet<Piece, Integer, Integer> move) {
        String moveString;
        if (move.getValue0().isWhite()) {
            moveString = "W";
        } else {
            moveString = "B";
        }
        moveString = moveString + move.getValue0().getType().charAt(0) +
        move.getValue0().getXposition() + move.getValue0().getYposition() + move.getValue1() + move.getValue2();
        return moveString;
    }

    // Converts String format to Triplet
    public Triplet<Piece, Integer, Integer> convertToTriplet(MoveResource move) {
        Game game = games.get(move.getGameId());
        String stringMove = move.getMove();
        Piece piece = game.getBd().getPiece(stringMove.charAt(2) - '0', stringMove.charAt(3) - '0');
        return new Triplet<>(piece, stringMove.charAt(4) - '0', stringMove.charAt(5) - '0');
    }

    public Map<String, Game> getGames() {
        return games;
    }
    public Map<String, String> getMultiplayerGames() {
        return multiplayerGames;
    }

}
