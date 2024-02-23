package Services;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


import Abstracts.CommonProperties;


public class CommonService {
  
  public class IdInterface {
   int id = 0;
  }
  
  public static double decreaseByPercent(double amount, float percent) {
    return amount - (amount * percent);
  }

  public static double calculateDiscountedPrice(double price, String discountCode) {
    if (discountCode != null) {
      float discountAmount = Float.parseFloat(discountCode.split("-")[0]);
      float percent = (discountAmount / 100);
      price = decreaseByPercent(price, percent);
    }
    return get2Digits(price);
  }

  public static double get2Digits(double number) {
    return Double.parseDouble(new DecimalFormat("##.##").format(number));
  }

  public static LocalDate formatDate(String date) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd-MM-yyyy").parseStrict().toFormatter();
    return LocalDate.parse(date, formatter);
  }

  public static LocalTime formatTime(String date) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("HH:mm").parseStrict().toFormatter();
    return LocalTime.parse(date, formatter);
  }

  public static String shortDateFormat(LocalDateTime dateTime) {
    return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(dateTime);
  }

  public static Boolean dateIsValid(String date) {
    try {
      formatDate(date);
      return true;
    } catch (DateTimeParseException e) {
    }
    return false;
  }

  public static Boolean timeIsValid(String time) {
    try {
      formatTime(time);
      return true;
    } catch (DateTimeParseException e) {
    }
    return false;
  }

  public static void printOptions(List<String> options) {
    for (String option : options) {
      System.out.println(option);
    }
  }

  public static void menuHandler(List<String> options, List<Runnable> actions) {
    CommonService.printOptions(options);
    String choice = InputReaderService.getString("What do you mind to do?", CommonService.createArrayOf(options.size()));
    actions.get(Integer.parseInt(choice) - 1).run();
  }

  public static <T> List<Object> map(List<T> array, Function<? super T, Object> mapper) {
    return array.stream().map(mapper).toList();
  }

  public static <T> List<List<Object>> map2D(List<T> array, Function<? super T, List<Object>> mapper) {
    return array.stream().map(mapper).toList();
  }

  public static <T> List<T> filter(List<T> array, Predicate<? super T> predicate) {
    return array.stream().filter(predicate).toList();
  }

  public static List<Object> createArrayOf(int size) {
    List<Object> options = new ArrayList<>();
    for (int i = 1; i <= size; i++) {
      options.add("" + i);
    }
    return options;
  }

  public static List<Object> getAllIdsOfEntity(List<? extends CommonProperties> list) {
    List<Object> ids = list.stream().map((item) -> (Object) Integer.valueOf(item.getId())).toList();
    return ids;
  }

}
