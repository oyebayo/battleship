package com.findcomputerstuff.apps.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FleetLoader {
    private static final String FLEET_FILE_PATH = "fleets.txt";
    private final List<String[]> fleetFileContents;
    private final PrintStream output;
    private int fleetCount;

    public FleetLoader(PrintStream output) {
        this.fleetFileContents = new ArrayList<>();
        this.output = output;
    }

    public boolean isLoaded() {
        return fleetCount > 0;
    }

    public String[] getFleetStrings(int index) {
        return fleetFileContents.get(index);
    }
    // Method to load fleets from a file
    // Reads the fleet file and stores the fleet configurations in memory
    public int loadFleets() {
        int fleetCount = 0;
        try (Scanner fileScanner = new Scanner(new File(FLEET_FILE_PATH))) {
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
                    fleetFileContents.add(fleetStrings.toArray(new String[0]));
                    fleetCount++;
                }
            }
        } catch (FileNotFoundException e) {
            output.println("Fleet file could not be loaded.");
        }
        return fleetCount;
    }

    public int getFleetCount() {
        return fleetCount;
    }
}