package org.broadcaststorm.page;

import org.broadcaststorm.DownloadLink;
import org.broadcaststorm.Song;
import org.broadcaststorm.SongContainer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by needspeed on 1/16/16.
 */
public class Editionpage extends Datapage{

    public Editionpage(Document website) {
        super(website);
    }
    @Override
    public List<Song> getSongs() {
        return new ArrayList<>();

        /*
        Elements infodivs = website.getElementsByAttributeValue("style", "width:100%px;overflow:auto;");
        Element infodiv = null;
        for (Element tempinfodiv : infodivs) {
            if (tempinfodiv.tagName() == "div") {
                infodiv = tempinfodiv;
            }
        }
        String titlesum = infodiv.text();
        String[] titles=titlesum.split("\n");
        String[][] info=new String[titles.length][];
        for(int i=0;i<titles.length;i++) {
            info[i] = titles[i].split(" - ");
        }
        Elements links= website.getElementsByAttributeValue("target", "_blank");
        ArrayList<String> linkstrings = new ArrayList<String>();
        for (Element link : links) {
            if (link.tagName() == "a") {
                linkstrings.add(link.absUrl("href"));
            }
        }
        List<DownloadLink> downloadLinks= new ArrayList<DownloadLink>();
        for(int i=0;i<linkstrings.size();i++){
            String stringurl=linkstrings.get(i).replace("http://dontknow.me/at/?","");
            String host="";
            try {
                URL url= new URL(stringurl);
                host= url.getHost();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            DownloadLink dlink=new DownloadLink(stringurl,host);
            downloadLinks.add(dlink);
        }
        List<Song> songList= new ArrayList<Song>();
        for(int i=0;i<info.length;i++){
            String artist=info[i][0];
            String title=info[i][0];
            Song song = new Song(SongContainer.EDITION,downloadLinks,title,artist,null);
            songList.add(song);
        }


        return songList;*/
    }
}
