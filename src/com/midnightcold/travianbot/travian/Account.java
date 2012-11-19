package com.midnightcold.travianbot.travian;

import com.midnightcold.travianbot.connection.TravianConnector;
import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;
import com.midnightcold.travianbot.parser.TravianParser;
import com.midnightcold.travianbot.parser.entity.MainAccount;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Account {
    
    private TravianConnector connetion;
    private Village[] villages = new Village[0];
    private int gold;
    private int silver;
    private String hostname;
    private String username;
    private String password;
    
    public Account(String hostname, String username, String password){
        try {
            this.hostname = hostname;
            this.username = username;
            this.password = password;
            
            connetion = new TravianConnector(hostname, username, password);
            updateAccount();

        } catch (TravianLowerErrorException ex) {            
          System.out.println(ex);
        }
               
    
    }

    private void updateAccount() {
        try {
            String dorf1 = connetion.getDorf1Source();
            String dorf2;
            
            MainAccount account = TravianParser.updateAccount(dorf1);
            
            this.gold = account.gold;
            this.silver = account.silver;
            
            int[] villagesId = account.villages;
             
            villages = new Village[villagesId.length];
           
            for(int i=0;i<villagesId.length;i++){
                dorf1 = connetion.changeVillage(villagesId[i]);
                dorf2 = connetion.getDorf2Source();
                villages[i] = TravianParser.updateVillage(dorf1, dorf2);
            }
            
        System.out.println(this.toString());
            
        } catch (TravianLowerErrorException ex) {
          //TODO: передовать на уровень выше
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    @Override
    public String toString(){
        
        String result = "";
        
        result += "Аккаун "+username+" на сервере "+hostname+"";
        result += "имеет "+gold+" золота и "+silver+" серебра\n";
        result += "У игрока "+villages.length+" деревень\n";
        for(int i=0;i<villages.length;i++){
            //result += "Деревня "+villages[i].+"\n";
            result += villages[i].toString() + "\n";
        }
                
        return result;
    
    }
    
}
