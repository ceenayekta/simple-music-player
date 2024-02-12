package Services;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Utils {
  
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
    Utils.printOptions(options);
    String choice = InputReaderService.getString("What do you mind to do?", Utils.createArrayOf(options.size()));
    actions.get(Integer.parseInt(choice) - 1).run();
  }

  public static <T> List<Object> map(List<T> array, Function<? super T, ? extends Object> mapper) {
    return array.stream().map(mapper).collect(Collectors.toList());
  }

  public static <T> List<List<Object>> map2D(List<T> array, Function<? super T, ? extends List<Object>> mapper) {
    return array.stream().map(mapper).collect(Collectors.toList());
  }

  public static <T> List<T> filter(List<T> array, Predicate<? super T> predicate) {
    return array.stream().filter(predicate).collect(Collectors.toList());
  }

  public static List<Object> createArrayOf(int size) {
    List<Object> options = new ArrayList<>();
    for (int i = 1; i <= size; i++) {
      options.add("" + i);
    }
    return options;
  }

  // public static <T extends IdInterface> void getAllIds(List<T> list) {
  //   List<Integer> ids = list.stream().map((item) -> item.id).collect(Collectors.toList());
  //   list.forEach((obj) -> { ids.add(obj.id); });
  // }

  // public static <T extends IdInterface> T getById(ArrayList<T> array, int id) {
  //   for (T item : array) {
  //     if (item.id == id) {
  //         return item;
  //     }
  //   }
  //   return null;
  // }

  // --------------- wizards ----------------
  
  // public static User signupWizard(Scanner inputReader) {
  //   String fullname = Utils.getString(inputReader, "Set Fullname:", null);
  //   LocalDate birthday = Utils.getDate(inputReader, "Insert BirthDay (DD-MM-YYYY):");
  //   String username = Utils.getString(inputReader, "Set Username:", null);
  //   while (Database.getUserByUsername(username) != null) {
  //     System.out.println("This username already exists!");
  //     username = Utils.getString(inputReader, "Try another Username:", null);
  //   }
  //   String password = Utils.getString(inputReader, "Set Password:", null);
  //   return Database.signupUser(birthday, fullname, username, password);
  // }
  
  // public static City createCityWizard(Scanner inputReader) {
  //   String name = Utils.getString(inputReader, "Set Fullname:", null);
  //   int latitude = Utils.getInteger(inputReader, "Set Latitude");
  //   int longitude = Utils.getInteger(inputReader, "Set Longitude:");
  //   return Database.createCity(name, latitude, longitude);
  // }
  
  // public static Trip createTripWizard(Scanner inputReader) {
  //   LocalDate date = Utils.getDate(inputReader, "Insert Date (DD-MM-YYYY):");
  //   LocalTime time = Utils.getTime(inputReader, "Insert Time (HH:mm):");
  //   printCities(Database.getCities());
  //   String originId = Utils.getString(inputReader, "Choose Origin city (ID):", null);
  //   String destinationId = Utils.getString(inputReader, "Choose Destination city (ID):", null);
  //   City origin = Database.getCityById(Integer.parseInt(originId));
  //   City destination = Database.getCityById(Integer.parseInt(destinationId));
  //   printTransports(Database.getTransports());
  //   String transportId = Utils.getString(inputReader, "Choose Transport (ID):", null);
  //   Transport transport = Database.getTransportById(Integer.parseInt(transportId));
  //   return Database.createTrip(LocalDateTime.of(date, time), origin, destination, transport);
  // }

  // --------------- prints ------------------

  public static void printTable(List<String> labels, List<List<Object>> data) {
    for (String label : labels) {
        System.out.printf("%-15s", label);
    }
    System.out.println();
    for (int i = 0; i < labels.size(); i++) {
      System.out.printf("%-15s", "---------------");
    }
    System.out.println();
    for (List<Object> row : data) {
        for (Object cell : row) {
            System.out.printf("%-15s", cell);
        }
        System.out.println();
    }
    if (data.size() == 0) {
      System.out.printf("%-" + labels.size()*15 + "s", "No Data to Show!");
    }
    for (int i = 0; i < labels.size(); i++) {
      System.out.printf("%-15s", "---------------");
    }
    System.out.println();
  }

  // public static void printUsers(List<User> users) {
  //   List<String> labels = Arrays.asList("ID", "Fullname", "Birthday", "Username");
  //   List<List<Object>> data = Utils.map2D(users, (u) -> Arrays.asList(u.getId(), u.getFullname(), u.getBirthday(), u.getUsername()));
  //   Utils.printTable(labels, data);
  // }

  // public static void printCities(List<City> cities) {
  //   List<String> labels = Arrays.asList("ID", "Name");
  //   List<List<Object>> data = Utils.map2D(cities, (c) -> Arrays.asList(c.getId(), c.getName()));
  //   Utils.printTable(labels, data);
  // }

  // public static void printTrips(List<Trip> trips, Boolean upComing, boolean showPrice) {
  //   List<Trip> tripsToPrint = trips;
  //   if (upComing != null) {
  //     tripsToPrint = Utils.filter(trips, (trip) -> upComing ? trip.getDate().isAfter(LocalDateTime.now()) : trip.getDate().isBefore(LocalDateTime.now()));
  //   }
  //   List<String> labels = Arrays.asList("ID", "Transport", "Origin", "Destination", showPrice ? "Price" : "Passengers Count", "Duration", "Date", "Time");
  //   List<List<Object>> data = Utils.map2D(tripsToPrint, (t) -> Arrays.asList(t.getId(), t.getTransport().getType(), t.getOrigin().getName(), t.getDestination().getName(), showPrice ? "$" + t.getPrice() : t.getPassengers().size(), t.getDuration() + "h", t.getDate().toLocalDate(), t.getDate().toLocalTime()));
  //   Utils.printTable(labels, data);
  // }

  // public static void printTickets(List<Ticket> tickets, Boolean upComing) {
  //   List<Ticket> ticketsToPrint = tickets;
  //   if (upComing != null) {
  //     ticketsToPrint = Utils.filter(tickets, (t) -> upComing ? t.getTrip().getDate().isAfter(LocalDateTime.now()) : t.getTrip().getDate().isBefore(LocalDateTime.now()));
  //   }
  //   List<String> labels = Arrays.asList( "ID", "Is Cancelled", "Transport", "Origin", "Destination", "Price", "Duration", "Date", "Time");
  //   List<List<Object>> data = Utils.map2D(ticketsToPrint, (t) -> Arrays.asList(t.getId(), t.isCanceled() ? "Yes" : "No", t.getTrip().getTransport().getType(), t.getTrip().getOrigin().getName(), t.getTrip().getDestination().getName(), "$" + t.getPrice(), t.getTrip().getDuration() + "h", t.getTrip().getDate().toLocalDate(), t.getTrip().getDate().toLocalTime()));
  //   Utils.printTable(labels, data);
  // }

  // public static void printTransports(List<Transport> Transports) {
  //   List<String> labels = Arrays.asList("ID", "Type", "Capacity", "Speed", "Price/Hour");
  //   List<List<Object>> data = Utils.map2D(Transports, (t) -> Arrays.asList(t.getId(), t.getType(), t.getCapacity(), t.getSpeed() + "km/h", "$" + t.getPricePerHour()));
  //   Utils.printTable(labels, data);
  // }

}
