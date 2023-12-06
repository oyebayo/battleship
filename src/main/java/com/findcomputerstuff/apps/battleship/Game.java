package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.GameEndException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Game {
	private final Scanner scanner;
	private final PrintStream output;
	private final PlayerManager playerManager;
	private final CommandProcessor commandProcessor;

	public Game(InputStream input, PrintStream output) {
		if (input == null || output == null) {
			throw new IllegalArgumentException("Input and output streams must not be null");
		}

		// Initializes the game with default values
		this.scanner = new Scanner(input);
		this.output = output;
		this.playerManager = new PlayerManager(input, output);
		this.commandProcessor = new CommandProcessor(new Scanner(input), output, playerManager);
	}

	// Method to start the game
	// Begins the game loop, processing user commands until the game ends
	public void start() 
	{
		try {
			playerManager.initializePlayers();

			if(!playerManager.isInitialized()) return;
			while (true) {
				String command = scanner.next();
				if (command.equals("quit")) {
					try {
						quit();
					} catch (GameEndException e) {
						output.println(e.getMessage());
						return;
					}
				} else {
					commandProcessor.processCommand(command);
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
