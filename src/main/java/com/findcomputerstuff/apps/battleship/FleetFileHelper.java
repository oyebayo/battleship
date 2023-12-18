package com.findcomputerstuff.apps.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FleetFileHelper {
    private FleetFileHelper() {}
    public static final String FLEET_FILE_PATH = "fleet.txt";
    public static Scanner createFileScanner() throws FileNotFoundException {
        File fleetFile = new File(FLEET_FILE_PATH);
        return new Scanner(fleetFile);
    }
}
