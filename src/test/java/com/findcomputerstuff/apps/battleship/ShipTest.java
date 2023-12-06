package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import com.findcomputerstuff.apps.battleship.entities.Ship;
import com.findcomputerstuff.apps.battleship.entities.ShipOrientation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ShipTest {
    private List<Cell> shipCells;
    private List<Cell> noCells;
    private ArrayList<Cell> overlappingShipCells;
    private ArrayList<Cell> farShipCells;
    private ArrayList<Cell> wideShipCells;
    private ArrayList<Cell> singleCell;

    @BeforeEach
    void beforeEach() {
        shipCells = new ArrayList<>();
        shipCells.add(new Cell(0, 0, 'A'));
        shipCells.add(new Cell(1, 0, 'A'));
        shipCells.add(new Cell(2, 0, 'A'));

        wideShipCells = new ArrayList<>();
        wideShipCells.add(new Cell(0, 0, 'B'));
        wideShipCells.add(new Cell(0, 1, 'B'));
        wideShipCells.add(new Cell(0, 2, 'B'));

        noCells = new ArrayList<>();

        singleCell = new ArrayList<>();
        singleCell.add(new Cell(2, 1, 'X'));

        overlappingShipCells = new ArrayList<>();
        overlappingShipCells.add(new Cell(0, 0, 'A'));
        overlappingShipCells.add(new Cell(0, 1, 'A'));
        overlappingShipCells.add(new Cell(0, 2, 'A'));

        farShipCells = new ArrayList<>();
        farShipCells.add(new Cell(1, 1, 'A'));
        farShipCells.add(new Cell(1, 2, 'A'));
        farShipCells.add(new Cell(1, 3, 'A'));
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

        ship.getCells().add(new Cell(0, 1, 'A'));
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

        ship.getCells().add(new Cell(0, 1, 'A'));
        assertEquals("4A at (0,0), UNKNOWN", ship.toString());
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
