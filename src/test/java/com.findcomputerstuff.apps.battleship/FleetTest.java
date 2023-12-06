package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    private Ship[] ships;
    private ArrayList<Cell> shipCells;
    private ArrayList<Cell> farShipCells;
    private ArrayList<Cell> wideShipCells;

    @BeforeEach
    void setUp() {
        ships = new Ship[3];

        shipCells = new ArrayList<>();
        shipCells.add(new Cell(0, 0, 'A'));
        shipCells.add(new Cell(1, 0, 'A'));
        shipCells.add(new Cell(2, 0, 'A'));
        ships[0] = new Ship(shipCells);

        wideShipCells = new ArrayList<>();
        wideShipCells.add(new Cell(0, 1, 'B'));
        wideShipCells.add(new Cell(0, 2, 'B'));
        wideShipCells.add(new Cell(0, 3, 'B'));
        ships[1] = new Ship(wideShipCells);

        farShipCells = new ArrayList<>();
        farShipCells.add(new Cell(3, 2, 'A'));
        farShipCells.add(new Cell(3, 3, 'A'));
        ships[2] = new Ship(farShipCells);
    }

    @AfterEach
    void tearDown() {
        shipCells = null;
        ships = null;
        wideShipCells = null;
        farShipCells = null;
    }

    @Test
    void testFleetCreation() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	assertEquals(3, fleet.size());
    }

    @Test
    void testFleetHitObjectAt() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	var result = fleet.hitObjectAt(0, 0);

        assertEquals(HitType.SHIP, result.getHitType());
        assertEquals(3, result.getCellCount());
        assertTrue(fleet.getShipAt(0, 0).isWreck());
    }

    @Test
    void testFleetHitObjectAt_blank() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	var result = fleet.hitObjectAt(1, 1);
        assertEquals(HitType.BLANK, result.getHitType());
        assertEquals(0, result.getCellCount());
    }

    @Test
    void testFleetHitObjectAt_wreck(){
        Fleet fleet = new Fleet(ships, 4, 4);
        fleet.hitObjectAt(0, 0);
        var sameSpotResult = fleet.hitObjectAt(0, 0);
        var otherSpotOnSameShipResult = fleet.hitObjectAt(1, 0);
        var anotherSpotOnSameShipResult = fleet.hitObjectAt(2, 0);

        assertEquals(HitType.WRECK, sameSpotResult.getHitType());
        assertEquals(3, sameSpotResult.getCellCount());

        assertEquals(HitType.WRECK, otherSpotOnSameShipResult.getHitType());
        assertEquals(3, otherSpotOnSameShipResult.getCellCount());

        assertEquals(HitType.WRECK, anotherSpotOnSameShipResult.getHitType());
        assertEquals(3, anotherSpotOnSameShipResult.getCellCount());
    }

    @Test
    void testFleetAddShip() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	Ship newShip = new Ship(List.of(new Cell(0, 1, 'C')));
    	assertEquals(1, fleet.addShip(newShip));
        assertEquals(4, fleet.size());
    }

    @Test
    void testFleetAddShip_noFit() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	Ship newShip = new Ship(List.of(
                new Cell(0, 1, 'C'),
                new Cell(0, 2, 'C'),
                new Cell(0, 3, 'C'),
                new Cell(0, 4, 'C')));

    	assertEquals(0, fleet.addShip(newShip));
        assertEquals(3, fleet.size());
    }

    @Test
    void testFleetAddShip_overlap() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	Ship newShip = new Ship(List.of(
                new Cell(0, 0, 'C'),
                new Cell(1, 0, 'C'),
                new Cell(2, 0, 'C'),
                new Cell(3, 0, 'C')));

    	assertEquals(0, fleet.addShip(newShip));
        assertEquals(3, fleet.size());
    }

    @Test
    void testFleetHasRemainingShips_singleHit() {
    	Fleet fleet = new Fleet(ships, 4, 4);

        fleet.hitObjectAt(0, 0);
        assertTrue(fleet.hasRemainingShips());
    }

    @Test
    void testFleetHasRemainingShips_allHit() {
        Fleet fleet = new Fleet(ships, 4, 4);
        fleet.hitObjectAt(0, 0);
        fleet.hitObjectAt(0, 1);
        fleet.hitObjectAt(3, 2);

        assertFalse(fleet.hasRemainingShips());
    }

    @Test
    void testFleetGetShipAt() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	assertEquals(ships[0], fleet.getShipAt(0, 0));
    	assertEquals(ships[1], fleet.getShipAt(0, 1));
    	assertEquals(ships[2], fleet.getShipAt(3, 2));
        assertNull(fleet.getShipAt(1, 1));
    }

    @Test
    void testFleetGetShips() {
    	Fleet fleet = new Fleet(ships, 4, 4);
    	assertTrue(fleet.getShips().contains(ships[0]));
        assertTrue(fleet.getShips().contains(ships[1]));
        assertTrue(fleet.getShips().contains(ships[2]));
    }

    @Test
    void testFleetPrintGrid() {
    	Fleet fleet = new Fleet(ships, 4, 4);
        var gridString = fleet.printGrid();
        assertEquals("ABBB\nA...\nA...\n..AA", gridString);

    	fleet.hitObjectAt(0, 0);
    	gridString = fleet.printGrid();
        assertEquals("*BBB\n*...\n*...\n..AA", gridString);

        fleet.hitObjectAt(0, 1);
        gridString = fleet.printGrid();
        assertEquals("****\n*...\n*...\n..AA", gridString);

        fleet.hitObjectAt(3, 2);
        gridString = fleet.printGrid();
        assertEquals("****\n*...\n*...\n..**", gridString);
    }
}