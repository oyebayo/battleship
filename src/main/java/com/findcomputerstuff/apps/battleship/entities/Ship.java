package com.findcomputerstuff.apps.battleship.entities;

public class Ship {
	public static final String HAS_NO_CELLS = "NoCell Ship";
	private Cell[] cells;

	public Ship(Cell[] shipCells){
		this.cells = shipCells;
	}

	public void addCell(Cell cell) {
		Cell[] newCells = new Cell[this.cells.length + 1];
		System.arraycopy(this.cells, 0, newCells, 0, this.cells.length);
		newCells[this.cells.length] = cell;
		this.cells = newCells;
	}

	public void markAsWreck() {
        for (Cell cell : cells) {
            cell.hit();
        }
	}
	public int getSize() {
		return cells.length;
	}
	public char getLabel() {
		if (cells == null || cells.length == 0) return Cell.NULL_CHARACTER;
		return cells[0].getLetter();
	}
	public boolean isWreck() {
		for (Cell cell : cells) {
			if (!cell.isHit()) {
				return false;
			}
		}
		return true;
	}
	public boolean canFitWithinDimensions(int maxRows, int maxColumns) {
		for (Cell cell : cells) {
			if (cell.getRow() > maxRows - 1 || cell.getColumn() > maxColumns - 1) {
				// this ship is outside the given bounds. 
				return false;
			}
		}
		return true;
	}

	public Cell getCell(int index) {
		if (index >= 0 && index < cells.length) {
			return cells[index];
		} else {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + cells.length);
		}
	}

	public boolean overlapsWith(Ship otherShip) {
		for (Cell thisCell : this.cells) {
			for (Cell otherCell : otherShip.cells) {
				if (thisCell.equals(otherCell)) {
					// this cell is occupied by another ship. Cannot add
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (cells.length == 0) return HAS_NO_CELLS;

		Cell firstCell = cells[0];
		char letter = firstCell.getLetter();
		int row = firstCell.getRow();
		int column = firstCell.getColumn();
		int size = cells.length;

		String orientation = getOrientation().name();

		return String.format("%d%s at (%d,%d), %s", size, letter, row, column, orientation);
	}

	public ShipOrientation getOrientation() {
		if (cells.length <= 1) return ShipOrientation.SINGLE;

		int hCount = 0;
		int vCount = 0;
		var referenceCell = cells[0];
		for(Cell cell : cells){
			if (cell == referenceCell) continue;
			if (cell.getRow() == referenceCell.getRow()) hCount++;
			if (cell.getColumn() == referenceCell.getColumn()) vCount++;

			referenceCell = cell;
		}

		if (hCount >= 1 && vCount == 0) return ShipOrientation.HORIZONTAL;
		else if (vCount >= 1 && hCount == 0) return ShipOrientation.VERTICAL;
		else return ShipOrientation.UNKNOWN;
	}
}
