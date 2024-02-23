package Services;

import java.util.ArrayList;
import java.util.List;

import Entities.Listener;
import Entities.Song;
import Entities.SongPlayer;

public class SongPlayerService {
  static int songPosition = 0;
  static List<Song> upcomingSongs = new ArrayList<>();
  static List<Song> songsToPlay = new ArrayList<>();
  static Listener listener;
  static SongPlayer player = new SongPlayer();

  public static void init(List<Song> songs) {
    songsToPlay = songs;
    upcomingSongs = songs;
    listener = (Listener) AuthService.getUser();
  }
  
  public static void listenToSong(Song song) {
    player.play(song.getFile());
    upcomingSongs.remove(song);
    song.addPlayedBy(listener);
    // TODO: add real play time
    song.getArtist().increasePlayTime(song.getDuration());
    listener.increasePlayTime(song.getDuration());
  }

  public static void next() {
    songPosition++;
  }

  public static void previous() {
    songPosition--;
  }

  public static void stop() {
    songPosition = 0;
    songsToPlay = new ArrayList<>();
  }

}
