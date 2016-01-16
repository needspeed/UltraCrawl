package org.broadcaststorm.page;

import org.broadcaststorm.Song;
import org.broadcaststorm.SongContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by needspeed on 1/16/16.
 */
public class Songpage extends Datapage{
    public Songpage(Document website) {
        super(website);
    }

    @Override
    public List<Song> getSongs() {
        String[] title_str = website.getElementsByClass("title").first().ownText().split(" - ");
        if (title_str.length != 2) return null;
        String title = title_str[0];
        String artist = title_str[1];

        System.out.println("Found song: " + artist + " --- " + title);

        return new ArrayList<Song>(Collections.singletonList(new Song(SongContainer.SONG, null, title, artist, null)));
    }
}
