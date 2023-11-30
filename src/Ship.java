import java.util.ArrayList;
import java.util.List;

public class Ship {
	private List<Cell> cells;
	public Ship(){
		cells = new ArrayList<Cell>(0);
	}
	public List<Cell> getCells() {
		return cells;
	}
	
	public void markAsWreck() {
		for(int i = 0; i < cells.size(); i++) {
			cells.get(i).hit();
		}
	}
	public int getSize() {
		return cells.size();
	}
	public boolean isWreck() {
		//no need to check all the cells
		return cells.get(0).isHit();
	}
	
	public boolean canFitWithinDimensions(int maxRows, int maxColumns) {
		for (Cell cell : cells) {
			if (cell.getRow() > maxRows || cell.getColumn() < maxColumns) {
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
