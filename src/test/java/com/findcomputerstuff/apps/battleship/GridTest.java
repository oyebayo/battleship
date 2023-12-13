package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GridTest {
    private String[] gridStrings;

    @BeforeEach
    void beforeEach() {
        gridStrings = new String[]{
            "DAAA..B.CCC.",
            "D..CCCB.....",
            "DBB.......CC"
        };
    }

    @Test
    void testConvertToFleet() {
        Grid grid = new Grid(gridStrings);
        Fleet fleet = grid.convertToFleet();
        assertEquals(7, fleet.size());

        assertEquals(3, fleet.getShipAt(0, 0).getSize());
        assertEquals('D', fleet.getShipAt(0, 0).getLabel());

        assertEquals(3, fleet.getShipAt(0, 1).getSize());
        assertEquals('A', fleet.getShipAt(0, 1).getLabel());

        assertEquals(3, fleet.getShipAt(1, 3).getSize());
        assertEquals('C', fleet.getShipAt(1, 3).getLabel());

        assertEquals(2, fleet.getShipAt(0, 6).getSize());
        assertEquals('B', fleet.getShipAt(0, 6).getLabel());

        assertEquals(3, fleet.getShipAt(0, 8).getSize());
        assertEquals('C', fleet.getShipAt(0, 8).getLabel());

        assertEquals(2, fleet.getShipAt(2, 1).getSize());
        assertEquals('B', fleet.getShipAt(2, 1).getLabel());

        assertEquals(2, fleet.getShipAt(2, 10).getSize());
        assertEquals('C', fleet.getShipAt(2, 10).getLabel());
    }

    @Test
    void testFindAnyAdjacentShipWithSameLabel_adjacentVertical() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(new Cell[]{
            new Cell(0, 0, 'A'),
            new Cell(1, 0, 'A'),
            new Cell(2, 0, 'A')
        });
        Fleet fleet = new Fleet(new Ship[]{neighbourShip}, 3, 12);

        Ship ship = grid.findAnyAdjacentShipWithSameLabel(0, 1, fleet);

        assertEquals(3, ship.getSize());
        assertEquals('A', ship.getLabel());
        assertEquals(ShipOrientation.VERTICAL, ship.getOrientation());

    }

    @Test
    void testFindAnyAdjacentShipWithSameLabel_adjacentHorizontal() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(new Cell[]{
            new Cell(1, 3, 'B'),
            new Cell(1, 4, 'B'),
            new Cell(1, 5, 'B')
        });
        Fleet fleet = new Fleet(new Ship[]{neighbourShip}, 3, 12);

        Ship ship = grid.findAnyAdjacentShipWithSameLabel(1, 6, fleet);

        assertEquals(3, ship.getSize());
        assertEquals('B', ship.getLabel());
        assertEquals(ShipOrientation.HORIZONTAL, ship.getOrientation());

    }

    @Test
    void testFindAnyAdjacentShipWithSameLabel_notAdjacent() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(new Cell[]{
            new Cell(1, 0, 'A'),
            new Cell(2, 0, 'A')
        });
        Fleet fleet = new Fleet(new Ship[]{neighbourShip}, 3, 12);

        Ship ship = grid.findAnyAdjacentShipWithSameLabel(0, 1, fleet);
        assertNull(ship);
    }

    @Test
    void testFindAnyAdjacentShipWithSameLabel_differentLabel() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(new Cell[]{
            new Cell(0, 0, 'D'),
            new Cell(1, 0, 'D'),
            new Cell(2, 0, 'D')
        });
        Fleet fleet = new Fleet(new Ship[]{neighbourShip}, 3, 12);

        Ship ship = grid.findAnyAdjacentShipWithSameLabel(0, 1, fleet);
        assertNull(ship);
    }
}
