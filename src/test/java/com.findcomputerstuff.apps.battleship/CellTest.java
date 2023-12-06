package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CellTest {
    @Test
    public void testEquals() {
        Cell cell1 = new Cell(1, 1, 'A');
        Cell cell2 = new Cell(1, 1, 'A');
        Cell cell3 = new Cell(1, 1, '*');
        assertEquals(cell1, cell2);
        assertEquals(cell1, cell3);
    }
    @Test
    public void testHit() {
        Cell cell = new Cell(1, 1, 'A');
        assertFalse(cell.isHit());
        cell.hit();
        assertTrue(cell.isHit());
    }

    @Test
    public void tesGetLetter() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals('A', cell.getLetter());
    }
}
