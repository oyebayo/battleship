package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.Cell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
class CellTest {
    @ParameterizedTest
    @CsvSource({
            "1, 1, 'A', 1, 1, 'A', true",
            "1, 1, 'A', 1, 1, 'B', true",
            "1, 1, 'A', 2, 2, 'A', false"
    })
    void equalsTest(int row1, int col1, char letter1, int row2, int col2, char letter2, boolean expected) {
        Cell cell1 = new Cell(row1, col1, letter1);
        Cell cell2 = new Cell(row2, col2, letter2);
        if (expected) {
            assertEquals(cell1, cell2);
        } else {
            assertNotEquals(cell1, cell2);
        }
    }
    @Test
    void equalsReturnsFalseForNullObject() {
        Cell cell = new Cell(1, 1, 'A');
        assertNotEquals(null, cell);
    }

    @Test
    void equalsReturnsFalseForNonCellTypeObject() {
        Cell cell = new Cell(1, 1, 'A');
        Object nonCellObject = new Object();
        assertNotEquals(cell, nonCellObject);
    }

    @Test
    void hashCodeTest() {
        Cell cell1 = new Cell(1, 1, 'A');
        Cell cell2 = new Cell(1, 1, 'B');
        assertEquals(cell1.hashCode(), cell2.hashCode());
    }

    @Test
    void hitChangesCellToWreckCharacter() {
        Cell cell = new Cell(1, 1, 'A');
        cell.hit();
        assertEquals(Cell.WRECK_CHARACTER, cell.getLetter());
    }

    @Test
    void isHitReturnsTrueForWreckCharacter() {
        Cell cell = new Cell(1, 1, Cell.WRECK_CHARACTER);
        assertTrue(cell.isHit());
    }

    @Test
    void isHitReturnsFalseForNonWreckCharacter() {
        Cell cell = new Cell(1, 1, 'A');
        assertFalse(cell.isHit());
    }

    @Test
    void getRowReturnsCorrectRow() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals(1, cell.getRow());
    }

    @Test
    void getColumnReturnsCorrectColumn() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals(1, cell.getColumn());
    }

    @Test
    void getLetterReturnsCorrectLetter() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals('A', cell.getLetter());
    }

    @Test
    void toStringReturnsCorrectFormat() {
        Cell cell = new Cell(1, 1, 'A');
        assertEquals("A (1, 1)", cell.toString());
    }
}
