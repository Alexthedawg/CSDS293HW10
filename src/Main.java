import java.io.*;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A class that parses user input to generate a blacklist of
 * Hosts that surpassed a specified threshold for failed logins
 * and illegal user access. A blacklist is reported to the user
 * via standard output.
 */
public final class Main {

    private static final int DEFAULT_THRESHOLD = 3;
    private static final Pattern FAILED_LOGINS_PATTERN = Pattern.compile("Failed *logins *from:\\s*", Pattern.CASE_INSENSITIVE);
    private static final Pattern ILLEGAL_USERS_PATTERN = Pattern.compile("Failed *logins *from:\\s*", Pattern.CASE_INSENSITIVE);
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");
    private static final Pattern DOMAIN_NAME_PATTERN = Pattern.compile("(?<=\\()[a-zA-Z0-9-.]{2,}(?=\\))");
    private static final Pattern TRIES_NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final int CHAR_LIM = 80;
    private static final String ENDLINE = "\\\n";
    private static final int ENDLINELEN = ENDLINE.length();
    private static final String NEWLINE = "     ";
    private static final String SEPARATOR = ", ";

    /**
     * Runs this program.
     * @param args the threshold for the run
     */
    public static void main(String[] args) {

        /* Setting the security threshold. */
        final int threshold = setSecurityThreshold(args);

        /* Reading security log input from standard input. */
        final String input = getSecurityLogInput();

        /* Initializing the denied list for the ip addresses / domains. */
        HashMap<Host, Integer> list = new HashMap<>();

        /* Collect the Host Info of failed logins and illegal users. */
        list = collectHostInfo(Main.FAILED_LOGINS_PATTERN, input, list);
        list = collectHostInfo(Main.ILLEGAL_USERS_PATTERN, input, list);

        /* Report Host Info to standard output. */
        String denyList = reportInfo(list, threshold);
        System.out.println(denyList);
    }

    /**
     * A method to set the security threshold. If no argument is
     * provided, then the threshold is set to the default 3. The
     * threshold may not be set to 0 or less.
     * @param args a String array that may contain a threshold value
     * @return the threshold value obtained from args or by default
     * @throws IllegalArgumentException if the threshold is set <= 0
     */
    public static int setSecurityThreshold(final String[] args) {
        /* Null check. */
        Objects.requireNonNull(args);

        /*
         * If there is an argument provided, then the threshold is
         * the first argument provided in the array, regardless of
         * any other arguments provided in the array. If there is no
         * argument provided, then the security threshold is set to
         * DEFAULT_THRESHOLD.
         */
        final int threshold = (args.length > 0)
                ? Integer.parseInt(args[0])
                : DEFAULT_THRESHOLD;

        if (threshold <= 0) throw new IllegalArgumentException("Threshold cannot be set to this value.");

        return threshold;
    }

    /**
     * A method to get input from command line and store it
     * in a String.
     * @return a String containing input from the command line
     */
    public static String getSecurityLogInput() {
        StringBuilder sb = new StringBuilder();

        System.out.println("Enter log summary below (press ctrl+d when finished):");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            CharBuffer buffer = CharBuffer.allocate(1024);

            while (reader.read(buffer) != -1) {
                buffer.flip();
                sb.append(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * A method to add Host information to a list that tracks the amount of tries.
     * The method adds Host information from a specified Pattern in the input
     * until it reaches a black line.
     *
     * @param pattern the Pattern of a section to search under
     * @param input   the user input to scan
     * @param list    the list to add Host info to
     * @return the list with the updated Hosts found
     */
    public static HashMap<Host, Integer> collectHostInfo(Pattern pattern, String input, HashMap<Host, Integer> list) {
        Scanner scanner = new Scanner(input);

        /* Check that the Pattern exists in the input given. */
        if (scanner.findWithinHorizon(pattern, 0) != null) {
            /* Run while the next line exists and is not blank. */
            do {
                String line = scanner.nextLine();
                if (line.length() == 0) { break; }
                Host host = getHost(line);
                /* Add host to list. */
                list.put(host, list.getOrDefault(host, 0) + host.tries());
            } while (scanner.hasNextLine());
        }
        scanner.close();
        return list;
    }

    /**
     * A method to get a Host from a formatted String of data.
     * @param line a String containing data to create a Host
     * @return a new Host based on the data of the line
     */
    public static Host getHost(String line) {
        Objects.requireNonNull(line);

        /* Initializing variables. */
        String ipAddress, domainName = null, tries;

        /* Getting ip address. */
        Scanner scanner = new Scanner(line);
        ipAddress = scanner.findWithinHorizon(Main.IP_ADDRESS_PATTERN, 0);

        /* Getting domain name (if it exists). */
        domainName = scanner.findWithinHorizon(Main.DOMAIN_NAME_PATTERN, 0);

        /* Getting number of tries. */
        tries = scanner.findWithinHorizon(Main.TRIES_NUMBER_PATTERN, 0);
        scanner.close();

        /* Make sure that a valid Host can be created from the info. */
        Objects.requireNonNull(ipAddress);
        Objects.requireNonNull(tries);

        return new Host(ipAddress, domainName, Integer.parseInt(tries));
    }

    /**
     * A method to report the data in the blacklisted Host list
     * to standard output.
     *
     * @param list      the list with the given Host Info
     * @param threshold the threshold for blacklisting Hosts.
     * @return
     */
    public static String reportInfo(HashMap<Host, Integer> list, int threshold) {
        String hosts = list.keySet().stream()
                .filter(host -> host.tries() >= threshold)
                .map(host -> host.toString())
                .collect(Collectors.joining("\n"));

        Scanner scanner = new Scanner(hosts);
        StringBuilder sb = new StringBuilder("     ");

        while (scanner.hasNext()) {
            String host = scanner.next();
            if (host.length() + sb.length() < CHAR_LIM - 1) {
                sb.append(host + SEPARATOR);
            } else {
                sb.append(ENDLINE + NEWLINE + host + SEPARATOR);
            }
        }
        scanner.close();
        sb.delete(sb.length() - ENDLINELEN, sb.length());
        return sb.toString();
    }
}
