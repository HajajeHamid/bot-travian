package com.midnightcold.travianbot.travian.entity;


public enum ResourceType {
    
    Lumber(0),Clay(1),Iron(2),Crop(3);
    
    private int number;
    
    private ResourceType(int number){
    
        this.number = number;
        
    }
    
    public int value(){
    
        return number;
    
    }
    
    
    //TODO: :E
    public static ResourceType valueOf(int n){
    
        switch(n){
        
            case 0: return Lumber;
            case 1: return Clay;
            case 2: return Iron;
            case 3: return Crop; 
            
            default:
                return null;
        }
    
    }
    
}
