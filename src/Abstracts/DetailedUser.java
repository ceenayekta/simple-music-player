package Abstracts;

import java.util.ArrayList;
import java.util.List;

import Entities.Playlist;

public abstract class DetailedUser extends User {
  private String bio;
  private boolean isBanned = false;
  private int playTimeInSeconds = 0;
  private List<Playlist> playlists;
  private List<DetailedUser> followers;
  private List<DetailedUser> followings;
  private List<DetailedUser> blockedUsers;

  public DetailedUser(String fullname, String username, String password, String bio) {
    super(fullname, username, password);
    this.bio = bio;
    this.playlists = new ArrayList<>();
    this.followers = new ArrayList<>();
    this.followings = new ArrayList<>();
    this.blockedUsers = new ArrayList<>();
  }

  public void setBio(String bio) {
    this.bio = bio;
  }
  public String getBio() {
    return bio;
  }

  public boolean isBanned() {
    return isBanned;
  }
  public void setBanned(boolean isBanned) {
    this.isBanned = isBanned;
  }

  public Integer getPlayTimeInSeconds() {
    return playTimeInSeconds;
  }
  public void setPlayTimeInSeconds(int playTimeInSeconds) {
    this.playTimeInSeconds = playTimeInSeconds;
  }
  public void increasePlayTime(int seconds) {
    playTimeInSeconds += seconds;
  }

  public List<Playlist> getPlaylists() {
    return playlists;
  }
  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }
  public int getPlaylistsCount() {
    return this.playlists.size();
  }
  public int getSongsCount() {
    int count = 0;
    for (Playlist playlist : playlists) {
      count += playlist.getSongsCount();
    }
    return count;
  }
  public void addPlaylist(Playlist newPlaylist) {
    this.playlists.add(newPlaylist);
  }
  public void removePlaylist(Playlist targetPlaylist) {
    this.playlists.remove(targetPlaylist);
  }
  
  public List<DetailedUser> getFollowers() {
    return followers;
  }
  public void setFollowers(List<DetailedUser> followers) {
    this.followers = followers;
  }
  public int getFollowersCount() {
    return this.followers.size();
  }
  public void addFollower(DetailedUser targetUser) {
    this.followers.add(targetUser);
  }
  public void removeFollower(DetailedUser targetUser) {
    this.followers.remove(targetUser);
  }

  public List<DetailedUser> getFollowings() {
    return followings;
  }
  public void setFollowings(List<DetailedUser> followings) {
    this.followings = followings;
  }
  public int getFollowingsCount() {
    return this.followings.size();
  }
  public void addFollowing(DetailedUser targetUser) {
    this.followings.add(targetUser);
  }
  public void removeFollowing(DetailedUser targetUser) {
    this.followings.remove(targetUser);
  }

  public List<DetailedUser> getBlockedUsers() {
    return blockedUsers;
  }
  public void setBlockedUsers(List<DetailedUser> blockedUsers) {
    this.blockedUsers = blockedUsers;
  }

  public void follow(DetailedUser targetUser) {
    this.addFollowing(targetUser);
    targetUser.addFollower(this);
  }
  public void unfollow(DetailedUser targetUser) {
    this.removeFollowing(targetUser);
    targetUser.removeFollower(this);
  }
  public void block(DetailedUser targetUser) {
    this.unfollow(targetUser);
    targetUser.unfollow(this);
    this.blockedUsers.add(targetUser);
  }
  public void unblock(DetailedUser targetUser) {
    this.blockedUsers.remove(targetUser);
  }
  public boolean hasBlockedTheUser(int userId) {
    return blockedUsers.stream().anyMatch(u -> u.getId() == userId);
  }

}
