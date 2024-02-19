import java.util.Arrays;
import java.util.List;

import Abstracts.User;
import Controllers.LoginController;
import Services.AuthService;
import Services.OutputService;
import Services.CommonService;

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
    CommonService.menuHandler(options, actions);
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
    CommonService.menuHandler(options, actions);
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
    User newUser = OutputService.signupWizard();
    boolean succeed = AuthService.login(newUser);
    System.out.println(succeed ? "Glad to have you in our family, " + newUser.getFullname() + "!" : "Failed to create account. Please try again later :(");
  }

  public static void exit() {
    stop = true;
  }
}
