package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CellTest {
    @Test
    public void equalsReturnsTrueForSameCell() {
        Cell cell1 = new Cell(1, 1, 'A');
        Cell cell2 = new Cell(1, 1, 'A');
        assertEquals(cell1, cell2);
    }

    @Test
    public void equalsReturnsTrueForSameCell_withDifferentLetter() {
        Cell cell1 = new Cell(1, 1, 'A');
        Cell cell2 = new Cell(1, 1, 'B');
        assertEquals(cell1, cell2);
    }

    @Test
    public void equalsReturnsFalseForDifferentCells() {
        Cell cell1 = new Cell(1, 1, 'A');
        Cell cell2 = new Cell(2, 2, 'B');
        assertNotEquals(cell1, cell2);
    }

    @Test
    public void hitChangesCellToWreckCharacter() {
        Cell cell = new Cell(1, 1, 'A');
        cell.hit();
        assertEquals(Cell.WRECK_CHARACTER, cell.getLetter());
    }

    @Test
    public void isHitReturnsTrueForWreckCharacter() {
        Cell cell = new Cell(1, 1, Cell.WRECK_CHARACTER);
        assertTrue(cell.isHit());
    }

    @Test
    public void isHitReturnsFalseForNonWreckCharacter() {
        Cell cell = new Cell(1, 1, 'A');
        assertFalse(cell.isHit());
    }

    @Test
    public void getRowReturnsCorrectRow() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals(1, cell.getRow());
    }

    @Test
    public void getColumnReturnsCorrectColumn() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals(1, cell.getColumn());
    }

    @Test
    public void getLetterReturnsCorrectLetter() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals('A', cell.getLetter());
    }

    @Test
    public void toStringReturnsCorrectFormat() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals("A (1, 1)", cell.toString());
    }
}
