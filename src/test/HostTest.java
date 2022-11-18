import org.junit.Test;

import static org.junit.Assert.*;

public class HostTest {
    @Test
    public void testEquals() {
        Host h1, h2;

        /* Normal Cases */
        h1 = new Host("ipAddress", "domainName", 4);
        h2 = new Host("ipAddress", "domainName", 4);
        assertTrue(h1.equals(h2));
        assertTrue(h2.equals(h1));

        /* Normal Case w/ different tries */
        h1 = new Host("ipAddress", "domainName", 4);
        h2 = new Host("ipAddress", "domainName", 5);
        assertTrue(h1.equals(h2));
        assertTrue(h2.equals(h1));

        /* Null Cases */
        Host dummy = null;
        assertThrows(NullPointerException.class, () -> dummy.equals(new Host("", "", 0)));
        assertFalse(h1.equals(null));

        /* Same ipAddress, different domainName */
        h1 = new Host("ipAddress", "diffName", 4);
        h2 = new Host("ipAddress", "domainName", 5);
        assertFalse(h1.equals(h2));
        assertFalse(h2.equals(h1));

        /* Same domainName, different ipAddress */
        h1 = new Host("diffAddress", "domainName", 4);
        h2 = new Host("ipAddress", "domainName", 5);
        assertTrue(h1.equals(h2));
        assertTrue(h2.equals(h1));

        /* Domain Name == null */
        h1 = new Host("diffAddress", null, 4);
        h2 = new Host("ipAddress", null, 5);
        assertFalse(h1.equals(h2));
        assertFalse(h2.equals(h1));

        h1 = new Host("ipAddress", null, 4);
        h2 = new Host("ipAddress", null, 5);
        assertTrue(h1.equals(h2));
        assertTrue(h2.equals(h1));
    }

    @Test
    public void testToString() {
        Host h1;

        /* Normal Case */
        h1 = new Host("ipAddress", "diffName", 4);
        assertEquals("diffName", h1.toString());

        /* Domain Name == null */
        h1 = new Host("ipAddress", null, 4);
        assertEquals("ipAddress", h1.toString());
    }
}

