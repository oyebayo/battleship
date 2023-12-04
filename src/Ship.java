import java.util.ArrayList;
import java.util.List;

public class Ship {
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
	public boolean isWreck() {
		if (cells.isEmpty()) return false;
		//no need to check all the cells
		return cells.get(0).isHit();
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
}
