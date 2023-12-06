package com.findcomputerstuff.apps.battleship;

public class Battleship {
    public static void main(String[] args) {
    	Game game = new Game((InputScanner) System.in, System.out);
    	game.start();
    }
}