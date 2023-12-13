package com.findcomputerstuff.apps.battleship;

import java.io.PrintStream;
import java.util.Scanner;

public class Game {
	private final PlayerManager playerManager;
	private final CommandProcessor commandProcessor;

	public Game(CommandProcessor commandProcessor, PlayerManager playerManager) {
		// Initializes the game with default values
		this.playerManager = playerManager;
		this.commandProcessor = commandProcessor;
	}

	// Method to start the game
	// Begins the game loop, processing user commands until the game ends
	public void start(Scanner scanner, PrintStream output)
	{
        try (scanner) {
            playerManager.initializePlayers(scanner);

            if (!playerManager.isInitialized()) return;
            while (true) {
                String command = scanner.hasNext() ? scanner.next() : "";
                if (command.equals("quit")) {
                    if (playerManager.hasLessActivePlayersThanRequired()) {
                        output.println(playerManager.getWinningPlayer().getName() + " won the game");
                        return;
                    }else{
                        output.println("The game was not over yet...");
                    }
                } else if (command.equals("bye")) {
                    break;
                } else {
                    commandProcessor.processCommand(scanner, command);
                }
            }
        }
	}
}
