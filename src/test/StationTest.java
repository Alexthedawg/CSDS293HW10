import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StationTest {
    @Test
    public void supervisorTest() {
        Station s = new Station(null, new Agent(null), new PassengerSupplier());
        Supervisor t = new Supervisor();

        assertEquals(null, s.getSupervisor());
        assertEquals(null, s.clearSupervisor());
        assertFalse(s.hasSupervisor());
        assertEquals(null, s.setSupervisor(t));
        assertEquals(t, s.getSupervisor());
        assertTrue(s.hasSupervisor());
        assertEquals(t, s.setSupervisor(new Supervisor()));
    }

    @Test
    public void isAutomatedTest() {
        Station s1 = new Station(null, new Agent(null), new PassengerSupplier());
        Station s2 = new Station(null, new Agent(List.of(new AutomatedAgent())), new PassengerSupplier());

        assertTrue(s2.isAutomated());
        assertFalse(s1.isAutomated());
    }

    @Test
    public void queueTest() {
        Station s1 = new Station(null, new Agent(null), new PassengerSupplier());

        assertTrue(s1.isQueueEmpty());
        assertEquals(null, s1.removeIrregularEntity());

        assertTrue(s1.refillQueue());
        assertFalse(s1.isQueueEmpty());

        assertFalse(s1.refillQueue());
    }

    @Test
    public void processPassengerEntityTest() {
        Station s1 = new Station(null, new Agent(null), new PassengerSupplier());

        assertEquals(-1.0, s1.processPassengerEntity(), 0.0);

        s1.refillQueue();

        assertTrue(s1.processPassengerEntity() >= -1.0);
    }
}
