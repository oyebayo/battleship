package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.GameEndException;
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
		try {
			playerManager.initializePlayers(scanner);

			if(!playerManager.isInitialized()) return;
			while (true) {
				String command = scanner.hasNext() ? scanner.next() : "";
				if (command.equals("quit")) {
					try {
						quit();
					} catch (GameEndException e) {
						output.println(e.getMessage());
						return;
					}
				} else if (command.equals("bye")) {
					break;
				} else {
					commandProcessor.processCommand(scanner, command);
				}
			}
		} finally {
			scanner.close();
		}
	}

	// Method to quit the game
	// Ends the game prematurely, throwing an exception with a message indicating the game's outcome
    private void quit() throws GameEndException {
		if(playerManager.hasLessActivePlayersThanRequired()){
			throw new GameEndException(playerManager.getWinningPlayer().getName() + " won the game");
		} else {
			throw new GameEndException("The game was not over yet...");
		}
    }
}
