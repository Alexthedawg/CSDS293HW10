mport org.junit.Test;

import java.util.HashMap;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class MainTest {

    private static final int DEFAULT_THRESHOLD = 3;
    private static final Pattern FAILED_LOGINS_PATTERN = Pattern.compile("Failed *logins *from:\\s*", Pattern.CASE_INSENSITIVE);
    private static final Pattern ILLEGAL_USERS_PATTERN = Pattern.compile("Failed *logins *from:\\s*", Pattern.CASE_INSENSITIVE);

    @Test
    public void testSetSecurityThreshold() {
        int threshold = -1;
        String[] args;

        /* Normal Case */
        args = new String[]{"5"};
        threshold = Main.setSecurityThreshold(args);
        assertEquals(5, threshold);

        /* args.length > 1 */
        args = new String[]{"4", "2", "10"};
        threshold = Main.setSecurityThreshold(args);
        assertEquals(4, threshold);

        args = new String[]{"7", "hi", "m, ad,f nas, n"};
        threshold = Main.setSecurityThreshold(args);
        assertEquals(7, threshold);

        /* args.length == 0 */
        args = new String[0];
        threshold = Main.setSecurityThreshold(args);
        assertEquals(DEFAULT_THRESHOLD, threshold);

        /* args[0] <= 0 */
        assertThrows(IllegalArgumentException.class, () -> Main.setSecurityThreshold(new String[]{"0", "2345"}));
        assertThrows(IllegalArgumentException.class, () -> Main.setSecurityThreshold(new String[]{"-234", "0"}));
        assertThrows(IllegalArgumentException.class, () -> Main.setSecurityThreshold(new String[]{"-234", "0"}));

        /* args[0] is not an integer */
        assertThrows(NumberFormatException.class, () -> Main.setSecurityThreshold(new String[]{"hi", "3"}));
        assertThrows(NumberFormatException.class, () -> Main.setSecurityThreshold(new String[]{" asd", "3"}));
        assertThrows(NumberFormatException.class, () -> Main.setSecurityThreshold(new String[]{"-5.6", "3"}));
        assertThrows(NumberFormatException.class, () -> Main.setSecurityThreshold(new String[]{"5.6", "3"}));
    }

    @Test
    public void testGetHost() {
        Host expected;
        String line;

        /* Normal Case */
        expected = new Host("2.115.68.148", "host148-68-static.115-2-b.business.telecomitalia.it", 7);
        line = "2.115.68.148 (host148-68-static.115-2-b.business.telecomitalia.it): 7 times";
        assertEquals(expected, Main.getHost(line));

        /* domainName == null */
        expected = new Host("27.254.44.45", null, 1);
        line = "27.254.44.45: 1 time";
        assertEquals(expected, Main.getHost(line));
    }

    @Test
    public void testGetSecurityLogInput() {
        /* I have no idea how to test this. */
    }

    @Test
    public void testReportInfo() {
        String input = "Failed logins from: \\n    2.115.68.148 (host148-68-static.115-2-b.business.telecomitalia.it): 7 times \\n    27.254.44.45: 6 times \\n    31.186.13.221 (server-1.gencfb.org): 7 times \\n    43.255.188.161: 2968 times \\n    50.62.42.229 (ip-50-62-42-229.ip.secureserver.net): 7 times \\n    50.63.56.230 (ip-50-63-56-230.ip.secureserver.net): 7 times \\n    58.137.72.110: 7 times \\n    59.175.153.96 (96.153.175.59.broad.wh.hb.dynamic.163data.com.cn): 7 times \\n    63.251.162.66 (insynq-1.border2.sef003.pnap.net): 396 times\\n\\nIllegal users from: \\n    2.115.68.148 (host148-68-static.115-2-b.business.telecomitalia.it): 1 time \\n    27.254.44.45: 1 time \\n    31.186.13.221 (server-1.gencfb.org): 1 time \\n    50.62.42.229 (ip-50-62-42-229.ip.secureserver.net): 1 time \\n    50.63.56.230 (ip-50-63-56-230.ip.secureserver.net): 1 time \\n    58.137.72.110: 1 time \\n    59.175.153.96 (96.153.175.59.broad.wh.hb.dynamic.163data.com.cn): 1 time \\n    63.251.162.66 (insynq-1.border2.sef003.pnap.net): 521 times";
        String expected = "     server-1.gencfb.org, 43.255.188.161, \\\\n     96.153.175.59.broad.wh.hb.dynamic.163data.com.cn, \\\\n     ip-50-63-56-230.ip.secureserver.net, \\\\n     insynq-1.border2.sef003.pnap.net, \\\\n     58.137.72.110, \\\\n     host148-68-static.115-2-b.business.telecomitalia.it, \\\\n     ip-50-62-42-229.ip.secureserver.net, \\\\n     27.254.44.45";
        HashMap list = new HashMap();

        list = Main.collectHostInfo(FAILED_LOGINS_PATTERN, input, list);
        list = Main.collectHostInfo(ILLEGAL_USERS_PATTERN, input, list);

        String actual = Main.reportInfo(list, DEFAULT_THRESHOLD);

        /* Normal Case */
    }
}

