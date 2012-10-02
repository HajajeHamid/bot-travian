package com.midnightcold.travianbot.travianWorker;

import com.midnightcold.travianbot.exception.LoadHttpPageException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection extends HttpWorker{

    public Connection(String host) {
        super(host);
    }
      
    
    public void get(String path, RequestData[] requestDatas){
        
        String dataString="";
        
        if(requestDatas != null && requestDatas.length !=0 ){
            dataString="?";
            for(RequestData requestData: requestDatas)
               dataString+=requestData.name + "=" + requestData.value + "&";
            dataString = dataString.substring(0, dataString.length()-1);
        }
        try {
            makeConnect("GET", path+dataString, "");
        } catch (LoadHttpPageException ex) {
           //TODO: error
        }
        
    }
    
    public void post(String path, RequestData[] requestDatas){
        
         String dataString="";
        
        if(requestDatas != null && requestDatas.length !=0 ){
            
            for(RequestData requestData: requestDatas)
               dataString+=requestData.name + "=" + requestData.value + "&";
            dataString = dataString.substring(0, dataString.length()-1);
        }
        try {
            makeConnect("POST", path, dataString);
        } catch (LoadHttpPageException ex) {
            //TODO: error
        }
        
    }
    
}
