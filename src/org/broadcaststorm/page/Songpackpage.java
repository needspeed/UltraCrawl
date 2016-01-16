package org.broadcaststorm.page;

import org.broadcaststorm.Song;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by needspeed on 1/16/16.
 */
public class Songpackpage extends Datapage{
    public Songpackpage(Document website) {
        super(website);
    }

    @Override
    List<Song> getSongs() {
        return null;
    }
}
