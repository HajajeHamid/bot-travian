package com.midnightcold.travianbot.parser;

import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;
import com.midnightcold.travianbot.parser.entity.MainAccount;
import com.midnightcold.travianbot.parser.pages.Dorf1;
import com.midnightcold.travianbot.parser.pages.Dorf2;
import com.midnightcold.travianbot.system.Logger;
import com.midnightcold.travianbot.travian.Village;
import java.util.logging.Level;


public class TravianParser {
    
    public static MainAccount updateAccount(String dorf1Html) throws TravianLowerErrorException{
            
        MainAccount account = new MainAccount();
        
        Dorf1 dorf1 = Dorf1Parser.parse(dorf1Html);
        
        account.gold=dorf1.gold;
        account.silver=dorf1.silver;
        account.villages=dorf1.vilagesId;
        
        return account;
                
    }
    
    //TODO: Придумать еще как жагружать нужные данные в акк    
    public static Village updateVillage(String dorf1Html, String dorf2Html) throws TravianLowerErrorException{
        
        Village v = new Village();
        
        Dorf1 dorf1 = Dorf1Parser.parse(dorf1Html);
        Dorf2 dorf2 = Dorf2Parser.parse(dorf2Html);
        
        v.buildings = dorf2.buildings;
        v.fields = dorf1.fields;
        v.productions = dorf1.productions;
        v.resources = dorf1.resourses;
        v.time = dorf1.time;
        v.troops = dorf1.troops;
        
        v.buildings = dorf2.buildings;
                
        return v;
    
    }
    
}
