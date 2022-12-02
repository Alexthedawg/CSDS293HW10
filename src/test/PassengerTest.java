import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {

    @Test
    public void getPassengerTypeTest() {
        Passenger p = new Passenger(2.5, PassengerType.REROUTED);
        Passenger d = new Passenger(2.5, PassengerType.REGULAR);

        assertEquals(PassengerType.REROUTED, p.getPassengerType());
        assertEquals(PassengerType.REGULAR, d.getPassengerType());
    }

    @Test
    public void isIrregularTest() {
        Passenger p = new Passenger(2.5, PassengerType.REROUTED);
        Passenger d = new Passenger(2.5, PassengerType.REGULAR);

        assertTrue(p.isIrregular());
        assertFalse(d.isIrregular());
    }

    @Test
    public void needsSupervisorTest() {
        Passenger p = new Passenger(2.5, PassengerType.REROUTED);
        Passenger d = new Passenger(2.5, PassengerType.REGULAR);
        Passenger s = new Passenger(2.5, PassengerType.EXTRA_BAGGAGE);

        assertTrue(p.needsSupervisor());
        assertFalse(d.needsSupervisor());
        assertFalse(s.needsSupervisor());
    }
}
