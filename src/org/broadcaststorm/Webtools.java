package org.broadcaststorm;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by needspeed on 1/16/16.
 */
public class Webtools {
    public static String UrlBuilder (String baseurl, Map<String, String> params) {
        boolean first = true;
        for (String k : params.keySet()) {
            baseurl += (first) ? "?" : "&";
            first = false;
            baseurl += k + "=";
            baseurl += params.get(k);
        }
        return baseurl;
    }

    public static int parseInt(String numberString) {
        try {
            return Integer.parseInt(numberString);
        }
        catch (NumberFormatException e){
            return -1;
        }
    }

    public static String getDeepestTextOrLink(Element element) {
        while (element.children().size() > 0 && !element.tagName().equals("a")) {
            element = element.child(0);
        }
        if (element.tagName().equals("a")) return element.absUrl("href");
        return element.ownText();
    }

    public static Map<String, String> TwoDTableToMap(Element table) {
        Map<String, String> map = new HashMap<>();

        Elements trs = table.children();
        for (Element tr : trs) {
            Elements tds = tr.children();

            if (tds.size() < 2) continue;
            String key = getDeepestTextOrLink(tds.get(0));
            String value = getDeepestTextOrLink(tds.get(1));
            map.put(key,value);
        }
        return map;
    }

    public static List<DownloadLink> getDownloadLinksFromMap(Map<String, String> map) {
        return map.keySet().stream()
                .filter(key -> key.startsWith("Download"))
                .map(map::get)
                .map(s -> s.replaceFirst("http://dontknow.me/at/[?]", ""))
                .map(DownloadLink::new).collect(Collectors.toList());
    }
}
