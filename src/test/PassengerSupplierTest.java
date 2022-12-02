import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PassengerSupplierTest {
    @Test
    public void nextPassengerTest() {
        Passenger[] p = new Passenger[50];
        Passenger[] d = new Passenger[50];

        for (int i = 0; i < p.length; i++) {
            p[i] = new Passenger(0.0, (i % 2 == 0)
                    ? PassengerType.REROUTED
                    : PassengerType.REGULAR);
            d[i] = new Passenger(0.0, PassengerType.REGULAR);
        }

        PassengerSupplier p1 = new PassengerSupplier();
        PassengerSupplier p2 = new PassengerSupplier(Arrays.stream(p), Arrays.stream(d));

        assertEquals(null, p1.getNextPassenger());
        assertEquals(null, p1.peekNextPassenger());
        assertTrue(p1.isEmpty());

        assertEquals(p2.peekNextPassenger(), p2.getNextPassenger());
        assertFalse(p2.isEmpty());

    }
}
