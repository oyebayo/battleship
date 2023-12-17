package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BattleshipTest {
    private Game game;
    private Logger loggerMock;
    private MockedStatic<FleetFileHelper> staticFHelperMock;
    private MockedStatic<GameFactory> staticGameMock;

    @BeforeEach
    void setUp() {
        game = Mockito.mock(Game.class);
        loggerMock = Mockito.mock(Logger.class);

        staticFHelperMock = Mockito.mockStatic(FleetFileHelper.class);
        MockedStatic<Logger> loggerMockedStatic = Mockito.mockStatic(Logger.class);
        loggerMockedStatic.when(() -> Logger.getLogger(Battleship.class.getName())).thenReturn(loggerMock);

        staticGameMock = Mockito.mockStatic(GameFactory.class);
        staticGameMock.when(() -> GameFactory.createGame(any(Scanner.class), any(PrintStream.class))).thenReturn(game);

        String input = "2\nPlayer 1\n1\nPlayer 2\n1\nplayers\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void mainMethodCallsStartMethodWhenFleetFileIsFound() {
        // Arrange
        Scanner fleetFileScanner = new Scanner("2 8\nRRRGG.R.\n......R.\n");
        staticFHelperMock.when(FleetFileHelper::createFileScanner).thenReturn(fleetFileScanner);

        // Act
        Battleship.main(new String[]{});

        // Assert
        staticGameMock.verify(() -> GameFactory.createGame(any(Scanner.class), any(PrintStream.class)));
        verify(game).start(any(Scanner.class), any(PrintStream.class));
    }

    @Test
    void mainMethodDoesNotCreateGameWhenFleetFileIsMissing() {
        // Arrange
        staticFHelperMock.when(FleetFileHelper::createFileScanner).thenThrow(new FileNotFoundException());

        Battleship.main(new String[]{}); // Act
        staticGameMock.verifyNoInteractions(); // Assert
    }

    @Test
    void mainMethodCreatesSevereLogEntryWhenFleetFileIsMissing() {
        // Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        staticFHelperMock.when(FleetFileHelper::createFileScanner).thenThrow(new FileNotFoundException());

        Battleship.main(new String[]{}); // Act
        // Verify that the logger was called with the expected severity and message
        verify(loggerMock).log(eq(Level.SEVERE), argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue().startsWith("Fleet file not found: "));
    }
}