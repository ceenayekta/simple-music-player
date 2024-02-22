package Pages;

import java.util.Arrays;
import java.util.List;


public class ArtistUI {

  public static List<String> getOptions() {
    List<String> options = Arrays.asList(
      "1. Nothing"
    );
    return options;
  }
  
  public static List<Runnable> getActions() {
    List<Runnable> actions = Arrays.asList(
      () -> {}
    );
    return actions;
  }

  
}