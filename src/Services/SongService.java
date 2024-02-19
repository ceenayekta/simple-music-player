package Services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Entities.Playlist;
import Entities.Song;
import Enums.Category;
import Managers.PlaylistManager;
import Managers.SongManager;

public class SongService {

  public static List<Song> getSimilarSongsByAuthor(Song song) {
    return song.getArtist().getAllSongs();
  }
  
  public static List<Playlist> getAlbumsOfAuthor(Song song) {
    return song.getArtist().getPlaylists();
  }

  public static List<Song> filterSongsByCategory(List<Song> songs, Category category) {
    Stream<Song> filteredSongs = songs.stream().filter(s -> s.getCategory().equals(category));
    return filteredSongs.collect(Collectors.toList());
  }
  
  public static List<Song> getAllSimilarSongsByCategory(Song song) {
    List<Song> similarSongs = filterSongsByCategory(SongManager.getAllSongs(), song.getCategory());
    return similarSongs;
  }  
  
  public static boolean areHalfOfSongsSameCategory(List<Song> songs, Category category) {
    List<Song> filteredSongs = filterSongsByCategory(songs, category);
    return filteredSongs.size() > (songs.size() / 2);
  }

  public static List<Playlist> getSimilarPlaylistsByCategory(Song song) {
    Stream<Playlist> filteredPlaylists = PlaylistManager.getAllPlaylists().stream().filter(p -> areHalfOfSongsSameCategory(p.getSongs(), song.getCategory()));
    return filteredPlaylists.collect(Collectors.toList());
  }
}
