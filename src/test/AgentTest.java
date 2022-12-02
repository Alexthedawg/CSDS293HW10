import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AgentTest {

    @Test
    public void isAutomaticTest() {
        Agent a1 = new Agent(null);
        Agent a2 = new Agent(new ArrayList<>());
        assertFalse(a1.isAutomatedStation());
        assertTrue(a2.isAutomatedStation());
    }

}

