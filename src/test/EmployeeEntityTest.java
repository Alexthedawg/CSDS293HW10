import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class EmployeeEntityTest {

    @Test
    public void getPassengerProcessTimeTest() {
        Passenger p = new Passenger(2.5, PassengerType.REGULAR);
        Passenger d = new Passenger(1.0, PassengerType.OVERBOOKED);

        assertEquals(2.5, Supervisor.getPassengerProcessTime(p), 0.1);
        assertEquals(1.5, Supervisor.getPassengerProcessTime(d), 0.1);
    }

    @Test
    public void getGroupProcessTimeTest() {
        Passenger p = new Passenger(2.5, PassengerType.REGULAR);
        Passenger d = new Passenger(1.0, PassengerType.OVERBOOKED);
        PassengerGroup g = new PassengerGroup(Stream.of(p, d));

        assertEquals(4.0 * 0.8, Supervisor.getGroupProcessTime(g), 0.1);
    }

    @Test
    public void setGetFinishBusyTest() {
        Supervisor s = new Supervisor();

        assertTrue(s.setBusy(2.5));
        assertFalse(s.setBusy(5.0));
        assertEquals(2.5, s.getBusyTime(), 0.1);
        assertEquals(2.5, s.finishTask(), 0.1);

    }
}
