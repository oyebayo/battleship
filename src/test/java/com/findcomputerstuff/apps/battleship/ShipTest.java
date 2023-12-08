package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import com.findcomputerstuff.apps.battleship.entities.Ship;
import com.findcomputerstuff.apps.battleship.entities.ShipOrientation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ShipTest {
    private Cell[] shipCells;
    private Cell[] noCells;
    private Cell[] overlappingShipCells;
    private Cell[] farShipCells;
    private Cell[] wideShipCells;
    private Cell[] singleCell;

    @BeforeEach
    void beforeEach() {
        Cell[] cells = new Cell[]{
            new Cell(0, 0, 'A'),
            new Cell(1, 0, 'A'),
            new Cell(2, 0, 'A'),
            new Cell(0, 0, 'B'),
            new Cell(0, 1, 'B'),
            new Cell(0, 2, 'B'),
            new Cell(2, 1, 'X'),
            new Cell(0, 0, 'A'),
            new Cell(0, 1, 'A'),
            new Cell(0, 2, 'A'),
            new Cell(1, 1, 'A'),
            new Cell(1, 2, 'A'),
            new Cell(1, 3, 'A'),
            new Cell(2, 3, 'A')
        };

        shipCells = Arrays.copyOfRange(cells, 0, 3);
        wideShipCells = Arrays.copyOfRange(cells, 3, 6);
        noCells = new Cell[]{};
        singleCell = Arrays.copyOfRange(cells, 6, 7);
        overlappingShipCells = Arrays.copyOfRange(cells, 7, 10);
        farShipCells = Arrays.copyOfRange(cells, 10, 14);
    }

    @Test
    public void testShipCreation() {
        Ship ship = new Ship(shipCells);
        assertEquals(3, ship.getSize());
        assertFalse(ship.isWreck());
    }

    @Test
    public void testShipMarkAsWreck() {
        Ship ship = new Ship(shipCells);
        ship.markAsWreck();
        assertTrue(ship.isWreck());
    }

    @Test
    public void testShipGetSize() {
        Ship ship = new Ship(shipCells);
        assertEquals(3, ship.getSize());

        ship = new Ship(noCells);
        assertEquals(0, ship.getSize());
    }

    @Test
    public void testShipCanFitWithinDimensions() {
        Ship ship = new Ship(shipCells);
        assertTrue(ship.canFitWithinDimensions(10, 10));
    }

    @Test
    public void testShipCanNotFitWithinDimensions() {
        Ship ship = new Ship(shipCells);
        assertFalse(ship.canFitWithinDimensions(1, 1));
    }

    @Test
    public void testShipOverlapsWith() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(overlappingShipCells);

        assertTrue(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipDoesNotOverlapWith() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(farShipCells);
        assertFalse(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipOverlapsWithItself() {
        Ship ship = new Ship(shipCells);
        assertTrue(ship.overlapsWith(ship));
    }

    @Test
    public void testShipDoesNotOverlapWithEmptyShip() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(noCells);
        assertFalse(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipGetOrientation() {
        Ship ship = new Ship(wideShipCells);
        assertEquals(ShipOrientation.HORIZONTAL, ship.getOrientation());

        ship = new Ship(singleCell);
        assertEquals(ShipOrientation.SINGLE, ship.getOrientation());

        ship = new Ship(noCells);
        assertEquals(ShipOrientation.SINGLE, ship.getOrientation());

        ship =  new Ship(shipCells);
        assertEquals(ShipOrientation.VERTICAL, ship.getOrientation());

        ship =  new Ship(farShipCells);
        assertEquals(ShipOrientation.UNKNOWN, ship.getOrientation());

    }

    @Test
    public void testShipToString() {
        Ship ship = new Ship(wideShipCells);
        assertEquals("3B at (0,0), HORIZONTAL", ship.toString());

        ship = new Ship(singleCell);
        assertEquals("1X at (2,1), SINGLE", ship.toString());

        ship = new Ship(noCells);
        assertEquals(Ship.HAS_NO_CELLS, ship.toString());

        ship = new Ship(shipCells);
        assertEquals("3A at (0,0), VERTICAL", ship.toString());

        ship = new Ship(farShipCells);
        assertEquals("4A at (1,1), UNKNOWN", ship.toString());
    }


    @AfterEach
    void afterEach() {
        shipCells = null;
        noCells = null;
        overlappingShipCells = null;
        farShipCells = null;
        wideShipCells = null;
        singleCell = null;
    }
}
