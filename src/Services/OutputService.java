package Services;

import java.util.Arrays;
import java.util.List;

import Abstracts.User;
import Managers.UserManager;

public class OutputService {

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

  // --------------- wizards ----------------
  
  public static User signupWizard() {
    User user = null;
    String isArtist = InputReaderService.getString("Are you Artist or Listener? (a/l) ", Arrays.asList("a", "l"));
    String fullName = InputReaderService.getString("Enter your full name: ", null);
    String username = InputReaderService.getString("Enter your username: ", null);
    String bio = InputReaderService.getString("Tell us a short bio about " + fullName + ": ", null);
    while (UserManager.getUserByUsername(username) != null) {
      System.out.println("This username is already taken.");
      username = InputReaderService.getString("Please choose another one: ", null);
    }
    String password = InputReaderService.getString("Enter your password: ", null);
    if (isArtist.equals("a")) {
      String nickname = InputReaderService.getString("Enter your nickname: ", null);
      user = UserManager.createUser(fullName, username, password, bio, nickname);
    } else {
      user = UserManager.createUser(fullName, username, password, bio);
    }
    return user;
  }
}
