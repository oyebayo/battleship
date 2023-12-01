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

    private static Ship findShipAtAdjacentCell(char[][] grid, int row, int col, Fleet fleet) {
        char thisLetter = grid[row][col];

        CellScanDirection[] directions = CellScanDirection.values();
        for (CellScanDirection direction : directions) {
            int nRow = row + (direction == CellScanDirection.DOWN ? 1 : (direction == CellScanDirection.UP ? -1 : 0));
            int nCol = col + (direction == CellScanDirection.RIGHT ? 1 : (direction == CellScanDirection.LEFT ? -1 : 0));

            if (nRow >= 0 && nRow < grid.length && nCol >= 0 && nCol < grid[nRow].length) {
                char neighborLetter = grid[nRow][nCol];

                if (neighborLetter == thisLetter && !hasSameLetterNeighbor(grid, nRow, nCol, direction)) {
                    return fleet.getShipAt(nRow, nCol);
                }
            }
        }

        return null;
    }
    private enum CellScanDirection { UP, DOWN, LEFT, RIGHT }
    private static boolean hasSameLetterNeighbor(char[][] grid, int row, int col, CellScanDirection direction) {
        int nRow = row + (direction == CellScanDirection.DOWN ? 1 : (direction == CellScanDirection.UP ? -1 : 0));
        int nCol = col + (direction == CellScanDirection.RIGHT ? 1 : (direction == CellScanDirection.LEFT ? -1 : 0));

        if (nRow >= 0 && nRow < grid.length && nCol >= 0 && nCol < grid[nRow].length) {
            char thisLetter = grid[row][col];
            char neighborLetter = grid[nRow][nCol];

            if (direction == CellScanDirection.RIGHT || direction == CellScanDirection.LEFT) {
                // Check left and right neighbors
                return (col > 0 && grid[row][col - 1] == thisLetter) || (col < grid[row].length - 1 && grid[row][col + 1] == thisLetter);
            } else {
                // Check top and bottom neighbors
                return (row > 0 && grid[row - 1][col] == thisLetter) || (row < grid.length - 1 && grid[row + 1][col] == thisLetter);
            }
        }

        return false;
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
