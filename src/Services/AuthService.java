package Services;
import Abstracts.User;
import Abstracts.User.UserRole;
import Entities.Admin;
import Entities.Artist;
import Entities.Listener;

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

  public static User login(User user) {
    if (user instanceof Admin) {
      setRole(UserRole.Admin);
    } else if (user instanceof Artist) {
      setRole(UserRole.Artist);
    } else if (user instanceof Listener) {
      setRole(UserRole.Listener);
    }
    setLoggedIn(true);
    setUser(user);
    return user;
  }

  public static void logout() {
    setLoggedIn(false);
    setUser(null);
  }
}
