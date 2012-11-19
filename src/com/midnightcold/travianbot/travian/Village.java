package com.midnightcold.travianbot.travian;

import com.midnightcold.travianbot.travian.entity.*;

public class Village {
    
    public Building[] buildings;
    public Field[] fields;
    public int time;
    public Resource[] resources;
    public Production[] productions;
    public Troop troops;
    
    
    
    @Override
    public String toString(){
        String result = "";
        
        for(int i=0;i<buildings.length;i++){
            
            result += "Здание "+buildings[i].type+" имеет уровень "+buildings[i].level+"\n";
        
        }
        
        for(int i=0;i<fields.length;i++){
            
            result += "Поле "+fields[i].type+" имеет уровень "+fields[i].level+"\n";
        
        }
         
        result += "Время которое прошло равно "+time+"с";
        
        for(int i=0;i<resources.length;i++){
            
            result += "На складах ресурса "+resources[i].type+" лежит "+resources[i].amount+"\n";
        
        }
        
        for(int i=0;i<productions.length;i++){
            
            result += "Ресурса "+productions[i].type+" производиться "+productions[i].production+"\n";
        
        }
        
        return result;
    }
    
}
