package Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Abstracts.DetailedUser;
import Entities.Playlist;
import Entities.Song;

public class PlaylistManager {
  private static List<Playlist> playlists = new ArrayList<>();
  
  public static List<Playlist> getAllPlaylists() {
    return playlists;
  }

  public static void addPlaylist(Playlist playlist) {
    playlists.add(playlist);
  }

  public static void removePlaylist(Playlist playlist) {
    playlists.remove(playlist);
  }

  public static Playlist createPlaylist(String name, String description, DetailedUser owner, List<Song> songs) {
    Playlist newPlaylist = new Playlist(name, description, owner, songs);
    addPlaylist(newPlaylist);
    return newPlaylist;
  }

  public static void removePlaylistById(int playlistId) {
    Playlist playlist = getPlaylistById(playlistId);
    removePlaylist(playlist);
  }

  public static Playlist getPlaylistById(int playlistId) {
    Optional<Playlist> playlist = playlists.stream().filter(p -> p.getId() == playlistId).findFirst();
    return playlist.orElse(null);
  }

  public static void generateData() {
    // addUser(new Admin("Ceena Yekta", "ceen", "ceen123"));
    // addUser(new Artist("Sebastian Bach", "Bach.S", "bach#1", "bach", "The Great Bach"));
    // addUser(new Listener("John Doe", "John", "doe12", "John Who?"));
  }
}
