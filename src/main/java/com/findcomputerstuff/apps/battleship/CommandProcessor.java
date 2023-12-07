package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.HitResult;
import com.findcomputerstuff.apps.battleship.entities.Player;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CommandProcessor {
    private final PrintStream output;
    private final PlayerManager playerManager;

    public CommandProcessor(PlayerManager playerManager, PrintStream output) {
        this.output = output;
        this.playerManager = playerManager;
    }

    // Method to process user commands
    // Determines the type of command entered by the user and calls the appropriate method to handle it
    void processCommand(Scanner scanner, String commandType) {
        String input = scanner.nextLine().trim();
        switch (commandType) {
            case "player" -> playerManager.printNextPlayer();
            case "score" -> playerManager.printPlayerScore(input);
            case "fleet" -> playerManager.printPlayerFleet(input);
            case "shoot" -> playerManager.takeShot(input);
            case "scores" -> playerManager.printPlayersSortedByScore();
            case "players" -> playerManager.printActivePlayers();
            default -> output.println("Invalid command");
        }
    }
}