import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Fleet fleet;

    @BeforeEach
    void beforeEach() {
        fleet = new Fleet(3, 12);
        /*
        DAAA..B.CCC.
        D.....B.....
        D.........CC
        */

        Ship ship1 = new Ship();
        ship1.getCells().add(new Cell(0, 0, 'D'));
        ship1.getCells().add(new Cell(1, 0, 'D'));
        ship1.getCells().add(new Cell(2, 0, 'D'));
        fleet.addShip(ship1);

        Ship ship2 = new Ship();
        ship2.getCells().add(new Cell(0, 1, 'A'));
        ship2.getCells().add(new Cell(0, 2, 'A'));
        ship2.getCells().add(new Cell(0, 3, 'A'));
        fleet.addShip(ship2);

        Ship ship3 = new Ship();
        ship3.getCells().add(new Cell(0, 6, 'B'));
        ship3.getCells().add(new Cell(1, 6, 'B'));
        fleet.addShip(ship3);

        Ship ship4 = new Ship();
        ship4.getCells().add(new Cell(0, 8, 'C'));
        ship4.getCells().add(new Cell(0, 9, 'C'));
        ship4.getCells().add(new Cell(0, 10, 'C'));
        fleet.addShip(ship4);

        Ship ship5 = new Ship();
        ship5.getCells().add(new Cell(2, 10, 'C'));
        ship5.getCells().add(new Cell(2, 11, 'C'));
        fleet.addShip(ship5);
    }

    @Test
    void testPlayer() {
        Player player = new Player("Test", fleet);
        assertEquals("Test", player.getName());
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScore() {
        Player player = new Player("Test", fleet);
        player.addScore(50);
        assertEquals(50, player.getScore());

        player.addScore(20);
        assertEquals(70, player.getScore());
    }

    @Test
    void testIsEliminated() {
        Player player = new Player("Test", fleet);
        assertFalse(player.isEliminated());

        player.getFleet().hitObjectAt(0, 0);
        assertFalse(player.isEliminated());

        player.getFleet().hitObjectAt(0, 3);
        player.getFleet().hitObjectAt(1, 6);
        player.getFleet().hitObjectAt(0, 9);
        player.getFleet().hitObjectAt(2, 10);
        assertTrue(player.isEliminated());
    }

    @AfterEach
    void afterEach() {
        fleet = null;
    }
}