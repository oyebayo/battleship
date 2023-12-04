import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ShipTest {
    private List<Cell> shipCells;
    private List<Cell> noCells;
    private ArrayList<Cell> overlappingShipCells;
    private ArrayList<Cell> farShipCells;

    @BeforeEach
    void beforeEach() {
        shipCells = new ArrayList<>();
        shipCells.add(new Cell(0, 0, 'A'));
        shipCells.add(new Cell(0, 1, 'A'));
        shipCells.add(new Cell(0, 2, 'A'));

        noCells = new ArrayList<>();

        overlappingShipCells = new ArrayList<>();
        overlappingShipCells.add(new Cell(0, 1, 'A'));
        overlappingShipCells.add(new Cell(1, 1, 'A'));
        overlappingShipCells.add(new Cell(2, 1, 'A'));

        farShipCells = new ArrayList<>();
        farShipCells.add(new Cell(1, 1, 'A'));
        farShipCells.add(new Cell(1, 2, 'A'));
        farShipCells.add(new Cell(1, 3, 'A'));
    }
    @Test
    public void testShipCreation() {
        Ship ship = new Ship(shipCells);
        assertEquals(3, ship.getSize());
        assertFalse(ship.isWreck());
    }

    @Test
    public void testShipMarkAsWreck() {
        Ship ship = new Ship(shipCells);
        ship.markAsWreck();
        assertTrue(ship.isWreck());
    }

    @Test
    public void testShipGetSize() {
        Ship ship = new Ship(shipCells);
        assertEquals(3, ship.getSize());

        ship = new Ship(noCells);
        assertEquals(0, ship.getSize());
    }

    @Test
    public void testShipCanFitWithinDimensions() {
        Ship ship = new Ship(shipCells);
        assertTrue(ship.canFitWithinDimensions(10, 10));
    }

    @Test
    public void testShipCanNotFitWithinDimensions() {
        Ship ship = new Ship(shipCells);
        assertFalse(ship.canFitWithinDimensions(1, 1));
    }

    @Test
    public void testShipOverlapsWith() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(overlappingShipCells);

        assertTrue(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipDoesNotOverlapWith() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(farShipCells);
        assertFalse(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipOverlapsWithItself() {
        Ship ship = new Ship(shipCells);
        assertTrue(ship.overlapsWith(ship));
    }

    @Test
    public void testShipDoesNotOverlapWithEmptyShip() {
        Ship ship = new Ship(shipCells);
        Ship otherShip = new Ship(noCells);
        assertFalse(ship.overlapsWith(otherShip));
    }

    @AfterEach
    void afterEach() {
        shipCells = null;
        noCells = null;
        overlappingShipCells = null;
        farShipCells = null;
    }
}
