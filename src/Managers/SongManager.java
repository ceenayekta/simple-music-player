package Managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Entities.Artist;
import Entities.Playlist;
import Entities.Song;
import Enums.Category;

public class SongManager {
  private static List<Song> songs = new ArrayList<>();

  public static List<Song> getAllSongs() {
    return songs;
  }

  public static void addSong(Song song) {
    songs.add(song);
  }

  public static void removeSong(Song song) {
    songs.remove(song);
  }

  public static Song createSong(String name, Playlist album, Artist artist, Category category, File file) {
    Song newSong = new Song(name, album, artist, file, category);
    addSong(newSong);
    return newSong;
  }

  public static void removeSongById(int songId) {
    Song song = getSongById(songId);
    removeSong(song);
  }

  public static Song getSongById(int songId) {
    Optional<Song> song = songs.stream().filter(s -> s.getId() == songId).findFirst();
    return song.orElse(null);
  }

  public static List<Song> getActiveSongs() {
    List<Song> filteredSongs = songs.stream().filter(s -> s.getAlbum() != null && s.getAlbum().isPublic() && s.getAlbum().isPublished() && !s.isBanned()).toList();
    return filteredSongs;
  }

  public static List<Song> getActiveSongsOfArtist(int artistId) {
    List<Song> filteredSongs = getActiveSongs().stream().filter(s -> s.getArtist().getId() == artistId).toList();
    return filteredSongs;
  }

  public static List<Song> filterSongsByName(String query, boolean onlyActive) {
    List<Song> songsToSearch = onlyActive ? getActiveSongs() : songs;
    List<Song> song = songsToSearch.stream().filter(s -> s.getName().toLowerCase().contains(query.toLowerCase())).toList();
    return song;
  }

  public static List<Song> filterSongsByCategory(Category category, boolean onlyActive) {
    List<Song> songsToSearch = onlyActive ? getActiveSongs() : songs;
    List<Song> song = songsToSearch.stream().filter(s -> s.getCategory().equals(category)).toList();
    return song;
  }

  public static List<Song> sortSongsByPlayCount(boolean onlyActive) {
    List<Song> songsToSearch = onlyActive ? getActiveSongs() : songs;
    List<Song> songs = songsToSearch.stream().sorted((u1, u2) -> {
      return u1.getPlayCount().compareTo(u2.getPlayCount());
    }).toList();
    return songs;
  }

  public static void generateData() {
    // addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    // addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    // addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
