import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Abstracts.User;
import Controllers.LoginController;
import Pages.AdminUI;
import Pages.ArtistUI;
import Pages.ListenerUI;
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
    List<String> options = new ArrayList<>();
    List<Runnable> actions = new ArrayList<>();

    switch (AuthService.getRole()) {
      case Admin:
        options.addAll(AdminUI.getOptions());
        actions.addAll(AdminUI.getActions());
        break;
      case Artist:
        options.addAll(ArtistUI.getOptions());
        actions.addAll(ArtistUI.getActions());
        break;
      case Listener:
        options.addAll(ListenerUI.getOptions());
        actions.addAll(ListenerUI.getActions());
        break;
      default:
        break;
    }

    options.add("" + (options.size() + 1) + ". Logout");
    options.add("" + (options.size() + 1) + ". Exit");
    actions.add(() -> logout());
    actions.add(() -> exit());
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
