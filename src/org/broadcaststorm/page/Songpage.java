package org.broadcaststorm.page;

import org.broadcaststorm.Song;
import org.jsoup.nodes.Document;

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
        return null;
    }
}
