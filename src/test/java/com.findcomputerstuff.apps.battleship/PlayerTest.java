package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import com.findcomputerstuff.apps.battleship.entities.Fleet;
import com.findcomputerstuff.apps.battleship.entities.Player;
import com.findcomputerstuff.apps.battleship.entities.Ship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Fleet fleet;

    @BeforeEach
    void beforeEach() {
        Ship[] ships = new Ship[5]; // empty array

        /*
        DAAA..B.CCC.
        D.....B.....
        D.........CC
        */
        var shipCells = Arrays.asList(
                new Cell(0, 0, 'D'),
                new Cell(1, 0, 'D'),
                new Cell(2, 0, 'D'),
                new Cell(0, 1, 'A'),
                new Cell(0, 2, 'A'),
                new Cell(0, 3, 'A'),
                new Cell(0, 6, 'B'),
                new Cell(1, 6, 'B'),
                new Cell(0, 8, 'C'),
                new Cell(0, 9, 'C'),
                new Cell(0, 10, 'C'),
                new Cell(2, 10, 'C'),
                new Cell(2, 11, 'C')
        );

        ships[0] = new Ship(shipCells.stream().limit(3).toList());
        ships[1] = new Ship(shipCells.stream().skip(3).limit(3).toList());
        ships[2] = new Ship(shipCells.stream().skip(6).limit(2).toList());
        ships[3] = new Ship(shipCells.stream().skip(8).limit(3).toList());
        ships[4] = new Ship(shipCells.stream().skip(11).limit(2).toList());

        fleet = new Fleet(ships, 3, 12);
    }

    @Test
    void testPlayer() {
        Player player = new Player("Test", fleet);
        assertEquals("Test", player.getName());
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScore() {
        Player player = new Player("Test", fleet);
        player.addScore(50);
        assertEquals(50, player.getScore());

        player.addScore(20);
        assertEquals(70, player.getScore());
    }

    @Test
    void testIsEliminated() {
        Player player = new Player("Test", fleet);
        assertFalse(player.isEliminated());

        player.getFleet().hitObjectAt(0, 0);
        assertFalse(player.isEliminated());

        player.getFleet().hitObjectAt(0, 3);
        player.getFleet().hitObjectAt(1, 6);
        player.getFleet().hitObjectAt(0, 9);
        player.getFleet().hitObjectAt(2, 10);
        assertTrue(player.isEliminated());
    }

    @AfterEach
    void afterEach() {
        fleet = null;
    }
}