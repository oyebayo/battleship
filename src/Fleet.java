import java.util.ArrayList;
import java.util.List;

public class Fleet {
	private int maxColumns;
	private int maxRows;
	private List<Ship> ships;

	public Fleet(int maxRows, int maxColumns){
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
		ships = new ArrayList<Ship>(0);
	}

	public Ship getShipAt(int row, int column) {
		for(Ship ship : ships) {
			for (Cell cell : ship.getCells()) {
				if (cell.getColumn() == column && cell.getRow() == row) {
					// we found a ship
					return ship;
				}
			}
		}
		return null;
	}
	
	public boolean hasRemainingShips() {
		for(Ship ship : ships) {
			if (!ship.isWreck()) return true;
		}
		return false;
	}
	
	public HitResult hitObjectAt(int row, int column) {
		Ship ship = getShipAt(row, column);
		HitResult result = new HitResult();
		if (ship == null) {
			result.HitType = HitType.BLANK;
		}else {
			result.CellCount = ship.getSize();
			result.HitType = ship.isWreck()? HitType.WRECK : HitType.SHIP;
			ship.markAsWreck(); // this will change the ship characters to asterisk if not already changed
		}
		
		return result;
	}
	
	public int addShip(Ship ship) {
		if (!ship.canFitWithinDimensions(maxRows, maxColumns)) return 0;
		for (Ship existingShip : ships) {
			if (ship.overlapsWith(existingShip)){
				// this cell is occupied by another ship. Cannot add
				return 0;
			}
		}
		ships.add(ship);
		return 1;
	}
	
	public int getSize(){
        	return ships.size();
    }
}
