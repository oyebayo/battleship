package com.findcomputerstuff.apps.battleship.entities;

public class GameEndException extends Exception {
    public GameEndException(String message) {
        super(message);
    }
}