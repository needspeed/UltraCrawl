package org.broadcaststorm.page;

import org.broadcaststorm.DownloadLink;
import org.broadcaststorm.Song;
import org.broadcaststorm.SongContainer;
import org.broadcaststorm.Webtools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by needspeed on 1/16/16.
 */
public class Songpage extends Datapage{
    public Songpage(Document website) {
        super(website);
    }

    @Override
    public List<Song> getSongs() {
        if (website == null) throw new IllegalStateException();

        String[] title_str = website.getElementsByClass("title").first().ownText().split(" - ");
        if (title_str.length < 2) {
            return new ArrayList<>();
        }
        String title = title_str[0];
        String artist = title_str[1];

        //System.out.println("Found song: " + artist + " --- " + title);

        Element table = website.getElementsByClass("table_back").first();
        Element body = table.getElementsByTag("tbody").get(1);

        Map<String, String> table_map = Webtools.TwoDTableToMap(body);

        String genre = table_map.get("Kategorie:");
        List<DownloadLink> links = Webtools.getDownloadLinksFromMap(table_map);

        return new ArrayList<Song>(Collections.singletonList(new Song(SongContainer.SONG, links, title, artist, genre)));
    }
}
