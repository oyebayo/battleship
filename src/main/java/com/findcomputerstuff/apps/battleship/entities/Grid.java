package com.findcomputerstuff.apps.battleship.entities;

public class Grid {
    private final char[][] chars;
    public Grid(String[] gridStrings){
        int rows = gridStrings.length;
        int cols = gridStrings[0].length();

        chars = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            chars[i] = gridStrings[i].toCharArray();
        }
    }

    public Fleet convertToFleet(){
        Ship[] ships = new Ship[]{};
        Fleet fleet = new Fleet(ships, chars.length, chars[0].length);

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                char currentChar = chars[i][j];

                // Skip dots (empty cells)
                if (currentChar == Cell.BLANK_CHARACTER) {
                    continue;
                }

                Cell thisCell = new Cell(i, j, currentChar);
                Ship neighbourShip = findAnyAdjacentShipWithSameLabel(i, j, fleet);
                // If no adjacent ship found, create a new ship ID and add the current cell
                if (neighbourShip == null) {
                    Cell[] cells = new Cell[]{thisCell};
                    Ship ship = new Ship(cells);
                    fleet.addShip(ship);
                } else {
                    neighbourShip.addCell(thisCell);
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

            if (newRow >= 0 && newRow < chars.length && newCol >= 0 && newCol < chars[row].length) {
                Ship adjacentShip = fleet.getShipAt(newRow, newCol);
                if (adjacentShip == null) continue;
                if (adjacentShip.getLabel() != chars[row][col]) continue;

                return adjacentShip;
            }
        }

        return null;
    }
}
