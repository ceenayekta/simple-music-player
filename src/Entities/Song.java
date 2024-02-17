package Entities;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Abstracts.CommonProperties;
import Abstracts.User;

public class Song extends CommonProperties {
  private String name;
  private Playlist album;
  private Artist artist;
  private File file;
  private List<User> playedBy;

  public Song(String name, Playlist album, Artist artist, File file) {
    super();
    this.name = name;
    this.album = album;
    this.artist = artist;
    this.file = file;
    this.playedBy = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  public Playlist getAlbum() {
    return album;
  }

  protected void setAlbum(Playlist album) {
    this.album = album;
  }

  public Artist getArtist() {
    return artist;
  }

  protected void setArtist(Artist artist) {
    this.artist = artist;
  }

  public File getFile() {
    return file;
  }
  public void setFile(File file) {
    this.file = file;
  }
  public float getSize() {
    return this.file.length();
  }
  public int getLength() {
    // return AudioFileIO.read(this.file).getAudioHeader().getTrackLength();
    return 0;
  }
  public String getPath() {
    return this.file.getPath();
  }

  public List<User> getPlayedBy() {
    return playedBy;
  }
  public void setPlayedBy(List<User> playedBy) {
    this.playedBy = playedBy;
  }
  public int getPlayCount() {
    return playedBy.size();
  }

  public LocalDateTime getPublishedAt() {
    return album.getPublishedAt();
  }
}
