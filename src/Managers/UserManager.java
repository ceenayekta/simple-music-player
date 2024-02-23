package Managers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Abstracts.DetailedUser;
import Abstracts.User;
import Entities.Admin;
import Entities.Artist;
import Entities.Listener;
import Entities.Playlist;
import Enums.UserRole;
import Services.AuthService;

public class UserManager {
  private static List<User> users = new ArrayList<>();

  public static List<User> getAllUsers() {
    return users;
  }

  public static void addUser(User user) {
    users.add(user);
  }

  public static void removeUser(User user) {
    if (user instanceof DetailedUser) {
      for (Playlist playlist : ((DetailedUser)user).getPlaylists()) {
        PlaylistManager.removePlaylist(playlist, true);
      }
    }
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

  public static List<DetailedUser> getDetailedUsers(List<User> users) {
    List<DetailedUser> detailedUsers = new ArrayList<>();
    for (User user : users) {
      if (user instanceof DetailedUser) {
        detailedUsers.add((DetailedUser)user);
      }
    }
    return detailedUsers;
  }

  public static List<DetailedUser> getUsersSortedByPlayTime(UserRole role) {
    List<DetailedUser> filteredDetailedUsers = getDetailedUsers(getUserByRole(role));
    List<DetailedUser> sortedUsers = filteredDetailedUsers.stream().sorted((u1, u2) -> {
      return u1.getPlayTimeInSeconds().compareTo(u2.getPlayTimeInSeconds());
    }).toList();
    return sortedUsers;
  }

  public static List<User> getUserByRole(UserRole role) {
    List<User> filteredUsers = getNotBlockedUsers().stream().filter(u -> u.getRole() == role).toList();
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

  public static List<User> getNotBlockedUsers() {
    Integer userId = AuthService.isLoggedIn() ? AuthService.getUser().getId() : null;
    List<User> user = users.stream().filter(s -> s instanceof DetailedUser && !((DetailedUser)s).hasBlockedTheUser(userId)).toList();
    return user;
  }

  public static List<User> filterUsersByName(String query) {
    List<User> user = getNotBlockedUsers().stream().filter(s -> {
      return s.getFullname().toLowerCase().contains(query.toLowerCase()) || (s instanceof Artist ? ((Artist)s).getNickname().toLowerCase().contains(query.toLowerCase()) : true);
    }).toList();
    return user;
  }

  public static User getUserByUsername(String username) {
    Optional<User> user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    return user.orElse(null);
  }

  public static void generateData() {
    addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
