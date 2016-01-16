package org.broadcaststorm;

import java.util.Map;

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
}
