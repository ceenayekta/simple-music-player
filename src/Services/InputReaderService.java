package Services;
import java.util.Scanner;

public class InputReaderService {
  private static Scanner inputReader = new Scanner(System.in);

  public static Scanner getInputReader() {
    return inputReader;
  }

  public static void end() {
    inputReader.close();
  }

  public static String getString(String message) {
    if (message != null) {
      System.out.print(message);
    }
    return inputReader.next();
  }

}
