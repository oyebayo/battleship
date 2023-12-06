package com.findcomputerstuff.apps.battleship;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class CommandProcessor {
    private static final int POINTS_FOR_SHIP_HIT = 100;
    private static final int POINTS_FOR_WRECK_HIT = -30;
    private final Scanner scanner;
    private final PrintStream output;
    private final PlayerManager playerManager;

    public CommandProcessor(Scanner scanner, PrintStream output, PlayerManager playerManager) {
        this.scanner = scanner;
        this.output = output;
        this.playerManager = playerManager;
    }

    // Method to process user commands
    // Determines the type of command entered by the user and calls the appropriate method to handle it
    void processCommand(String commandType) {
        String input = scanner.nextLine().trim();
        switch (commandType) {
            case "player" -> processPlayerCommand();
            case "score" -> processScoreCommand(input);
            case "fleet" -> processFleetCommand(input);
            case "shoot" -> processShootCommand(input);
            case "scores" -> processRankingCommand();
            case "players" -> processInGameCommand();
            default -> output.println("Invalid command");
        }
    }


    // Method to process player command
    // Displays the name of the next player to take a turn
    private void processPlayerCommand() {
        if (playerManager.hasLessActivePlayersThanRequired()) {
            output.println("The game is over");
            return;
        }

        String nextPlayer = playerManager.getCurrentPlayer().getName();
        output.println("Next player: " + nextPlayer);
    }

    // Method to process score command
    // Displays the score of a specified player
    private void processScoreCommand(String playerName) {
        playerManager.performActionOnPlayer(playerName, player -> output.println(player.getName() + " has " + player.getScore() + " points"));
    }

    // Method to process fleet command
    // Displays the fleet grid of a specified player
    private void processFleetCommand(String playerName) {
        playerManager.performActionOnPlayer(playerName, player -> output.println(player.getFleet().printGrid()));
    }

    // Method to process ranking command
    // Displays the current ranking of players by score
    private void processRankingCommand() {
        List<Player> sortedPlayers = playerManager.getPlayersSortedByScore();

        // Print the sorted ranking
        for (Player player : sortedPlayers) {
            output.println(player.getName() + " has " + player.getScore() + " points");
        }
    }

    // Method to process in-game command
    // Displays the names of all players who are still in the game
    private void processInGameCommand() {
        for (Player player : playerManager.getActivePlayers()) {
            output.println(player.getName());
        }
    }


    // Method to process shoot command
    // Processes a shoot command from the current player, updating scores and player states as necessary
    private void processShootCommand(String shootParameters) {
        if (playerManager.hasLessActivePlayersThanRequired()) {
            output.println("The game is over");
            return;
        }

        String[] parts = shootParameters.split(" ", 3);
        if (parts.length != 3 || isNotNumeric(parts[0]) || isNotNumeric(parts[1])) {
            output.println("Invalid command");
            return;
        }

        Player currentPlayer = playerManager.getCurrentPlayer();
        // convert the parameters to zero-based indices
        int targetRow = Integer.parseInt(parts[0]) - 1;
        int targetColumn = Integer.parseInt(parts[1]) - 1;
        String targetPlayerName = parts[2];

        playerManager.performActionOnPlayer(targetPlayerName, targetPlayer -> {
            if (!shotParametersValid(targetPlayer, currentPlayer, targetRow, targetColumn)) return;

            int points = getPointsForShot(targetPlayer, targetRow, targetColumn);
            currentPlayer.addScore(points);

            if (playerManager.hasLessActivePlayersThanRequired()) currentPlayer.addScore(currentPlayer.getScore());
            playerManager.moveToNextPlayer();
        });
    }

    // Method to check if shot parameters are valid
    // Checks if the parameters for a shot are valid (target player exists, is not the current player,
    // is not eliminated, and coordinates are within bounds)
    private boolean shotParametersValid(Player targetPlayer, Player currentPlayer, int targetRow, int targetColumn) {
        if (targetPlayer == null) return false;
        if (targetPlayer.getName().equals(currentPlayer.getName())) {
            output.println("Self-inflicted shot");
            return false;
        }
        if (targetPlayer.isEliminated()) {
            output.println("Eliminated player");
            return false;
        }
        if (targetRow < 0 || targetColumn < 0
                || targetRow >= targetPlayer.getFleet().getMaxRows()
                || targetColumn >= targetPlayer.getFleet().getMaxColumns()) {
            output.println("Invalid shot");
            return false;
        }
        return true;
    }

    // Method to get points for a shot
    // Determines the number of points to award for a shot based on the result of the shot
    private int getPointsForShot(Player targetPlayer, int targetRow, int targetColumn) {
        HitResult result = targetPlayer.getFleet().hitObjectAt(targetRow, targetColumn);
        return switch (result.getHitType()) {
            case SHIP -> result.getCellCount() * POINTS_FOR_SHIP_HIT;
            case WRECK -> result.getCellCount() * POINTS_FOR_WRECK_HIT;
            case BLANK -> 0;
        };
    }

    // Method to check if a string is not numeric
    // Checks if a string can be parsed as an integer
    private boolean isNotNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}