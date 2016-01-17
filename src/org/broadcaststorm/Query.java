package org.broadcaststorm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by needspeed on 1/17/16.
 */
public class Query {

    public static List<Song> firstQuery(List<Song> songs) {
        System.out.println("Songcount: " + songs.size());

        //Clean null songs (shouldnt be here anyway...)
        songs.removeIf(x -> x==null);

        removeNonUlLinks(songs);

        List<Song> ul_songs = getUlSongs(songs);
        System.out.println("Uploaded songs count: " + ul_songs.size());

        List<Song> online_songs = ul_songs.parallelStream().filter(Query::checkUploadedOnline).collect(Collectors.toList());
        System.out.println("Online count: " + online_songs.size());

        return online_songs;
    }

    public static void secondQuery(List<Song> songs) {
        System.out.println("Song count: " + songs.size());
        //songs.forEach(s -> System.out.println(s.artist + " - " + s.title));
        List<Song> metal = songs.stream().filter(song -> song.genre.contains("Metal")).collect(Collectors.toList());
        System.out.println("Metal Count: " + metal.size());
        //metal.forEach(s -> System.out.println(s.artist + " - " +s.title));
        List<String> urls = songs.stream().map(Query::songUrl).collect(Collectors.toList());
        List<String> metal_urls = metal.stream().map(Query::songUrl).collect(Collectors.toList());

        Path file = Paths.get("/tmp/urls.txt");
        Path metal_file = Paths.get("/tmp/metal_urls.txt");
        try {
            Files.write(file, urls, Charset.forName("UTF-8"));
            Files.write(metal_file, metal_urls, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String songUrl(Song s) {
        return s.links.get(0).url;
    }

    private static void removeNonUlLinks(List<Song> songs) {
        //DEBUG
        List<Song> nullsong = songs.stream().filter(x -> x == null).collect(Collectors.toList());
        List<Song> nulllinks = songs.stream().filter(x -> x.links == null).collect(Collectors.toList());
        List<Song> nulllink = songs.stream().filter(x -> x.links.stream().anyMatch(y -> y == null)).collect(Collectors.toList());
        List<Song> nullportal = songs.stream().filter(x -> x.links.stream().anyMatch(y -> y.portal == null)).collect(Collectors.toList());

        if (nullsong.size() > 0 || nulllinks.size() > 0 || nulllink.size() > 0 || nullportal.size() > 0)
            System.out.println("Found Null");

        songs.stream().forEach(s -> s.links.removeIf(l -> !l.portal.contains("ul.to")));
    }

    private static List<Song> getUlSongs(List<Song> songs) {
        return songs.stream().filter(s -> s.links.size() > 0).collect(Collectors.toList());
    }

    private static boolean checkUploadedOnline(Song s) {
        try {
            Document d = Jsoup.connect(s.links.get(0).url).get();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
