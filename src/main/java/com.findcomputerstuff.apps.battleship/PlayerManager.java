package com.findcomputerstuff.apps.battleship;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {
    private static final int MIN_PLAYERS = 2;
    private final List<Player> players;
    private final FleetDataLoader fleetDataLoader;
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
        this.fleetDataLoader = new FleetDataLoader(output);
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
            fleetDataLoader.load();
            if (!fleetDataLoader.isLoaded()) return;

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

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getActivePlayers() {
        List<Player> activePlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.isEliminated()) {
                activePlayers.add(player);
            }
        }
        return activePlayers;
    }
    public List<Player> getPlayersSortedByScore() {
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
        return sortedPlayers;
    }

    // Method to perform an action on a player
    // Finds a player by name and performs a specified action on them
    public void performActionOnPlayer(String playerName, PlayerAction action) {
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
}