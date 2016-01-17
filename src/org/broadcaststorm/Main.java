package org.broadcaststorm;

import org.broadcaststorm.page.Datapage;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Song> songs = new ArrayList<>();
        if (args.length == 0) {
            String baselink = "http://ultrastar-base.com/index.php";
            String section_selector = "section";

            Map<SongContainer, List<String>> songs_links = new HashMap<>();
            for (SongContainer container : SongContainer.values()) {
                String section = container.getWebname();
                Map<String, String> params = new HashMap<>();
                params.put(section_selector, section);
                String page_link = Webtools.UrlBuilder(baselink, params);
                Document page = null;

                System.out.println("Starting section:" + section + " page download");
                try {
                    page = Jsoup.connect(page_link).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Counting Pages");
                //Get Pagecount ----------------------------------------------------
                Element table = getTable(page);
                Element table_head = table.getElementsByClass("big").first();
                Elements links = table_head.getElementsByTag("a");

                List<Integer> pages = new ArrayList<>();
                //Find indexes
                for (Element e : links) {
                    String link = e.attr("href");
                    String label = e.ownText();

                    int pageno = Webtools.parseInt(label);
                    if (pageno >= 0 && link.contains("Anfangsposition=") && link.contains(section_selector + "=" + section)) {
                        Integer pos = Integer.parseInt(link.split("Anfangsposition=")[1]);
                        pages.add(pos);
                    }
                }

                //DEBUG
                //if (pages.size() > 5)
                //    pages = pages.subList(0, 5);

                System.out.println("Start getting links");
                //Get Songlinks ---------------------------------------------------
                List<String> song_links_section = new ArrayList<>();
                for (Integer i : pages) {

                    Map<String, String> next_page_params = new HashMap<>();

                    next_page_params.put(section_selector, section);
                    next_page_params.put("cat", "" + container.getCatnum());
                    next_page_params.put("order", "");
                    next_page_params.put("Anfangsposition", i + "");

                    Document next_page = null;
                    try {
                        next_page = Jsoup.connect(Webtools.UrlBuilder(baselink, next_page_params)).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Element next_table = getTable(next_page);

                    //Find song links
                    Element song_table = next_table.getElementsByTag("tbody").first();
                    Elements song_trs = song_table.children();

                    for (Element song_tr : song_trs) {
                        Elements song_tds = song_tr.children();
                        String name = song_tds.get(0).ownText(); //useless
                        String song_link = song_tds.get(1).ownText();
                        song_links_section.add(song_link);
                    }
                }
                songs_links.put(container, song_links_section);
            }

            System.out.println("Start downloading detail pages");
            //Build Songs -----------------------------------------------------
            for (SongContainer container : songs_links.keySet()) {
                songs_links.get(container).parallelStream()
                        .map(Jsoup::connect).map(Main::getDocument).filter(x -> x != null)
                        .map((Document x) -> Datapage.create(container, x))
                        .map(Datapage::getSongs)
                        .forEach(songs::addAll);
            }

            System.out.println("Serializing Results");
            Serializer.serialize(songs, "prequeried");

            List<Song> cleaned = Query.firstQuery(songs);
            Serializer.serialize(cleaned, "cleaned");
        }
        else if (args.length == 1) {
            songs = Serializer.deserialize("prequeried");
            List<Song> cleaned = Query.firstQuery(songs);

            Serializer.serialize(cleaned, "cleaned");
        }
        else {
            System.out.println("Deserializing Songs");
            songs = Serializer.deserialize("cleaned");

            if (songs == null) return;
            System.out.println("Query output: ----------------------");
            Query.secondQuery(songs);
        }
    }

    private static Element getTable(Document root) {
        return root.getElementsByClass("table_back").first();
    }

    private static Document getDocument(Connection c) {
        try {
            return c.get();
        }
        catch (IOException e) {
            return null;
        }
    }
}
