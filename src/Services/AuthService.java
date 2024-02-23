package Services;
import Abstracts.DetailedUser;
import Abstracts.User;
import Enums.UserRole;
import Managers.UserManager;

public class AuthService {
  private static User user;
  private static boolean isLoggedIn;
  private static UserRole role;

  private static void setLoggedIn(boolean loggedIn) {
    isLoggedIn = loggedIn;
  }

  public static boolean isLoggedIn() {
    return isLoggedIn;
  }

  private static void setUser(User newUser) {
    user = newUser;
  }

  public static User getUser() {
    return user;
  }

  private static void setRole(UserRole userRole) {
    role = userRole;
  }

  public static UserRole getRole() {
    return role;
  }

  public static boolean login(User user) {
    if (user  == null) return false;
    setRole(user.getRole());
    setLoggedIn(true);
    setUser(user);
    return true;
  }

  public static void logout() {
    setLoggedIn(false);
    setRole(null);
    setUser(null);
  }

  public static User validateCredentials(String username, String password) throws Exception {
    User user = UserManager.getUserByUsername(username);
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
}
