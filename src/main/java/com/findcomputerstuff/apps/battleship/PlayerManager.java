package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Fleet;
import com.findcomputerstuff.apps.battleship.entities.Grid;
import com.findcomputerstuff.apps.battleship.entities.HitResult;
import com.findcomputerstuff.apps.battleship.entities.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {
    private static final int MIN_PLAYERS = 2;
    private static final int POINTS_FOR_SHIP_HIT = 100;
    private static final int POINTS_FOR_WRECK_HIT = -30;
    private final List<Player> players;
    private final FleetDataLoader fleetDataLoader;
    private int currentPlayerIndex;
    private final PrintStream output;
    private boolean playersInitialized;

    public PlayerManager(FleetDataLoader fleetDataLoader, PrintStream output) {
        this.output = output;
        this.players = new ArrayList<>();
        this.fleetDataLoader = fleetDataLoader;
        this.currentPlayerIndex = 0;
    }

    // Method to initialize players
    // Reads player information from the user and creates Player objects
    void initializePlayers(Scanner scanner) {
        if (playersInitialized) return;

        try {
            fleetDataLoader.load();
            if (!fleetDataLoader.hasLoaded()) return;

            int playerCount = Integer.parseInt(scanner.nextLine().trim());
            if (playerCount < MIN_PLAYERS) {
                output.println("Invalid input. You must have at least " + MIN_PLAYERS + " players.");
                return;
            }

            for (int i = 1; i <= playerCount; i++) {
                String playerName = scanner.nextLine().trim();
                int fleetNumber = Integer.parseInt(scanner.nextLine().trim());

                if (fleetNumber < 1 || fleetNumber > fleetDataLoader.getFleetCount()) {
                    output.println("Invalid fleet number. Please enter a valid fleet number.");
                    return;
                }

                Grid grid = new Grid(fleetDataLoader.getFleetStrings(fleetNumber - 1));
                Fleet newFleet = grid.ConvertToFleet();
                Player player = new Player(playerName, newFleet);
                players.add(player);
            }

            playersInitialized = true;
        } catch (NumberFormatException e) {
            output.println("Invalid input. Please enter valid values.");
            players.clear();
        }
    }

    boolean isInitialized() {
        return playersInitialized;
    }

    // Method to process 'player' command
    // Displays the name of the next player to take a turn
    void printNextPlayer() {
        if (hasLessActivePlayersThanRequired()) {
            output.println("The game is over");
            return;
        }

        String nextPlayer = players.get(currentPlayerIndex).getName();
        output.println("Next player: " + nextPlayer);
    }

    // Method to process in-game command
    // Displays the names of all players who are still in the game
    void printActivePlayers() {
        for (Player player : players) {
            if (!player.isEliminated()) {
                output.println(player.getName());
            }
        }
    }

    // Method to process ranking command
    // Displays the current ranking of players by score
    void printPlayersSortedByScore() {
        // Create a copy of the players list
        List<Player> sortedPlayers = new ArrayList<>(players);

        // Sort the copied list by score in descending order using a basic sorting algorithm
        // If scores are tied, sort by name in ascending alphabetic order
        for (int i = 0; i < sortedPlayers.size() - 1; i++) {
            for (int j = 0; j < sortedPlayers.size() - i - 1; j++) {
                if (comparePlayers(sortedPlayers.get(j), sortedPlayers.get(j + 1)) > 0) {
                    // Swap elements if they are in the wrong order
                    Player temp = sortedPlayers.get(j);
                    sortedPlayers.set(j, sortedPlayers.get(j + 1));
                    sortedPlayers.set(j + 1, temp);
                }
            }
        }

        // Print the sorted ranking
        for (Player player : sortedPlayers) {
            output.println(player.getName() + " has " + player.getScore() + " points");
        }
    }

    // Method to get player by name
    // Searches for a player by name and returns the Player object if found
    private Player getPlayerByName(String playerName){
        for(Player player : players){
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        output.println("Nonexistent player");
        return null;
    }

    // Method to compare players
    // Compares two players first by score, then by name if scores are tied
    private int comparePlayers(Player p1, Player p2) {
        if (p1.getScore() != p2.getScore()) {
            // Sort by score in descending order
            return Integer.compare(p2.getScore(), p1.getScore());
        } else {
            // If scores are tied, sort by name in ascending alphabetic order
            return p1.getName().compareTo(p2.getName());
        }
    }

    // Method to advance the  turn to the next player, skipping over any eliminated players
    void moveToNextPlayer() {
        do{
            // modulo operator will ensure the indices remain less than max players
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }while(players.get(currentPlayerIndex).isEliminated());
    }

    // Method to check if the game is over
    // Checks if the game is over based on the number of players who still have ships remaining
    boolean hasLessActivePlayersThanRequired() {
        int activePlayers = 0;
        for (Player player : players) {
            if (player.getFleet().hasRemainingShips())
                activePlayers++;
        }

        return activePlayers < MIN_PLAYERS;
    }

    // Method to get the winning player
    // Determines the winning player based on score, with ties broken by who is not eliminated
    Player getWinningPlayer() {
        Player winner = null;
        for (Player player : players) {
            if (winner == null || player.getScore() > winner.getScore()) {
                winner = player;
            }
        }

        int highScore = winner != null ? winner.getScore() : 0;
        if (moreThanOnePlayerHasScore(highScore)){
            for (Player player : players) {
                if (!player.isEliminated()) {
                    winner = player;
                    break;
                }
            }
        }

        return winner;
    }

    // Method to check if more than one player has the highest score
    // Checks if more than one player has the current highest score
    private boolean moreThanOnePlayerHasScore(int highestScore) {
        int count = 0;
        for (Player player : players) {
            if (player.getScore() == highestScore) {
                count++;
            }
        }

        return count > 1;
    }

    // Method to process score command
    // Displays the score of a specified player
    void printPlayerScore(String playerName){
        Player player = getPlayerByName(playerName);
        if (player != null) {
            output.println(player.getName() + " has " + player.getScore() + " points");
        }
    }

    // Method to process fleet command
    // Displays the fleet grid of a specified player
    void printPlayerFleet(String playerName){
        Player player = getPlayerByName(playerName);
        if (player != null) {
            output.println(player.getFleet().printGrid());
        }
    }

    // Method to process shoot command
    // Processes a shoot command from the current player, updating scores and player states as necessary
    void takeShot(String shootParameters) {
        if (hasLessActivePlayersThanRequired()) {
            output.println("The game is over");
            return;
        }

        String[] parts = shootParameters.split(" ", 3);
        if (parts.length != 3 || isNotNumeric(parts[0]) || isNotNumeric(parts[1])) {
            output.println("Invalid command");
            return;
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        // convert the parameters to zero-based indices
        int targetRow = Integer.parseInt(parts[0]) - 1;
        int targetColumn = Integer.parseInt(parts[1]) - 1;
        String targetPlayerName = parts[2];


        Player targetPlayer = getPlayerByName(targetPlayerName);
        if (!shotParametersValid(targetPlayer, currentPlayer, targetRow, targetColumn)) return;

        int points = getPointsForShot(targetPlayer, targetRow, targetColumn);
        currentPlayer.addScore(points);

        if (hasLessActivePlayersThanRequired()) currentPlayer.addScore(currentPlayer.getScore());
        moveToNextPlayer();
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
