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
        // Check neighbors (up, down, left, right)
        char[] neighbors = {
            (row > 0) ? grid[row - 1][col] : ' ', // up
            (row < grid.length - 1) ? grid[row + 1][col] : ' ', // down
            (col > 0) ? grid[row][col - 1] : ' ', // left
            (col < grid[row].length - 1) ? grid[row][col + 1] : ' ' // right
        };

        for (char neighbor : neighbors) {
            if (neighbor == ' ' || neighbor == '.') return null;
            Ship possibleShip = fleet.getShipAt(row, col);
            if (possibleShip != null) return possibleShip;
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
