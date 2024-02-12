import java.util.Arrays;
import java.util.List;

import Abstracts.User;
import Controllers.LoginController;
import Managers.UserManager;
import Services.AuthService;
import Services.InputReaderService;
import Services.Utils;

public class UI {
  private static boolean stop = false;

  public static void run() {
    while (!stop) {
      if (AuthService.isLoggedIn()) showUserMenu();
      else showInitMenu();
    }
    lastMessage();
  }

  public static void showUserMenu() {
    List<String> options = Arrays.asList(
      "1. Nothing",
      "2. Logout",
      "3. Exit"
    );
    List<Runnable> actions = Arrays.asList(
      () -> {},
      () -> logout(),
      () -> exit()
    );
    Utils.menuHandler(options, actions);
  }

  private static void showInitMenu() {
    List<String> options = Arrays.asList(
      "1. Login",
      "2. Sign Up",
      "3. Exit"
    );
    List<Runnable> actions = Arrays.asList(
      () -> login(),
      () -> createAccount(),
      () -> exit()
    );
    Utils.menuHandler(options, actions);
  }

  public static void login() {
    LoginController.run(() -> {
      System.out.println("Glad to see you " + AuthService.getUser().getFullname() + "!");
    });
  }

  public static void lastMessage() {
    if (AuthService.isLoggedIn()) {
      System.out.println("Goodbye " + AuthService.getUser().getFullname() + "!");
    } else {
      System.out.println("Later!");
    }
  }

  public static void logout() {
    lastMessage();
    AuthService.logout();
  }

  public static void createAccount() {
    User user = null;
    String isArtist = InputReaderService.getString("Are you Artist or Listener? (a/l) ", Arrays.asList("a", "l"));
    String fullName = InputReaderService.getString("Enter your full name: ", null);
    String username = InputReaderService.getString("Enter your username: ", null);
    while (UserManager.getUserByUsername(username) != null) {
      System.out.println("This username is already taken.");
      username = InputReaderService.getString("Please choose another one: ", null);
    }
    String password = InputReaderService.getString("Enter your password: ", null);
    if (isArtist.equals("a")) {
      String nickname = InputReaderService.getString("Enter your nickname: ", null);
      user = UserManager.createUser(nickname, fullName, username, password);
    } else {
      user = UserManager.createUser(fullName, username, password);
    }
    AuthService.login(user);
  }

  public static void exit() {
    stop = true;
  }
}
