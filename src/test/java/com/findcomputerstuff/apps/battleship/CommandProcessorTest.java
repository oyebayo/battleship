package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommandProcessorTest {
    private CommandProcessor commandProcessor;
    private PlayerManager playerManager;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setup() {
        playerManager = mock(PlayerManager.class);
        outputStream = new ByteArrayOutputStream();
        commandProcessor = new CommandProcessor(playerManager, new PrintStream(outputStream));
    }

    @Test
    void processesPlayerCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("player\n".getBytes())), "player");
        verify(playerManager).printNextPlayer();
    }

    @Test
    void processesScoreCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("score\n".getBytes())), "score");
        verify(playerManager).printPlayerScore("score");
    }

    @Test
    void processesFleetCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("fleet\n".getBytes())), "fleet");
        verify(playerManager).printPlayerFleet("fleet");
    }

    @Test
    void processesShootCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("shoot\n".getBytes())), "shoot");
        verify(playerManager).takeShot("shoot");
    }

    @Test
    void processesScoresCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("scores\n".getBytes())), "scores");
        verify(playerManager).printPlayersSortedByScore();
    }

    @Test
    void processesPlayersCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("players\n".getBytes())), "players");
        verify(playerManager).printActivePlayers();
    }

    @Test
    void handlesInvalidCommand() {
        commandProcessor.processCommand(new Scanner(new ByteArrayInputStream("invalid\n".getBytes())), "invalid");
        assertEquals("Invalid command\n", outputStream.toString());
    }
}