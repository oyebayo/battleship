package com.findcomputerstuff.apps.battleship;

public class GameEndException extends Exception {
    public GameEndException(String message) {
        super(message);
    }
}