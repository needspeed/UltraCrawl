package org.broadcaststorm;

import java.util.List;

/**
 * Created by needspeed on 1/16/16.
 */
public class Song {
    SongContainer container;
    List<DownloadLink> links;
    String artist, title, genre;


    public Song(SongContainer container, List<DownloadLink> links, String title, String artist, String genre) {
        this.container = container;
        this.links = links;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }
}
