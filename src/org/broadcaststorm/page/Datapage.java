package org.broadcaststorm.page;

import org.broadcaststorm.Song;
import org.broadcaststorm.SongContainer;
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

    public abstract List<Song> getSongs();

    public static Datapage create(SongContainer container, Document document) {
        switch (container) {
            case SONG:
                return new Songpage(document);
            case PACK:
                return new Songpackpage(document);
            case EDITION:
                return new Editionpage(document);
        }
        return null;
    }
}
