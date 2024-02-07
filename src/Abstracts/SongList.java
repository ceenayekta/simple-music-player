package Abstracts;
import java.util.ArrayList;
import java.util.List;
import Entities.Song;

public abstract class SongList extends CommonProperties {
  static List<Song> songs = new ArrayList<>();

  public static float getTotalLength() {
    float total = 0;
    for (Song song : songs) {
      total += song.getLength();
    }
    return total;
  }
}
