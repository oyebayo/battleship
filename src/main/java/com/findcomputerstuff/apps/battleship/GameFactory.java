package com.findcomputerstuff.apps.battleship;

public class GameFactory {
    private GameFactory(){}
    public static Game createGame(java.util.Scanner fileScanner, java.io.PrintStream printStream) {
        FleetDataLoader fleetDataLoader = new FleetDataLoader(fileScanner, printStream);
        PlayerManager playerManager = new PlayerManager(fleetDataLoader, printStream);
        CommandProcessor commandProcessor = new CommandProcessor(playerManager, printStream);

        // Create an instance of Game using real implementations.
        return new Game(commandProcessor, playerManager);
    }
}