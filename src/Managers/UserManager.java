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

  public static User createUser(String fullname, String username, String password, String bio, String nickname) {
    User newUser = new Artist(fullname, username, password, bio, nickname);
    addUser(newUser);
    return newUser;
  }

  public static User createUser(String fullname, String username, String password, String bio) {
    User newUser = new Listener(fullname, username, password, bio);
    addUser(newUser);
    return newUser;
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

  public static void updatePassword(User user, String currentPassword, String newPassword) throws Exception {
    if (validateCredentials(user.getUsername(), currentPassword) != null) {
      user.setPassword(newPassword);
    }
  }

  public static void updateUsername(User user, String newUsername) throws Exception {
    try {
      validateCredentials(newUsername, "");
    } catch (Exception e) {
      if (e.getMessage().equals("Username does not exist!")) {
        user.setUsername(newUsername);
      } else {
        throw new Exception("Username is taken by another user!");
      }
    }
  }

  public static List<User> getAllUsers() {
    return users;
  }

  public static void generateData() {
    addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
