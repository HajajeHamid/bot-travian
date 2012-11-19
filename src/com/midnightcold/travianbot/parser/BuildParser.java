package com.midnightcold.travianbot.parser;

import java.util.ArrayList;
import java.util.List;
import tools.RegexpUtils;

public class BuildParser {

    public static String upgradeFieldLink(String html) {

        List<List<String>> linkRegx = new ArrayList<List<String>>();

        RegexpUtils.preg_match_all("/class=\"build\" onclick=\"window\\.location\\.href = '(.+?)'; return false;\">/", html, linkRegx);

        String link = linkRegx.get(0).get(1);

        return link.replaceAll("&amp;", "&");


    }

    public static int[] upgradeFieldResourse(String html) {
        List<List<String>> divRegx = new ArrayList<List<String>>();

        RegexpUtils.preg_match_all("/<div class=\"showCosts\">(.+?)<\\/div>/", html, divRegx);

        String div = divRegx.get(0).get(1);
        
        List<List<String>> rRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<img class=\"r[0-9]{1}\" src=\"img\\/x\\.gif\" alt=\"[A-Za-z]{1,}\" \\/>(.+?)<\\/span>/", div, rRegx);
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = Integer.parseInt(rRegx.get(i).get(1));
        }
        return res;
    }
}
