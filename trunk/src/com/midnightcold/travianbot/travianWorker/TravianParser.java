/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.midnightcold.travianbot.travianWorker;

import com.midnightcold.travianbot.entity.Dorf1;
import com.midnightcold.travianbot.entity.Dorf2;
import com.midnightcold.travianbot.entity.Village;
import com.midnightcold.travianbot.exception.TravianLowerErrorException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Esr
 */
public class TravianParser {
    
    public static Village[] updateAccount(String dorf1Html, String dorf2Html){
    
        Village[] v = new Village[1];
        //TODO: Вот тут заменить
        try {
            v[0] = updateVillage(dorf1Html, dorf2Html);
        } catch (TravianLowerErrorException ex) {
            Logger.getLogger(TravianParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return v;
        
    }
    
    //TODO: Придумать еще как жагружать нужные данные в акк    
    public static Village updateVillage(String dorf1Html, String dorf2Html) throws TravianLowerErrorException{
        
        Village v = new Village();
        
        Dorf1 dorf1 = Dorf1Parser.parse(dorf1Html);
        Dorf2 dorf2 = Dorf2Parser.parse(dorf2Html);
        
        v.buildings = dorf2.buildings;
        v.fields = dorf1.fields;
        v.productions = dorf1.productions;
        v.resourses = dorf1.resourses;
        v.time = dorf1.time;
        v.troops = dorf1.troops;
        
        v.buildings = dorf2.buildings;
                
        return v;
    
    }
    
}
