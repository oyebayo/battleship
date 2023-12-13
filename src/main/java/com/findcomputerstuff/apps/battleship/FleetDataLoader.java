package com.findcomputerstuff.apps.battleship;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FleetDataLoader {
    public static final String FLEET_FILE_PATH = "fleets.txt";
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
            while (fileScanner.hasNext()) {
                int rows = fileScanner.nextInt();
                int columns = fileScanner.nextInt();
                fileScanner.nextLine(); // Consume the newline after columns

                List<String> fleetStrings = new ArrayList<>();
                for (int i = 0; i < rows; i++) {
                    String nextLine = fileScanner.nextLine();
                    if (nextLine.length() != columns) break;
                    fleetStrings.add(nextLine);
                }

                if (fleetStrings.size() == rows) {
                    fleetData.add(fleetStrings.toArray(new String[0]));
                    fleetCount++;
                }
            }
        } catch (Exception e) {
            output.println("Fleet file could not be loaded.");
        }
    }

    int getFleetCount() {
        return fleetCount;
    }
}