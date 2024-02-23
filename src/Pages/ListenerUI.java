package Pages;

import java.util.Arrays;
import java.util.List;

import Entities.Song;
import Services.OutputService;
import Services.SongPlayerService;

public class ListenerUI {
  static boolean stopInnerMenu = false;

  public static List<String> getOptions() {
    stopInnerMenu = false;
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
  
  public static void listenToPlaylist(List<Song> songs) {
    SongPlayerService.stop();
    SongPlayerService.init(songs);
    for (Song song : songs) {
      OutputService.printSong(song);
      SongPlayerService.listenToSong(song);
      
    }
    
  }

}
