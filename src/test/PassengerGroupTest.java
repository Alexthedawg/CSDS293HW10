import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PassengerGroupTest {

    @Test
    public void isIrregularTest() {
        Passenger[] p = new Passenger[50];
        Passenger[] d = new Passenger[50];

        for (int i = 0; i < p.length; i++) {
            p[i] = new Passenger(0.0, (i % 2 == 0)
                    ? PassengerType.REROUTED
                    : PassengerType.REGULAR);
            d[i] = new Passenger(0.0, PassengerType.REGULAR);
        }

        PassengerGroup group = new PassengerGroup(Arrays.stream(p));
        assertTrue(group.isIrregular());

        group = new PassengerGroup(Arrays.stream(d));
        assertFalse(group.isIrregular());
    }

    @Test
    public void needsSupervisorTest() {
        Passenger[] p = new Passenger[50];
        Passenger[] d = new Passenger[50];

        for (int i = 0; i < p.length; i++) {
            p[i] = new Passenger(0.0, (i % 2 == 0)
                    ? PassengerType.REROUTED
                    : PassengerType.REGULAR);
            d[i] = new Passenger(0.0, PassengerType.REGULAR);
        }

        PassengerGroup g1 = new PassengerGroup(Arrays.stream(p));
        assertTrue(g1.needsSupervisor());

        PassengerGroup g2 = new PassengerGroup(Arrays.stream(d));
        assertFalse(g2.needsSupervisor());
    }
}
