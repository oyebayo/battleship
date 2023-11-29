import java.util.HashMap;
import java.util.Map;

public class ShipMapper {

    public static void main(String[] args) {
        String[] gridStrings = {
                "AA..B",
                "..C.B",
                "DEC.B",
                ".E..."
        };

        char[][] grid = transformToGrid(gridStrings);

        printGrid(grid);

        Map<Character, Integer> shipMap = mapShips(grid);

        // Display the mapping
        for (Map.Entry<Character, Integer> entry : shipMap.entrySet()) {
            System.out.println("Ship " + entry.getValue() + ": " + entry.getKey());
        }
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

    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static Map<Character, Integer> mapShips(char[][] grid) {
        Map<Character, Integer> shipMap = new HashMap<>();
        int shipId = 1;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char currentChar = grid[i][j];

                // Skip dots (empty cells)
                if (currentChar == '.') {
                    continue;
                }

                // Check neighbors
                int neighborShipId = getNeighborShipId(grid, i, j, shipMap);

                // If no adjacent ship found, assign a new ship ID
                if (neighborShipId == 0) {
                    shipMap.put(currentChar, shipId);
                    shipId++;
                } else {
                    shipMap.put(currentChar, neighborShipId);
                }
            }
        }

        return shipMap;
    }

    private static int getNeighborShipId(char[][] grid, int row, int col, Map<Character, Integer> shipMap) {
        char currentChar = grid[row][col];

        // Check neighbors (up, down, left, right)
        char[] neighbors = {
                (row > 0) ? grid[row - 1][col] : 0, // up
                (row < grid.length - 1) ? grid[row + 1][col] : 0, // down
                (col > 0) ? grid[row][col - 1] : 0, // left
                (col < grid[row].length - 1) ? grid[row][col + 1] : 0 // right
        };

        for (char neighbor : neighbors) {
            if (neighbor != 0 && neighbor != '.' && shipMap.containsKey(neighbor)) {
                return shipMap.get(neighbor);
            }
        }

        return 0; // No adjacent ship found
    }
}

