package Managers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Abstracts.DetailedUser;
import Abstracts.User;
import Entities.Admin;
import Entities.Artist;
import Entities.Listener;
import Enums.UserRole;

public class UserManager {
  private static List<User> users = new ArrayList<>();

  public static List<User> getAllUsers() {
    return users;
  }

  public static void addUser(User user) {
    users.add(user);
  }

  public static void removeUser(User user) {
    users.remove(user);
  }

  public static DetailedUser createUser(String fullname, String username, String password, String bio, String nickname) {
    DetailedUser newUser = new Artist(fullname, username, password, bio, nickname);
    addUser(newUser);
    return newUser;
  }

  public static DetailedUser createUser(String fullname, String username, String password, String bio) {
    DetailedUser newUser = new Listener(fullname, username, password, bio);
    addUser(newUser);
    return newUser;
  }

  public static void removeUserById(int userId) {
    User user = getUserById(userId);
    removeUser(user);
  }

  public static User getUserById(int userId) {
    Optional<User> user = users.stream().filter(u -> u.getId() == userId).findFirst();
    return user.orElse(null);
  }

  public static List<User> getUsersSortedAlphabetically() {
    List<User> sortedUsers = users.stream().sorted((u1, u2) -> u1.getFullname().compareToIgnoreCase(u2.getFullname())).toList();
    return sortedUsers;
  }

  public static List<User> getUsersSortedByPlayTime(UserRole role) {
    List<User> sortedUsers = getUserByRole(role).stream().sorted((u1, u2) -> {
      if (u1 instanceof DetailedUser && u2 instanceof DetailedUser) {
        return ((DetailedUser)u1).getPlayTimeInSeconds().compareTo(((DetailedUser)u2).getPlayTimeInSeconds());
      }
      return -1;
    }).toList();
    return sortedUsers;
  }

  public static List<User> getUserByRole(UserRole role) {
    List<User> filteredUsers = users.stream().filter(u -> u.getRole() == role).toList();
    return filteredUsers;
  }

  public static List<User> getBannedUsers() {
    List<User> filteredUsers = users.stream().filter(u -> {
      if (u instanceof DetailedUser) {
        return ((DetailedUser)u).isBanned();
      }
      return false;
    }).toList();
    return filteredUsers;
  }

  public static List<User> filterUsersByName(String query) {
    List<User> user = users.stream().filter(s -> s.getFullname().toLowerCase().contains(query.toLowerCase())).toList();
    return user;
  }

  public static User getUserByUsername(String username) {
    Optional<User> user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    return user.orElse(null);
  }

  public static User validateCredentials(String username, String password) throws Exception {
    User user = getUserByUsername(username);
    if (user == null) throw new Exception("Username does not exist!");
    else if (!user.getPassword().equals(password)) throw new Exception("Incorrect password!");
    else if (user instanceof DetailedUser && ((DetailedUser)user).isBanned()) throw new Exception("Your account is banned! Please contact support@example.com");
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

  public static void generateData() {
    addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
