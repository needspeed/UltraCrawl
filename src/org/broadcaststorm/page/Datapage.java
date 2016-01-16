package org.broadcaststorm.page;

import org.broadcaststorm.Song;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by needspeed on 1/16/16.
 */
public abstract class Datapage {

    Document website;

    public Datapage(Document website) {
        this.website = website;
    }

    abstract List<Song> getSongs();
}
