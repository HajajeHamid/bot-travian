/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.midnightcold.travianbot.travianWorker;

import com.midnightcold.travianbot.entity.Dorf2;
import com.midnightcold.travianbot.entity.travian.Building;
import java.util.ArrayList;
import java.util.List;
import tools.RegexpUtils;

/**
 *
 * @author Esr
 */
public class Dorf2Parser {

    static Dorf2 parse(String dorf2Html) {

        Dorf2 dorf2 = new Dorf2();

        dorf2.buildings = parseBuildings(dorf2Html);

        return dorf2;

    }

    private static Building[] parseBuildings(String html) {

        Building[] buildings;
        
        int[] buildingTypes = new int[21];
        
        List<List<String>> buildTypesRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/class=\"building (.+?)\" title=/", html, buildTypesRegx);
        for (int i = 0; i < 21; i++) {
            if (buildTypesRegx.get(i).get(1).equals("iso")) {
                buildingTypes[i] = -1;
            } else {
                buildingTypes[i] = Integer.parseInt(buildTypesRegx.get(i).get(1).substring(1));
            }
        }
        List<List<String>> levelDivRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/<div id=\"levels\">(.+?)div><\\/div>/", html, levelDivRegx);
        List<List<String>> levelRegx = new ArrayList<List<String>>();
        RegexpUtils.preg_match_all("/\"aid(.+?)\">(.+?)<\\//", levelDivRegx.get(0).get(0), levelRegx);
        int j = 0;
        List<Building> buildingsList = new ArrayList<Building>();
        
        for (int i = 0; i < 21; i++) {
            if (buildingTypes[i] != -1) {
                Building building = new Building();
                List<String> buildingInfo = levelRegx.get(j++);

                int level = Integer.parseInt(buildingInfo.get(2));
               building.level=level;
               building.type= buildingTypes[i];
                      
                                
                buildingsList.add(building);
            }

        }
        //TODO: Посмотреть функцию toArray(T[])
        Object[] buildingsObject = buildingsList.toArray();
        buildings = new Building[buildingsObject.length];
        for(int i=0;i<buildingsObject.length;i++)
            buildings[i]=(Building) buildingsObject[i];
        
        return buildings;
     
    }
}
