package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FleetDataLoaderTest {
    private FleetDataLoader fleetDataLoader;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private String complexInput;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        complexInput = "2 8\nRRRGG.R.\n......R.\n" +
                "1 14\n.D.EEDFFFDGG..\n" +
                "3 8\nRRRGG.R.\n......R.\n.BB...R.\n" +
                "12 1\n.\n.\nA\nA\n.\n.\nB\n.\nC\nC\nC\n.\n";
    }

    @Test
    void loadSimpleFleetDataSuccessfully() {
        String input = "3 12\nDAAA..B.CCC.\nD.....B.....\nD.........CC\n";
        fleetDataLoader = new FleetDataLoader(new Scanner(input), System.out);
        fleetDataLoader.load();
        assertTrue(fleetDataLoader.hasLoaded());
        assertEquals(1, fleetDataLoader.getFleetCount());
        assertArrayEquals(new String[]{"DAAA..B.CCC.", "D.....B.....", "D.........CC"}, fleetDataLoader.getFleetStrings(0));
    }

    @Test
    void loadComplexFleetDataSuccessfully() {
        fleetDataLoader = new FleetDataLoader(new Scanner(complexInput), System.out);
        fleetDataLoader.load();
        assertTrue(fleetDataLoader.hasLoaded());
        assertEquals(4, fleetDataLoader.getFleetCount());
        assertArrayEquals(new String[]{"RRRGG.R.", "......R."}, fleetDataLoader.getFleetStrings(0));
        assertArrayEquals(new String[]{".D.EEDFFFDGG.."}, fleetDataLoader.getFleetStrings(1));
        assertArrayEquals(new String[]{"RRRGG.R.", "......R.", ".BB...R."}, fleetDataLoader.getFleetStrings(2));
        assertArrayEquals(new String[]{".", ".", "A", "A", ".", ".", "B", ".", "C", "C", "C", "."}, fleetDataLoader.getFleetStrings(3));
    }

    @Test
    void loadFleetDataWithIncorrectRowSize() {
        String input = "3 12\nDAAA..B.CCC..\nD.....B......\nD.........CC.\n";
        fleetDataLoader = new FleetDataLoader(new Scanner(input), System.out);
        fleetDataLoader.load();
        assertFalse(fleetDataLoader.hasLoaded());
        assertEquals(0, fleetDataLoader.getFleetCount());
    }

    @Test
    void loadFleetDataWithMissingRows() {
        String input = "3 12\nDAAA..B.CCC.\nD.........CC\n";
        fleetDataLoader = new FleetDataLoader(new Scanner(input), System.out);
        fleetDataLoader.load();
        assertFalse(fleetDataLoader.hasLoaded());
        assertEquals(0, fleetDataLoader.getFleetCount());
    }

    @Test
    void loadFleetDataWithException() {
        String input = "3\nDAAA..B.CCC.\nD.....B.....\nD.........CC\n";
        fleetDataLoader = new FleetDataLoader(new Scanner(input), System.out);
        fleetDataLoader.load();
        assertFalse(fleetDataLoader.hasLoaded());
        assertEquals(0, fleetDataLoader.getFleetCount());
        assertEquals("Fleet file could not be loaded.\n", outContent.toString());
    }

    @Test
    void getFleetStringsForInvalidIndex() {
        String input = "3 12\nDAAA..B.CCC.\nD.....B.....\nD.........CC\n";
        fleetDataLoader = new FleetDataLoader(new Scanner(input), System.out);
        fleetDataLoader.load();
        assertThrows(IndexOutOfBoundsException.class, () -> fleetDataLoader.getFleetStrings(1));
    }
}