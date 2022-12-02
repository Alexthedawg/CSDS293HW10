import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void buyTests() {
        Game g1 = new Game(new Supervisor(), 10000, new ArrayList<>(), new ArrayList<>());
        Game g2 = new Game(new Supervisor(), 0, new ArrayList<>(), new ArrayList<>());

        assertTrue(g1.buyInPersonStation(50));
        assertTrue(g1.buySupervisor(50));
        assertTrue(g1.buyAutomatedStation(50));

        assertFalse(g2.buyAutomatedStation(50));
        assertFalse(g2.buySupervisor(50));
        assertFalse(g2.buyInPersonStation(50));
    }

    @Test
    public void setSupervisorTest() {
        Supervisor s = new Supervisor();
        Game g1 = new Game(s, 10000, new ArrayList<>(), new ArrayList<>());

        assertEquals(s, g1.setSupervisor(new Supervisor()));
    }

    @Test
    public void stationsTest() {
        Game g1 = new Game(new Supervisor(), 10000, new ArrayList<>(), new ArrayList<>());
        for (int i = 0; i < 50; i++) {
            g1.buyAutomatedStation(0);
            g1.buyInPersonStation(0);
        }
        g1.buyAutomatedStation(0);


        assertEquals(101, g1.getNumberOfStations());
        assertEquals(51, g1.getNumberOfAutomaticStations());
        assertEquals(50, g1.getNumberOfInPersonStations());
    }

    @Test
    public void entitiesTest() {
        EmployeeEntity[] arr = new EmployeeEntity[50];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Agent(null);
            if (i % 2 == 0) {
                arr[i].setBusy(4.5);
            }
        }
        arr[49].setBusy(700.3);

        Game g1 = new Game(new Supervisor(), 10000, new ArrayList<>(List.of(arr)), new ArrayList<>());

        assertTrue(g1.isAnyEntitiesBusy());
        assertEquals(700.3, g1.maxTimeUntilNotBusy(), 0.1);
    }
}
