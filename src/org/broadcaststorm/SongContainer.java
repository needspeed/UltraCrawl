package org.broadcaststorm;

/**
 * Created by needspeed on 1/16/16.
 */
public enum SongContainer {
    SONG(4,"songliste"),
    PACK(3,"songpackliste"),
    EDITION(2,"editionliste");

    private String webname;
    private int catnum;
    SongContainer(int catnum, String webname) {
        this.catnum = catnum;
        this.webname = webname;
    }

    public int getCatnum() {return catnum;}
    public String getWebname() {return webname;}
}
