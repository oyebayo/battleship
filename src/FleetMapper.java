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
                char adjacentCellLetter = grid[newRow][newCol];
                boolean mightBePartOfAnotherShip = hasSameLetterNeighbor(grid, newRow, newCol, scanDirection, adjacentCellLetter);
                Ship adjacentShip = fleet.getShipAt(newRow, newCol);

                if (adjacentShip == null || mightBePartOfAnotherShip) continue;

                return adjacentShip;
            }
        }

        return null;
    }
    private static boolean hasSameLetterNeighbor(char[][] grid, int row, int col, GridScanDirection scanDirection, char scanLetter) {
        int[][] positionsToCheck;

        if (scanDirection == GridScanDirection.UP || scanDirection == GridScanDirection.DOWN) {
            positionsToCheck = new int[][]{{row, col + 1}, {row, col - 1}};
        } else {
            positionsToCheck = new int[][]{{row + 1, col}, {row - 1, col}};
        }

        for (int[] position : positionsToCheck) {
            int newRow = position[0];
            int newCol = position[1];

            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[row].length) {
                if (grid[newRow][newCol] == scanLetter) {
                    return true;
                }
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
