package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import com.findcomputerstuff.apps.battleship.entities.Ship;
import com.findcomputerstuff.apps.battleship.entities.ShipOrientation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class ShipTest {
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
    void shouldInitializeCellsInConstructor() {
        Ship ship = new Ship(shipCells);
        assertEquals(shipCells.length, ship.getSize());
        for (int i = 0; i < shipCells.length; i++) {
            assertEquals(shipCells[i], ship.getCell(i));
        }
    }

    @Test
    void shouldAddCellToShip() {
        Ship ship = new Ship(shipCells);
        Cell newCell = new Cell(3, 0, 'A');
        ship.addCell(newCell);
        assertEquals(4, ship.getSize());
        assertEquals(newCell, ship.getCell(3));
    }

    @Test
    void shouldMarkShipAsWreck() {
        Ship ship = new Ship(shipCells);
        ship.markAsWreck();
        assertTrue(ship.isWreck());
    }
    @Test
    void shouldReturnShipSize() {
        Ship ship = new Ship(shipCells);
        assertEquals(3, ship.getSize());
    }
    @Test
    void shouldReturnShipLabel() {
        Ship ship = new Ship(shipCells);
        assertEquals('A', ship.getLabel());
    }
    @Test
    void shouldReturnTrueIfShipFitsWithinDimensions() {
        Ship ship = new Ship(shipCells);
        assertTrue(ship.canFitWithinDimensions(3, 3));
    }
    @Test
    void shouldReturnFalseIfShipDoesNotFitWithinDimensions() {
        Ship ship = new Ship(farShipCells);
        assertFalse(ship.canFitWithinDimensions(3, 3));
    }
    @Test
    void shouldReturnCellAtIndex() {
        Ship ship = new Ship(shipCells);
        assertEquals(shipCells[1], ship.getCell(1));
    }
    @Test
    void shouldThrowExceptionWhenCellIndexOutOfBounds() {
        Ship ship = new Ship(shipCells);
        assertThrows(IndexOutOfBoundsException.class, () -> ship.getCell(4));
    }
    @Test
    void shouldReturnTrueIfShipsOverlap() {
        Ship ship1 = new Ship(shipCells);
        Ship ship2 = new Ship(overlappingShipCells);
        assertTrue(ship1.overlapsWith(ship2));
    }
    @Test
    void shouldReturnFalseIfShipsDoNotOverlap() {
        Ship ship1 = new Ship(shipCells);
        Ship ship2 = new Ship(farShipCells);
        assertFalse(ship1.overlapsWith(ship2));
    }
    @Test
    void shouldReturnFalseIfOtherShipHasNoCells() {
        Ship ship1 = new Ship(shipCells);
        Ship ship2 = new Ship(noCells);
        assertFalse(ship1.overlapsWith(ship2));
    }

    @Test
    void shouldReturnHorizontalWhenShipIsHorizontal() {
        Ship horizontalShip = new Ship(wideShipCells);
        assertEquals(ShipOrientation.HORIZONTAL, horizontalShip.getOrientation());
    }

    @Test
    void shouldReturnVerticalWhenShipIsVertical() {
        Ship verticalShip = new Ship(shipCells);
        assertEquals(ShipOrientation.VERTICAL, verticalShip.getOrientation());
    }

    @Test
    void shouldReturnSingleWhenShipIsSingleCell() {
        Ship singleCellShip = new Ship(singleCell);
        assertEquals(ShipOrientation.SINGLE, singleCellShip.getOrientation());
    }

    @Test
    void shouldReturnUnknownWhenShipOrientationIsStrange() {
        Ship unknownOrientationShip = new Ship(farShipCells);
        assertEquals(ShipOrientation.UNKNOWN, unknownOrientationShip.getOrientation());
    }

    @Test
    void shouldReturnFormattedStringWhenShipHasCells() {
        Ship ship = new Ship(shipCells);
        String output = ship.toString();
        assertTrue(output.matches("\\d[A-Z] at \\(\\d,\\d\\), [A-Z]{2,}"));
    }

    @Test
    void shouldReturnFixedStringWhenShipHasNoCells() {
        Ship ship = new Ship(noCells);
        assertEquals(Ship.HAS_NO_CELLS, ship.toString());
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
