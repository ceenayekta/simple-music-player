package Entities;
import Abstracts.DetailedUser;

public class Artist extends DetailedUser {
  private String nickname;

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
