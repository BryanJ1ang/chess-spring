package com.localhost.chess.web;

import jakarta.annotation.Resource;

@Resource
public class MoveStatus {
    private boolean success;
    private String message;

    public MoveStatus(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setSuccess(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }


}
