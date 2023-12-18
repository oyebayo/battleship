package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.FleetDataException;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FleetDataLoader {
    private final List<String[]> fleetData;
    private final PrintStream output;
    private int fleetCount;
    private final Scanner fileScanner;

    public FleetDataLoader(Scanner fileScanner, PrintStream output) {
        this.fleetData = new ArrayList<>();
        this.output = output;
        this.fileScanner = fileScanner;
    }

    boolean hasLoaded() {
        return fleetCount > 0;
    }

    String[] getFleetStrings(int index) {
        return fleetData.get(index);
    }

    // Method to load fleets from a file
    // Reads the fleet file and stores the fleet configurations in memory
    void load() {
        if (hasLoaded()) return;

        fleetCount = 0;
        try {
            loadFleets();
        } catch (Exception e) {
            output.println("Fleet file could not be loaded.");
        }
    }

    int getFleetCount() {
        return fleetCount;
    }

    private void loadFleets() throws FleetDataException {
        while (fileScanner.hasNext()) {
            int rows = fileScanner.hasNextInt() ? fileScanner.nextInt() : -1;
            int columns = fileScanner.hasNextInt() ? fileScanner.nextInt() : -1;
            if (rows == -1 || columns == -1) throw new FleetDataException("Invalid fleet file layout");

            fileScanner.nextLine(); // Consume the newline after columns

            List<String> fleetStrings = loadFleetStrings(rows, columns);
            if (fleetStrings.size() == rows) {
                fleetData.add(fleetStrings.toArray(new String[0]));
                fleetCount++;
            }
        }
    }
    private List<String> loadFleetStrings(int rows, int columns) {
        List<String> fleetStrings = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            String nextLine = fileScanner.nextLine();
            if (nextLine.length() != columns) break;
            fleetStrings.add(nextLine);
        }
        return fleetStrings;
    }
}