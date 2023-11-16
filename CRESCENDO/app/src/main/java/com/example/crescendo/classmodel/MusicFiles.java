package com.example.crescendo.classmodel;

public class MusicFiles {
    private String title;
    private String artist;
    private int musicId;
    private int albumImageId;

    public MusicFiles(String title, String artist, int musicId, int albumImageId) {
        this.title = title;
        this.artist = artist;
        this.musicId = musicId;
        this.albumImageId = albumImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getAlbumImageId() {
        return albumImageId;
    }

    public void setAlbumImageId(int albumImageId) {
        this.albumImageId = albumImageId;
    }
}
