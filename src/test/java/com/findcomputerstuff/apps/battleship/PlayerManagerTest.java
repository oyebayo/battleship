package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerManagerTest {
    private PlayerManager playerManager;
    private ByteArrayOutputStream outContent;
    private final String startInput = "2\nHan Solo\n1\nDarth Vader\n2\n";
    private final String threePlayerInput = "3\nHan Solo\n1\nDarth Vader\n2\nLeia\n3\n";

    private final String[][] fleetStrings = new String[][]{
        {"RRRGG.R.","......R.",".BB...R."},
        {"D.AA..B.CCC.","D.....B.....","D.....CCCCCC"},
        {".D.E.DFFFFFF.."},
        {"A...."},
        {"AAAAB..D",".X..B..C",".X..B...",".X..B..."}
    };

    private void playThreePlayerGameToEliminatePlayer3(){
        playerManager.takeShot("1 2 Leia"); // Han Solo scores 100 points
        playerManager.takeShot("1 4 Leia"); // Darth Vader scores 200 points
        playerManager.takeShot("3 12 Darth Vader"); // Leia scores 600 points
        playerManager.takeShot("1 6 Leia"); // Han Solo scores 100 points
        playerManager.takeShot("1 7 Leia"); // Darth Vader scores 100 points.
        // Leia is eliminated so she cannot be next player
        playerManager.takeShot("1 1 Darth Vader"); // Han Solo scores 300 points
    }
    private void playTwoPlayerGameToEliminatePlayer1() {
        playerManager.takeShot("1 1 Darth Vader"); // Han Solo scores 300 points
        playerManager.takeShot("1 1 Han Solo"); // Darth Vader scores 300 points
        playerManager.takeShot("1 3 Darth Vader"); // Han Solo scores 200 points
        playerManager.takeShot("3 2 Han Solo"); // Darth Vader scores 200 points
        playerManager.takeShot("3 12 Darth Vader"); // Han Solo scores 200 points
        playerManager.takeShot("1 4 Han Solo"); // Darth Vader scores 200 points
        playerManager.takeShot("1 7 Darth Vader"); // Han Solo scores 200 points
        playerManager.takeShot("1 7 Han Solo"); // Darth Vader scores 300 points + bonus
    }

    private void playTiedGame() {
        playerManager.takeShot("1 1 Leia"); // Han Solo scores 400 points
        playerManager.takeShot("1 5 Leia"); // Darth Vader scores 400 points
        playerManager.takeShot("1 5 Darth Vader"); // Leia scores 0 points
        playerManager.takeShot("1 8 Leia"); // Han Solo scores 100 points
        playerManager.takeShot("1 7 Leia"); // Darth Vader scores 0 points
        playerManager.takeShot("1 1 Han Solo"); // Leia scores 100 points, Han Solo is eliminated
        playerManager.takeShot("2 8 Leia"); // Darth Vader scores 100 points
        playerManager.takeShot("1 1 Darth Vader"); // Leia scores 100 points, Darth Vader is eliminated
    }
    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);
        FleetDataLoader mockFleetDataLoader = mock(FleetDataLoader.class);

        // Set up the mockFleetDataLoader to return a fleet with a known state
        when(mockFleetDataLoader.getFleetStrings(0)).thenReturn(fleetStrings[0]);
        when(mockFleetDataLoader.getFleetStrings(1)).thenReturn(fleetStrings[1]);
        when(mockFleetDataLoader.getFleetStrings(2)).thenReturn(fleetStrings[2]);
        when(mockFleetDataLoader.getFleetStrings(3)).thenReturn(fleetStrings[3]);
        when(mockFleetDataLoader.getFleetStrings(4)).thenReturn(fleetStrings[4]);
        when(mockFleetDataLoader.hasLoaded()).thenReturn(true);
        when(mockFleetDataLoader.getFleetCount()).thenReturn(5);

        playerManager = new PlayerManager(mockFleetDataLoader, printStream);
    }

    @Test
    void initializePlayersSucceedsOnValidInput() {
        playerManager.initializePlayers(new Scanner(startInput));
        assertTrue(playerManager.isInitialized());
    }

    @ParameterizedTest
    @CsvSource({
            "'invalid\n', false", // invalid number of players
            "'1\nHan Solo\n1\n', false", // only 1 player
            "'2\nHan Solo\n0\nDarth Vader\n2\n', false", // 0 is an invalid fleet number
            "'2\nHan Solo\n6\nDarth Vader\n2\n', false", // 6 is an invalid fleet number
            "'2\n\n1\nDarth Vader\n2\n', false" // empty player name
    })
    void testInitializePlayers(String input, boolean expected) {
        playerManager.initializePlayers(new Scanner(input));
        assertEquals(expected, playerManager.isInitialized());
    }

    @Test
    void printNextPlayerDisplaysCorrectly() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.printNextPlayer();
        assertEquals("Next player: Han Solo\n", outContent.toString());
    }

    @Test
    void printNextPlayerDisplaysCorrectlyAfterElimination() {
        playerManager.initializePlayers(new Scanner(threePlayerInput));
        playThreePlayerGameToEliminatePlayer3();
        playerManager.printNextPlayer();
        assertEquals("Next player: Darth Vader\n", outContent.toString());
    }

    @Test
    void printNextPlayerDisplaysGameOverWhenNoOpponentsActive() {
        playerManager.initializePlayers(new Scanner(startInput));
        playTwoPlayerGameToEliminatePlayer1();
        playerManager.printNextPlayer();
        assertEquals("The game is over\n", outContent.toString());
    }

    @Test
    void printActivePlayersDisplaysCorrectly() {
        playerManager.initializePlayers(new Scanner(threePlayerInput));
        playerManager.printActivePlayers();
        assertEquals("Han Solo\nDarth Vader\nLeia\n", outContent.toString());
    }

    @Test
    void printActivePlayersDisplaysCorrectlyAfterElimination() {
        playerManager.initializePlayers(new Scanner(threePlayerInput));
        playThreePlayerGameToEliminatePlayer3();
        playerManager.printActivePlayers();
        assertEquals("Han Solo\nDarth Vader\n", outContent.toString());
    }

    @Test
    void printPlayersSortedByScoreDisplaysCorrectOrder() {
        playerManager.initializePlayers(new Scanner(threePlayerInput));
        playThreePlayerGameToEliminatePlayer3();
        playerManager.printPlayersSortedByScore();
        assertEquals("Darth Vader has 700 points\nLeia has 600 points\nHan Solo has 500 points\n", outContent.toString());
    }

    @Test
    void printPlayersSortedByScoreDisplaysCorrectOrderWhenGameIsTied() {
        String tiedInput = "3\nHan Solo\n4\nDarth Vader\n4\nLeia\n5\n";
        playerManager.initializePlayers(new Scanner(tiedInput));
        playTiedGame();
        playerManager.printPlayersSortedByScore();
        assertEquals("Darth Vader has 500 points\nHan Solo has 500 points\nLeia has 400 points\n", outContent.toString());
    }

    @Test
    void getWinningPlayerReturnsCorrectly() {
        playerManager.initializePlayers(new Scanner(startInput));
        playTwoPlayerGameToEliminatePlayer1();
        Player winner = playerManager.getWinningPlayer();
        assertEquals("Darth Vader", winner.getName());
    }

    @Test
    void getWinningPlayerReturnsCorrectlyWhenGameIsTied() {
        String tiedInput = "3\nHan Solo\n4\nDarth Vader\n4\nLeia\n5\n";
        playerManager.initializePlayers(new Scanner(tiedInput));
        playTiedGame();
        Player winner = playerManager.getWinningPlayer();
        assertEquals("Leia", winner.getName());
    }

    @Test
    void takeShotUpdatesScoreCorrectly() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.takeShot("1 1 Darth Vader"); // Han Solo scores 300 points
        playerManager.printPlayerScore("Han Solo");
        assertEquals("Han Solo has 300 points\n", outContent.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "'1 1 Han Solo', 'Self-inflicted shot\n'", // take shot on self
            "'1 Darth Vader', 'Invalid command\n'", // take shot with incorrect number of coordinates
            "'b 1 Darth Vader', 'Invalid command\n'", // take shot with one coordinate non-numeric
            "'a b Darth Vader', 'Invalid command\n'", // take shot with non-numeric coordinates
            "'25 25 Darth Vader', 'Invalid shot\n'", // take shot outside grid maximum
            "'-1 -10 Darth Vader', 'Invalid shot\n'", // take shot outside grid minimum
            "'1 1 nobody', 'Nonexistent player\n'" // take shot on non-existent player
    })
    void testTakeShotErrors(String shotParameters, String expectedOutput) {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.takeShot(shotParameters);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void takeShotOnEliminatedPlayerReturnsError() {
        playerManager.initializePlayers(new Scanner(threePlayerInput));
        playThreePlayerGameToEliminatePlayer3();
        playerManager.takeShot("1 1 Leia"); // Attempt to take a shot on eliminated Leia
        assertEquals("Eliminated player\n", outContent.toString());
    }
    @Test
    void takeShotOnEmptyCellReturnsZeroPoints() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.takeShot("2 2 Darth Vader"); // Han Solo scores 0 points
        playerManager.printPlayerScore("Han Solo");
        assertEquals("Han Solo has 0 points\n", outContent.toString());
    }

    @Test
    void takeShotOnWreckCellReturnsWreckPoints() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.takeShot("1 1 Darth Vader"); // Han Solo scores 300 points
        playerManager.takeShot("1 1 Han Solo"); // Darth Vader scores 300 points
        playerManager.takeShot("1 1 Darth Vader"); // Han Solo scores -90 points because wreck
        playerManager.printPlayerScore("Han Solo");
        assertEquals("Han Solo has 210 points\n", outContent.toString());
    }

    @Test
    void takeShotWhenGameIsOverReturnsError() {
        playerManager.initializePlayers(new Scanner(startInput));
        playTwoPlayerGameToEliminatePlayer1();
        playerManager.takeShot("1 1 Han Solo"); // Attempt to take a shot on non-existent player
        assertEquals("The game is over\n", outContent.toString());
    }

    @Test
    void printPlayerFleetDisplaysCorrectly() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.printPlayerFleet("Han Solo");
        String expectedFleetRepresentation = "RRRGG.R.\n......R.\n.BB...R.\n"; // This should be replaced with the actual expected fleet representation
        assertEquals(expectedFleetRepresentation, outContent.toString());
    }

    @Test
    void printPlayerFleetReturnsErrorForNonexistentPlayer() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.printPlayerFleet("nobody");
        assertEquals("Nonexistent player\n", outContent.toString());
    }

    @Test
    void printPlayerScoreReturnsErrorForNonexistentPlayer() {
        playerManager.initializePlayers(new Scanner(startInput));
        playerManager.printPlayerScore("nobody");
        assertEquals("Nonexistent player\n", outContent.toString());
    }
}
