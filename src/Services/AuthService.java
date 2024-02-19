package Services;
import Abstracts.User;
import Enums.UserRole;

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
}
