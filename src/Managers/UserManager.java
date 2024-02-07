package Managers;
import java.util.ArrayList;
import java.util.List;
import Abstracts.User;
import Entities.Admin;
import Entities.Artist;
import Entities.Listener;

public class UserManager {
  private static List<User> users = new ArrayList<>();

  public static void addUser(User user) {
    users.add(user);
  }

  public static void removeUser(User user) {
    users.remove(user);
  }

  public static void removeUserById(int userId) {
    User user = getUserById(userId);
    removeUser(user);
  }

  public static User getUserById(int userId) {
    for (User user : users) {
      if (user.getId() == userId) {
        return user;
      }
    }
    return null;
  }

  public static User getUserByUsername(String username) {
    for (User user : users) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  public static User validateCredentials(String username, String password) throws Exception {
    User user = getUserByUsername(username);
    if (user == null) throw new Exception("Username does not exist!");
    if (!user.getPassword().equals(password)) throw new Exception("Incorrect password!");
    return user;
  }

  public static List<User> getAllUsers() {
    return users;
  }

  public static void generateData() {
    addUser(new Admin("ceen", "ceen123"));
    addUser(new Artist("Bach", "bach#1"));
    addUser(new Listener("John", "doe12"));
  }
}
