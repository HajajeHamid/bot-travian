package com.midnightcold.travianbot.entity;

import com.midnightcold.travianbot.TravianBot;
import com.midnightcold.travianbot.travianWorker.Connection;
import com.midnightcold.travianbot.travianWorker.TravianConnector;

public class Account {

    TravianConnector connetion;
    
    public Account(String hostname, String username, String password) {
      
      connetion = new TravianConnector(hostname, username, password);
        
    }

    public void update() {
        
    }
    
    
    
}
