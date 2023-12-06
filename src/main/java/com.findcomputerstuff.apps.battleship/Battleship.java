package com.findcomputerstuff.apps.battleship;

public class Battleship {
    public static void main(String[] args) {
    	Game game = new Game();
        game.setOutput(System.out);
    	game.start();
    }
}