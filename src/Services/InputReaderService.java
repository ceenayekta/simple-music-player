package Services;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class InputReaderService {
  private static Scanner inputReader = new Scanner(System.in);

  public static Scanner getInputReader() {
    return inputReader;
  }

  public static void end() {
    inputReader.close();
  }
  
  public static String getString(String message, List<Object> options) {
    if (message != null) {
      System.out.print(message);
    }
    String input = inputReader.nextLine();
    if (options != null) {
      String possibleOptions = "";
      for (Object option : options) {
        possibleOptions += option + ", ";
      }
      while (!options.contains(input)) {
        System.out.println("Invalid option. Possible options: " + possibleOptions);
        input = inputReader.nextLine();
      }
    }
    return input;
  }
  
  public static int getInteger(String message) {
    if (message != null) {
      System.out.print(message);
    }
    Integer input = inputReader.nextInt();
    inputReader.nextLine();
    while (!(input instanceof Integer)) {
      System.out.println("Invalid number.");
      input = inputReader.nextInt();
      inputReader.nextLine();
    }
    return input;
  }

  public static LocalDate getDate(String message) {
    String input = getString(message, null);
    while (!Utils.dateIsValid(input)) {
      System.out.println("Invalid date. Please insert with the format DD-MM-YYYY");
      input = getString(message, null);
    }
    LocalDate date = Utils.formatDate(input);
    return date;
  }

  public static LocalTime getTime(String message) {
    String input = getString(message, null);
    while (!Utils.timeIsValid(input)) {
      System.out.println("Invalid date. Please insert with the format HH:mm");
      input = getString(message, null);
    }
    LocalTime time = Utils.formatTime(input);
    return time;
  }

}
