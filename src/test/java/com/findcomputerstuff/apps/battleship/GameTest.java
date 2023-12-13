package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Fleet;
import com.findcomputerstuff.apps.battleship.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    private Game game;
    private CommandProcessor commandProcessor;
    private PlayerManager playerManager;
    private Scanner scanner;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setup() {
        commandProcessor = mock(CommandProcessor.class);
        playerManager = mock(PlayerManager.class);
        scanner = new Scanner("quit");
        output = new ByteArrayOutputStream();
        game = new Game(commandProcessor, playerManager);
    }

    @Test
    void gameStartsWithoutPlayers() {
        when(playerManager.isInitialized()).thenReturn(false);
        game.start(scanner, new PrintStream(output));
        verify(playerManager, times(1)).initializePlayers(any(Scanner.class));
        assertTrue(output.toString().isEmpty());
    }

    @Test
    void gameStartsWithPlayersAndQuits() {
        Fleet fleet = mock(Fleet.class);
        when(playerManager.isInitialized()).thenReturn(true);
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(true);
        when(playerManager.getWinningPlayer()).thenReturn(new Player("Winner", fleet));
        game.start(scanner, new PrintStream(output));
        verify(playerManager, times(1)).initializePlayers(any(Scanner.class));
        assertTrue(output.toString().contains("Winner won the game"));
    }

    @Test
    void gameStartsWithPlayersAndQuitsWithoutWinner() {
        when(playerManager.isInitialized()).thenReturn(true);
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        game.start(scanner, new PrintStream(output));
        verify(playerManager, times(1)).initializePlayers(any(Scanner.class));
        assertEquals("The game was not over yet...\n", output.toString());
    }

    @Test
    void gameProcessesCommand() {
        scanner = new Scanner("command\nbye");
        when(playerManager.isInitialized()).thenReturn(true);
        game.start(scanner, new PrintStream(output));
        verify(commandProcessor, times(1)).processCommand(any(Scanner.class), eq("command"));
    }
    @Test
    void gameDoesntEndsWhenQuitCommandIsProcessedAndGameIsNotOver() {
        scanner = new Scanner("command\nquit");
        when(playerManager.isInitialized()).thenReturn(true);
        game.start(scanner, new PrintStream(output));
        assertEquals("The game was not over yet...\n", output.toString());
    }

    @Test
    void gameEndsWhenQuitCommandIsProcessedAndGameIsOver() {
        scanner = new Scanner("command\nquit");
        when(playerManager.isInitialized()).thenReturn(true);
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(true);
        when(playerManager.getWinningPlayer()).thenReturn(new Player("Winner", mock(Fleet.class)));
        game.start(scanner, new PrintStream(output));
        assertEquals("Winner won the game\n", output.toString());
    }
}