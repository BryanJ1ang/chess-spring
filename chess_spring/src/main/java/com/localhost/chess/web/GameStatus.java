package com.localhost.chess.web;

import jakarta.annotation.Resource;
import com.fasterxml.jackson.annotation.JsonProperty;


@Resource
public class GameStatus {
    private String status = "";
    public GameStatus(String status) {
        this.status = status;
    }


    @JsonProperty("status")
    public void setMove(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }



}
