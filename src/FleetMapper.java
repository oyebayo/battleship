public class FleetMapper {
    public static Fleet createFleet(String[] fleetStrings){
        char[][] grid = transformToGrid(fleetStrings);
        Fleet fleet = new Fleet(grid.length, grid[0].length);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char currentChar = grid[i][j];

                // Skip dots (empty cells)
                if (currentChar == '.') {
                    continue;
                }

                Cell thisCell = new Cell(i, j, currentChar);
                Ship neighbourShip = findShipAtAdjacentCell(grid, i, j, fleet);
                // If no adjacent ship found, create a new ship ID and add the current cell
                if (neighbourShip == null) {
                    Ship ship = new Ship();
                    ship.getCells().add(thisCell);
                    fleet.addShip(ship);
                } else {
                    neighbourShip.getCells().add(thisCell);
                }
            }
        }
        return fleet;
    }

    private enum GridScanDirection { UP, DOWN, LEFT, RIGHT }
    private static Ship findShipAtAdjacentCell(char[][] grid, int row, int col, Fleet fleet) {
        for (GridScanDirection scanDirection : GridScanDirection.values()) {
            int newRow = row;
            int newCol = col;

            switch (scanDirection) {
                case UP:
                    newRow--;
                    break;
                case DOWN:
                    newRow++;
                    break;
                case LEFT:
                    newCol--;
                    break;
                case RIGHT:
                    newCol++;
                    break;
            }

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[row].length) {
                Ship adjacentShip = fleet.getShipAt(newRow, newCol);
                if (adjacentShip == null) continue;

                return adjacentShip;
            }
        }

        return null;
    }

    private static char[][] transformToGrid(String[] gridStrings) {
        int rows = gridStrings.length;
        int cols = gridStrings[0].length();

        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = gridStrings[i].toCharArray();
        }

        return grid;
    }
}
