package com.midnightcold.travianbot.parser;

import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;
import com.midnightcold.travianbot.parser.pages.Dorf1;
import com.midnightcold.travianbot.system.Logger;
import com.midnightcold.travianbot.travian.entity.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import tools.RegexpUtils;

public class Dorf1Parser {

    static Dorf1 parse(String dorf1Html) throws TravianLowerErrorException {

        Dorf1 dorf1 = new Dorf1();

        dorf1.fields = parseFields(dorf1Html);
        dorf1.gold = parseGold(dorf1Html);
        dorf1.productions = parseProduction(dorf1Html);
        dorf1.resourses = parseResource(dorf1Html);
        dorf1.silver = parseSilver(dorf1Html);
        dorf1.time = parseTime(dorf1Html);
        //dorf1.troops = parseTroops(dorf1Html);
        dorf1.vilagesId = parseVilagesId(dorf1Html);
        dorf1.freeCrop = parseFreeCrop(dorf1Html);

        return dorf1;

    }

    private static Field[] parseFields(String html) throws TravianLowerErrorException {

        //Определяем тип деревни

        List<List<String>> regxType = new ArrayList<List<String>>();
        
        RegexpUtils.preg_match_all("/<div id=\"village_map\" class=\"f[0-9]{1}\">/", html, regxType);

        String stringVillageType = regxType.get(0).get(0);

        int typeVillage = stringVillageType.charAt(stringVillageType.length() - 3) - '0';

        //Загружаем, где какие ресуры находятся

        BufferedReader file; 
        try {//TODO: Сдедать нормальный путь
            file = new BufferedReader(new FileReader("C:\\http\\TravianBot\\src\\com\\midnightcold\\travianbot\\parser\\typeVillages")); //Загрузить файл
        } catch (FileNotFoundException ex) {
            //TODO: Сделать нормальную ошибку
           System.err.println(ex);
            return null;
        }

        String fieldsOrder = null;

        for (int i = 0; i < typeVillage; i++) {
            try {
                fieldsOrder = file.readLine();
            } catch (IOException e) {
                throw new TravianLowerErrorException();
            }
        }

        Field[] fields = new Field[18];

        for (int i = 0; i < 18; i++) {

            int typeFild = (fieldsOrder.charAt(i) - '0' - 1);
            fields[i] = new Field();
            fields[i].type = ResourceType.valueOf(typeFild);

        }

        List<List<String>> resourceRegx = new ArrayList<List<String>>();

        RegexpUtils.preg_match_all("/<area href=\"build[^<]{1,}/", html, resourceRegx);

        for (int i = 0; i < resourceRegx.size(); i++) {

            String levelString = resourceRegx.get(i).get(0).trim();
            int level = levelString.charAt(levelString.length() - 4) - '0';
            fields[i].level = level;

        }

        return fields;

    }

    private static int parseGold(String html) {

        List<List<String>> goldRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/class=\"gold\" \\/><br \\/>(.+?)</", html, goldRegx);

        int gold = Integer.parseInt(goldRegx.get(0).get(1));

        return gold;

    }

    private static Resource[] parseResource(String html) {

        List<List<String>> resourceRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<span id=\"l[0-9]\" class=\"value \">(.+?)/[0-9]{1,}<\\/span>/", html, resourceRegx);

        Resource[] resurses = new Resource[4];

        for (int i = 0; i < 4; i++) {
            resurses[i] = new Resource();
            resurses[i].type = ResourceType.valueOf(i);
            resurses[i].amount = Integer.parseInt(resourceRegx.get(i).get(1));
        }

        return resurses;


    }

    private static int parseFreeCrop(String html) {

        List<List<String>> freeCropRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<span id=\"l5\" class=\"value \">(.+?)<\\/span>/", html, freeCropRegx);

        int freeCrop = Integer.parseInt(freeCropRegx.get(0).get(1));

        return freeCrop;

    }

    private static Production[] parseProduction(String html) {

        List<List<String>> productionTableRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<table id=\"production\"(.+?)<\\/table>/", html, productionTableRegx);

        String productionTable = productionTableRegx.get(0).get(0);
        List<List<String>> productionRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<td class=\"num\">(.+?)<\\/td>/", productionTable, productionRegx);

        Production[] prodactions = new Production[4];
        for (int i = 0; i < 4; i++) {
            
            prodactions[i] = new Production();
            prodactions[i].production = Integer.parseInt(productionRegx.get(i).get(1).trim());
            prodactions[i].type = ResourceType.valueOf(i);

        }

        return prodactions;


    }

    private static int parseSilver(String html) {

        List<List<String>> silverRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/class=\"silver\" \\/><br \\/>(.+?)</", html, silverRegx);

        int silver = Integer.parseInt(silverRegx.get(0).get(1));

        return silver;

    }

    private static int parseTime(String html) {

        List<List<String>> timeRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<span id=\"tp1\">(.+?)<\\/span>/", html, timeRegx);

        String[] timeString = timeRegx.get(0).get(1).split(":");
        int time = Integer.parseInt(timeString[0]) * 3600 + Integer.parseInt(timeString[1]) * 60 + Integer.parseInt(timeString[2]);

        return time;

    }
//TODO: Парсинг войск
    private static Troop parseTroops(String html) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static int[] parseVilagesId(String html) {

        List<List<String>> vilagesidRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/href=\"\\?newdid=(.+?)\"/", html, vilagesidRegx);

        int[] villages = new int[vilagesidRegx.size()];

        for (int i = 0; i < vilagesidRegx.size(); i++) {

            villages[i] = Integer.parseInt(vilagesidRegx.get(i).get(1));


        }
        
        return villages;

    }
    
    
}
