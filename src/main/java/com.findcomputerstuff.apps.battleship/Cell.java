package com.findcomputerstuff.apps.battleship;

public class Cell {
	public static final char WRECK_CHARACTER = '*';
	public static final char BLANK_CHARACTER = '.';
	public static final char NULL_CHARACTER = 0;
	private final int row;
	private final int column;
	private char letter;
	public Cell(int row, int column, char letter) {
		this.row = row;
		this.column = column;
		this.letter = letter;
	}
	public void hit()
	{
		letter = WRECK_CHARACTER;
	}
	public boolean isHit() {
		return letter == WRECK_CHARACTER;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cell that = (Cell) o;
		return row == that.getRow() && column == that.getColumn();
	}
    public char getLetter() {
        return letter;
    }

	@Override
	public String toString() {
		return letter + " (" + row + ", " + column + ")";
	}
	
}
