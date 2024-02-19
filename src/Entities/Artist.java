package Entities;
import java.util.List;

import Abstracts.DetailedUser;
import Enums.UserRole;

public class Artist extends DetailedUser {
  private String nickname;
  private List<Song> allSongs;

  public Artist(String fullname, String username, String password, String bio, String nickname) {
    super(fullname, username, password, bio);
    this.nickname = nickname;
  }

  @Override
  public UserRole getRole() {
    return UserRole.Artist;
  }

  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public List<Song> getAllSongs() {
    return allSongs;
  }
  public void setAllSongs(List<Song> allSongs) {
    this.allSongs = allSongs;
  }
  public void addSong(Song targetSong) {
    allSongs.add(targetSong);
  }
  public void removeSong(Song targetSong) {
    allSongs.remove(targetSong);
  }

  public Song getMostPlayedSong() {
    // Assuming that Song class has a method to get the play count
    Song mostPlayedSong = null;
    int maxPlayCount = 0;
    for (Playlist playlist : this.getPlaylists()) {
      for (Song song : playlist.getSongs()) {
        if (song.getPlayCount() > maxPlayCount) {
          maxPlayCount = song.getPlayCount();
          mostPlayedSong = song;
        }
      }
    }
    return mostPlayedSong;
  }
  
}
