package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Fleet;
import com.findcomputerstuff.apps.battleship.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommandProcessorTest {
    private PlayerManager playerManager;
    private ByteArrayOutputStream outputStream;
    private CommandProcessor commandProcessor;

    @BeforeEach
    public void setup() {
        playerManager = mock(PlayerManager.class);
        outputStream = new ByteArrayOutputStream();
        commandProcessor = new CommandProcessor(playerManager, new PrintStream(outputStream));
    }

    @Test
    public void processPlayerCommand_GameOver() {
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(true);
        commandProcessor.processPlayerCommand();
        assertEquals("The game is over\n", outputStream.toString());
    }

    @Test
    public void processPlayerCommand_NextPlayer() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("John");
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        when(playerManager.getCurrentPlayer()).thenReturn(player);
        commandProcessor.processPlayerCommand();
        assertEquals("Next player: John\n", outputStream.toString());
    }

    @Test
    public void processScoreCommand() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("John");
        when(player.getScore()).thenReturn(100);
        doAnswer(invocation -> {
            ((PlayerManager.PlayerAction) invocation.getArgument(1)).apply(player);
            return null;
        }).when(playerManager).performActionOnPlayer(eq("John"), any());
        commandProcessor.processScoreCommand("John");
        assertEquals("John has 100 points\n", outputStream.toString());
    }

    @Test
    public void processFleetCommand() {
        Player player = mock(Player.class);
        Fleet fleet = mock(Fleet.class);
        when(player.getFleet()).thenReturn(fleet);
        when(fleet.printGrid()).thenReturn("Grid");
        doAnswer(invocation -> {
            ((PlayerManager.PlayerAction) invocation.getArgument(1)).apply(player);
            return null;
        }).when(playerManager).performActionOnPlayer(eq("John"), any());
        commandProcessor.processFleetCommand("John");
        assertEquals("Grid\n", outputStream.toString());
    }

    @Test
    public void processShootCommand_GameOver() {
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(true);
        commandProcessor.processShootCommand("1 1 John");
        assertEquals("The game is over\n", outputStream.toString());
    }

    @Test
    public void processShootCommand_InvalidCommand() {
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        commandProcessor.processShootCommand("1 John");
        assertEquals("Invalid command\n", outputStream.toString());
    }

    @Test
    public void processShootCommand_SelfInflictedShot() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("John");
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        when(playerManager.getCurrentPlayer()).thenReturn(player);
        commandProcessor.processShootCommand("1 1 John");
        assertEquals("Self-inflicted shot\n", outputStream.toString());
    }

    @Test
    public void processShootCommand_EliminatedPlayer() {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("John");
        when(player.isEliminated()).thenReturn(true);
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        when(playerManager.getCurrentPlayer()).thenReturn(mock(Player.class));
        doAnswer(invocation -> {
            ((PlayerManager.PlayerAction) invocation.getArgument(1)).apply(player);
            return null;
        }).when(playerManager).performActionOnPlayer(eq("John"), any());
        commandProcessor.processShootCommand("1 1 John");
        assertEquals("Eliminated player\n", outputStream.toString());
    }

    @Test
    public void processShootCommand_InvalidShot() {
        Player player = mock(Player.class);
        Fleet fleet = mock(Fleet.class);
        when(player.getName()).thenReturn("John");
        when(player.isEliminated()).thenReturn(false);
        when(player.getFleet()).thenReturn(fleet);
        when(fleet.getMaxRows()).thenReturn(1);
        when(fleet.getMaxColumns()).thenReturn(1);
        when(playerManager.hasLessActivePlayersThanRequired()).thenReturn(false);
        when(playerManager.getCurrentPlayer()).thenReturn(mock(Player.class));
        doAnswer(invocation -> {
            ((PlayerManager.PlayerAction) invocation.getArgument(1)).apply(player);
            return null;
        }).when(playerManager).performActionOnPlayer(eq("John"), any());
        commandProcessor.processShootCommand("2 2 John");
        assertEquals("Invalid shot\n", outputStream.toString());
    }
}