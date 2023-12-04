import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ShipTest {
    @Test
    public void testShipCreation() {
        Ship ship = new Ship();
        assertEquals(0, ship.getSize());
        assertFalse(ship.isWreck());
    }

    @Test
    public void testShipMarkAsWreck() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));
        ship.markAsWreck();
        assertTrue(ship.isWreck());
    }

    @Test
    public void testShipGetSize() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));
        assertEquals(3, ship.getSize());
    }

    @Test
    public void testShipCanFitWithinDimensions() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));
        assertTrue(ship.canFitWithinDimensions(10, 10));
    }

    @Test
    public void testShipCanNotFitWithinDimensions() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));
        assertFalse(ship.canFitWithinDimensions(1, 1));
    }

    @Test
    public void testShipOverlapsWith() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));

        Ship otherShip = new Ship();
        otherShip.getCells().add(new Cell(0, 1, 'A'));
        otherShip.getCells().add(new Cell(1, 1, 'A'));
        otherShip.getCells().add(new Cell(1, 2, 'A'));

        assertTrue(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipDoesNotOverlapWith() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));

        Ship otherShip = new Ship();
        otherShip.getCells().add(new Cell(1, 1, 'A'));
        otherShip.getCells().add(new Cell(1, 2, 'A'));
        otherShip.getCells().add(new Cell(1, 3, 'A'));

        assertFalse(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipDoesNotOverlapWithItself() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));

        assertTrue(ship.overlapsWith(ship));
    }

    @Test
    public void testShipDoesNotOverlapWithEmptyShip() {
        Ship ship = new Ship();
        ship.getCells().add(new Cell(0, 0, 'A'));
        ship.getCells().add(new Cell(0, 1, 'A'));
        ship.getCells().add(new Cell(0, 2, 'A'));

        Ship otherShip = new Ship();
        assertFalse(ship.overlapsWith(otherShip));
    }

    @Test
    public void testShipDoesNotOverlapWithEmptyShip2() {
        Ship ship = new Ship();
        Ship otherShip = new Ship();
        assertFalse(otherShip.overlapsWith(ship));
    }
}
