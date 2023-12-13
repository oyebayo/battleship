package com.findcomputerstuff.apps.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

public class Battleship {
    static final String FLEET_FILE_PATH = "fleet.txt";
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Battleship.class.getName());
        File file = new File(FLEET_FILE_PATH);
        Battleship battleship = new Battleship();
        battleship.runGame(file, logger);
    }

    void runGame(File fleetFile, Logger logger) {
        try {
            PrintStream printStream = System.out;
            Scanner inputScanner = new Scanner(System.in);
            Scanner fileScanner = new Scanner(fleetFile);

            FleetDataLoader fleetDataLoader = new FleetDataLoader(fileScanner, printStream);
            PlayerManager playerManager = new PlayerManager(fleetDataLoader, printStream);
            CommandProcessor commandProcessor = new CommandProcessor(playerManager, printStream);

            Game game = new Game(commandProcessor, playerManager);
            game.start(inputScanner, printStream);
        } catch (FileNotFoundException e) {
            logger.warning("Fleet file not found: " + e.getMessage());
        }
    }
}