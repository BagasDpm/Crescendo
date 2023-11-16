package com.example.crescendo.classmodel;

public class Artist {
    private String artistName;
    private int albumImage;

    public Artist(String artistName, int albumImage) {
        this.artistName = artistName;
        this.albumImage = albumImage;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(int albumImage) {
        this.albumImage = albumImage;
    }
}
