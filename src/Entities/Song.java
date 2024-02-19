package Entities;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Abstracts.CommonProperties;
import Enums.Category;

public class Song extends CommonProperties {
  private String name;
  private Playlist album;
  private Artist artist;
  private File file;
  private Category category;
  private List<Listener> playedBy;

  public Song(String name, Playlist album, Artist artist, File file, Category category) {
    super();
    this.name = name;
    this.album = album;
    this.artist = artist;
    this.file = file;
    this.category = category;
    this.playedBy = new ArrayList<>();
    if (artist != null) artist.addSong(this);
    if (album != null) album.addSong(this);
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

  public Category getCategory() {
    return category;
  }
  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Listener> getPlayedBy() {
    return playedBy;
  }
  public void setPlayedBy(List<Listener> playedBy) {
    this.playedBy = playedBy;
  }
  public int getPlayCount() {
    return playedBy.size();
  }

  public LocalDateTime getPublishedAt() {
    return album.getPublishedAt();
  }
}