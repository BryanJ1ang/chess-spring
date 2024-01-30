package com.localhost.chess.controller;

import com.localhost.chess.model.javatuples.Triplet;
import com.localhost.chess.service.ChessGameService;
import com.localhost.chess.web.GameStatus;
import com.localhost.chess.web.MoveResource;
import com.localhost.chess.web.MoveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ChessGameService service;

    // EFFECTS: Constructor for GameController
    public GameController(ChessGameService service) {
        this.service = service;
    }

    @MessageMapping("/newGame")
    @SendTo("/topic/game-id")
    public MoveResource newGame(MoveResource move) {
        System.out.println("new Game requested");
        MoveResource moveResource = new MoveResource("", "");
        if (move.getGameId() != "") {
            if (service.getMultiplayerGames().get(move.getGameId()) != null) {
                moveResource.setGameId(service.getMultiplayerGames().get(move.getGameId()));
            } else {
                moveResource.setGameId(service.startNewGame());
                service.getMultiplayerGames().put(move.getGameId(), moveResource.getGameId()); // (lobby name, server gameId)
            }
            return moveResource;
        }
        moveResource.setGameId(service.startNewGame());
        System.out.println("new Game sending id: " + moveResource.getGameId());
        return moveResource;
    }
    public void updateMove(String gameId, MoveResource moveResource) {
        service.getGames().get(gameId).swapTurns();
        System.out.println("Sending move " + moveResource.getMove());
        messagingTemplate.convertAndSend("/topic/update-move/" + gameId, moveResource);
        service.getGames().get(gameId).updateGameStatus();
        System.out.println("game status updated");
        if (service.getGames().get(gameId).getGameStatus() != null) {
            System.out.println("sending game status");
            messagingTemplate.convertAndSend("/topic/game-status/" + gameId, new GameStatus(service.getGames().get(gameId).getGameStatus()));
        }
    }

    //EFFECTS: Returns best move
    @MessageMapping("/bestMove")
    public void getBestMove(MoveResource move) {
        String gameId = move.getGameId();
        System.out.println("Engine move requested for game: " + gameId);
        updateMove(gameId, new MoveResource(service.runEngine(gameId), gameId));
    }

    //EFFECTS: Returns best move
    @MessageMapping("/endGame")
    public void endGame(MoveResource move) {
        String gameId = move.getGameId();
        if (service.getMultiplayerGames().containsValue(gameId)) {
            service.getMultiplayerGames().values().remove(gameId);
        }
        System.out.println("Ending game: " + gameId);
        service.getGames().remove(gameId);
    }

    @MessageMapping("/requestMove")
    @SendTo("/topic/move-status")
    public MoveStatus requestMove(MoveResource move) {
        System.out.println("Client requested" + move.getMove() + " for game: " + move.getGameId());
        Boolean validMove;
        try  {
            if (service.validMove(move)) {
                System.out.println("valid move");
                System.out.println(move.getMove());
                service.updateGame(move);
                updateMove(move.getGameId(), new MoveResource(move.getMove(), move.getGameId()));
                return null;
            } else {
                System.out.println("send false move");
                return new MoveStatus(false, "Move rejected");
            }
        } catch (Exception e) {
            System.out.println("Unexpected Error!");
            return new MoveStatus(false, "Unexpected error has occurred!");
        }
    }
}
