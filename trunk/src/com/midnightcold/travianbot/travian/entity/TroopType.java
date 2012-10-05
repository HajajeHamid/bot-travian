package com.midnightcold.travianbot.travian.entity;

public enum TroopType {
    
    test(1);
    
    private int number;
    
    private TroopType(int number){
    
        this.number = number;
        
    }
    
    public int value(){
    
        return number;
    
    }
    
}
