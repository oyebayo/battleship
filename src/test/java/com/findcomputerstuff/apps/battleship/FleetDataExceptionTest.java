package com.findcomputerstuff.apps.battleship;

import com.findcomputerstuff.apps.battleship.entities.FleetDataException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the FleetDataException method of the FleetDataException class
 * No setup is required as the constructor (and the method being tested) only requires a string message as a parameter.
 */
class FleetDataExceptionTest {

    @Test
    void testFleetDataExceptionMessageValidation() {
        String exceptionMessage = "Fleet data exception triggered";
        FleetDataException fleetDataException = new FleetDataException(exceptionMessage);

        assertEquals(exceptionMessage, fleetDataException.getMessage(), "Exception message should match the input message");
    }
}