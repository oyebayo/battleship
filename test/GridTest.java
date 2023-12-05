import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GridTest {
    private String[] gridStrings;

    @BeforeEach
    public void beforeEach() {
        gridStrings = new String[]{
                "DAAA..B.CCC.",
                "D..CCCB.....",
                "DBB.......CC"
        };
    }

    @Test
    public void testConvertToFleet() {
        Grid grid = new Grid(gridStrings);
        Fleet fleet = grid.ConvertToFleet();
        assertEquals(7, fleet.size());

        assertEquals(3, fleet.getShipAt(0, 0).getCells().size());
        assertEquals('D', fleet.getShipAt(0, 0).getLabel());

        assertEquals(3, fleet.getShipAt(0, 1).getCells().size());
        assertEquals('A', fleet.getShipAt(0, 1).getLabel());

        assertEquals(3, fleet.getShipAt(1, 3).getCells().size());
        assertEquals('C', fleet.getShipAt(1, 3).getLabel());

        assertEquals(2, fleet.getShipAt(0, 6).getCells().size());
        assertEquals('B', fleet.getShipAt(0, 6).getLabel());

        assertEquals(3, fleet.getShipAt(0, 8).getCells().size());
        assertEquals('C', fleet.getShipAt(0, 8).getLabel());

        assertEquals(2, fleet.getShipAt(2, 1).getCells().size());
        assertEquals('B', fleet.getShipAt(2, 1).getLabel());

        assertEquals(2, fleet.getShipAt(2, 10).getCells().size());
        assertEquals('C', fleet.getShipAt(2, 10).getLabel());
    }

    @Test
    public void testFindAnyShipAdjacentToCell() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(List.of(
                new Cell(0, 0, 'D'),
                new Cell(1, 0, 'D')
        ));
        Fleet fleet = new Fleet(List.of(neighbourShip), 3, 12);

        Ship ship = grid.findAnyShipAdjacentToCell(0, 1, fleet);

        assertEquals(2, ship.getCells().size());
        assertEquals('D', ship.getLabel());

    }

    @Test
    public void testFindAnyShipAdjacentToCell2() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(List.of(
                new Cell(1, 3, 'C'),
                new Cell(1, 4, 'C'),
                new Cell(1, 5, 'C')
        ));
        Fleet fleet = new Fleet(List.of(neighbourShip), 3, 12);

        Ship ship = grid.findAnyShipAdjacentToCell(1, 6, fleet);

        assertEquals(3, ship.getCells().size());
        assertEquals('C', ship.getLabel());

    }

    @Test
    public void testFindAnyShipAdjacentToCell3() {
        Grid grid = new Grid(gridStrings);

        Ship neighbourShip = new Ship(List.of(
                new Cell(1, 0, 'D'),
                new Cell(2, 0, 'D')
        ));
        Fleet fleet = new Fleet(List.of(neighbourShip), 3, 12);

        Ship ship = grid.findAnyShipAdjacentToCell(0, 1, fleet);
        assertNull(ship);
    }
}
