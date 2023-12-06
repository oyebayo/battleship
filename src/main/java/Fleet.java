import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fleet {
	private final int maxColumns;
	private final int maxRows;
	private final List<Ship> ships;

	public Fleet(List<Ship> ships, int maxRows, int maxColumns){
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
		this.ships = ships;
	}

	public int getMaxRows(){
		return maxRows;
	}

	public Iterator<Ship> iterator() {
		return ships.iterator();
	}

	public int getMaxColumns(){
		return maxColumns;
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
		HitResult result;
		if (ship == null) {
			result = new HitResult(HitType.BLANK);
		} else {
			var hitType = ship.isWreck() ? HitType.WRECK : HitType.SHIP;
			result = new HitResult(hitType, ship.getSize());
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

    public String printGrid() {
		StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxColumns; j++) {
                Ship ship = getShipAt(i, j);
				if (ship == null){
					resultBuilder.append('.');
				} else {
					resultBuilder.append(ship.getCells().get(0).getLetter());
				}

				// see if we have reached the end of a row, but not end of the fleet
				if (i != maxRows - 1 && j == maxColumns - 1){ 
					resultBuilder.append('\n');
				} 
            }
        }
		return resultBuilder.toString();
    }

	public int size() {
		return ships.size();
	}
}
