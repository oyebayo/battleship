package com.findcomputerstuff.apps.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Battleship {
    public static void main(String[] args) {
        try {
            Scanner inputScanner = new Scanner(System.in);
            PrintStream output = System.out;
            Scanner fileScanner = new Scanner(new File(FleetDataLoader.FLEET_FILE_PATH));

            FleetDataLoader fleetDataLoader = new FleetDataLoader(fileScanner, output);
            PlayerManager playerManager = new PlayerManager(fleetDataLoader, output);
            CommandProcessor commandProcessor = new CommandProcessor(playerManager, output);

            Game game = new Game(commandProcessor, playerManager);
            game.start(inputScanner, output);
        } catch (FileNotFoundException e) {
            System.out.println("Fleet file not found: " + e.getMessage());
        }
    }
}