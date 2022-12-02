import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PassengerEntityTest {
    @Test
    public void processTimeTest() {
        Passenger p = new Passenger(6.6, PassengerType.REROUTED);
        assertEquals(6.6, p.getProcessTimeRequired(), 0.0);

        p.setProcessTimeRequired(3.0);
        assertEquals(3.0, p.getProcessTimeRequired(), 0.0);
    }
}
