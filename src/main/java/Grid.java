import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final char[][] grid;
    public Grid(String[] gridStrings){
        int rows = gridStrings.length;
        int cols = gridStrings[0].length();

        grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = gridStrings[i].toCharArray();
        }
    }

    public Fleet ConvertToFleet(){
        List<Ship> ships = new ArrayList<>();
        Fleet fleet = new Fleet(ships, grid.length, grid[0].length);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char currentChar = grid[i][j];

                // Skip dots (empty cells)
                if (currentChar == Cell.BLANK_CHARACTER) {
                    continue;
                }

                Cell thisCell = new Cell(i, j, currentChar);
                Ship neighbourShip = findAnyAdjacentShipWithSameLabel(i, j, fleet);
                // If no adjacent ship found, create a new ship ID and add the current cell
                if (neighbourShip == null) {
                    List<Cell> shipCells = new ArrayList<>();
                    shipCells.add(thisCell);
                    Ship ship = new Ship(shipCells);
                    fleet.addShip(ship);
                } else {
                    neighbourShip.getCells().add(thisCell);
                }
            }
        }
        return fleet;
    }

    public Ship findAnyAdjacentShipWithSameLabel(int row, int col, Fleet fleet) {
        for (GridScanDirection scanDirection : GridScanDirection.values()) {
            int newRow = row;
            int newCol = col;

            switch (scanDirection) {
                case UP -> newRow--;
                case DOWN -> newRow++;
                case LEFT -> newCol--;
                case RIGHT -> newCol++;
            }

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[row].length) {
                Ship adjacentShip = fleet.getShipAt(newRow, newCol);
                if (adjacentShip == null) continue;
                if (adjacentShip.getLabel() != grid[row][col]) continue;

                return adjacentShip;
            }
        }

        return null;
    }
}
