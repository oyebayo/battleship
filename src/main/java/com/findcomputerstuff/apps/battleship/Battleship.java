package com.findcomputerstuff.apps.battleship;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Battleship {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        PrintStream printStream = System.out;
        Scanner fileScanner;

        try {
            fileScanner = FleetFileHelper.createFileScanner();
        } catch (FileNotFoundException e) {
            Logger.getLogger(Battleship.class.getName()).log(Level.SEVERE, String.format("Fleet file not found: %s", e.getMessage()));
            return;
        }

        Game game = GameFactory.createGame(fileScanner, printStream);
        game.start(inputScanner, printStream);
    }
}