package com.findcomputerstuff.apps.battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
	public static final String HAS_NO_CELLS = "NoCell Ship";
	private final List<Cell> cells;
	public Ship(List<Cell> shipCells){
		cells = shipCells;
	}
	public List<Cell> getCells() {
		return cells;
	}


	public void markAsWreck() {
        for (Cell cell : cells) {
            cell.hit();
        }
	}
	public int getSize() {
		return cells.size();
	}
	public char getLabel() {
		if (cells == null || cells.isEmpty()) return Cell.NULL_CHARACTER;
		return cells.get(0).getLetter();
	}
	public boolean isWreck() {
		return cells.stream().allMatch(Cell::isHit);
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
	
	public boolean overlapsWith(Ship otherShip) {
		for (Cell cell : cells) {		
			for (Cell existingCell: otherShip.getCells()) {
				if (existingCell.equals(cell)){
					// this cell is occupied by another ship. Cannot add
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (cells.isEmpty()) return HAS_NO_CELLS;

		Cell firstCell = cells.get(0);
		char letter = firstCell.getLetter();
		int row = firstCell.getRow();
		int column = firstCell.getColumn();
		int size = cells.size();

		String orientation = getOrientation().name();

		return String.format("%d%s at (%d,%d), %s", size, letter, row, column, orientation);
	}

	public ShipOrientation getOrientation() {
		if (cells.size() <= 1) return ShipOrientation.SINGLE;

		int hCount = 0;
		int vCount = 0;
		var firstCell = cells.get(0);
		for(Cell cell : cells.subList(1, cells.size())){
			if (cell.getRow() == firstCell.getRow()) hCount++;
			if (cell.getColumn() == firstCell.getColumn()) vCount++;
		}

		if (hCount >= 1 && vCount == 0) return ShipOrientation.HORIZONTAL;
		else if (vCount >= 1 && hCount == 0) return ShipOrientation.VERTICAL;
		else return ShipOrientation.UNKNOWN;
	}
}
