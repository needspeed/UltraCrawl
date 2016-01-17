package org.broadcaststorm;

/**
 * Created by needspeed on 1/16/16.
 */
public class DownloadLink {
    public String url;
    public String portal;
    public DownloadLink(String url, String portal){
        this.url=url;
        this.portal=portal;
    }

    public DownloadLink(String url) {
        this.url = url;
        this.portal = url.replaceFirst("http://","").split("/")[0];
    }
}
