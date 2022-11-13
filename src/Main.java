import java.io.*;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Objects;

public final class Main {
  
  private static final int DEFAULT_THRESHOLD = 3;
  
  private final HashMap<String, String> denyList = new HashMap<>();
  
  public static void main(String[] args) {
    
    /* Setting the security threshold. */
    final int threshold = setSecurityThreshold(args);
    
    /* Reading security log input from standard input. */
    final String input = getSecurityLogInput();
    
    System.out.print(input);
  }
  
  /**
   * A method to set the security threshold. If no argument is
   * provided, then the threshold is set to the default 3.
   * @param args a String array that may contain a threshold value
   * @return the threshold value obtained from args or by default
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
    
    return threshold;
  }
  
  //TODO comment all this code.
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
}
