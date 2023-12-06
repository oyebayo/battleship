package com.findcomputerstuff.apps.battleship;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {
    private static final int MIN_PLAYERS = 2;
    private final List<Player> players;
    private final FleetLoader fleetLoader;
    private int currentPlayerIndex;
    private final PrintStream output;
    private final Scanner scanner;
    private boolean playersInitialized;

    @FunctionalInterface
    interface PlayerAction {
        void apply(Player player);
    }

    public PlayerManager(InputStream input, PrintStream output) {
        this.output = output;
        this.scanner = new Scanner(input);
        this.players = new ArrayList<>();
        this.fleetLoader = new FleetLoader(output);
        this.currentPlayerIndex = 0;
    }

    public boolean isInitialized() {
        return playersInitialized;
    }
    // Method to initialize players
    // Reads player information from the user and creates Player objects
    void initializePlayers() {
        if (playersInitialized) return;

        try {
            fleetLoader.loadFleets();
            if (!fleetLoader.isLoaded()) return;

            int playerCount = Integer.parseInt(scanner.nextLine().trim());
            if (playerCount < MIN_PLAYERS) {
                output.println("Invalid input. You must have at least " + MIN_PLAYERS + " players.");
                return;
            }

            for (int i = 1; i <= playerCount; i++) {
                String playerName = scanner.nextLine().trim();
                int fleetNumber = Integer.parseInt(scanner.nextLine().trim());

                if (fleetNumber < 1 || fleetNumber > fleetLoader.getFleetCount()) {
                    output.println("Invalid fleet number. Please enter a valid fleet number.");
                    return;
                }

                Grid grid = new Grid(fleetLoader.getFleetStrings(fleetNumber - 1));
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

    // Method to perform an action on a player
    // Finds a player by name and performs a specified action on them
    private void performActionOnPlayer(String playerName, PlayerAction action) {
        Player player = getPlayerByName(playerName);
        if (player != null) {
            action.apply(player);
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

    // Method to get the next player index
    // Determines the index of the next player to take a turn, skipping over any eliminated players
    private int getNextPlayerIndex() {
        int nextIndex = currentPlayerIndex;
        do{
            // modulo operator will ensure the indices remain less than max players
            nextIndex = (nextIndex + 1) % players.size();
        }while(players.get(nextIndex).isEliminated());
        return nextIndex;
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
}