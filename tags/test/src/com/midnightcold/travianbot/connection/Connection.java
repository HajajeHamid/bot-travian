package com.midnightcold.travianbot.connection;

import com.midnightcold.travianbot.exceptions.LoadHttpPageException;
import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;


public class Connection extends HttpWorker{

    public Connection(String host) {
        super(host);
    }
      
    
    public void get(String path, RequestData[] requestDatas) throws TravianLowerErrorException{
        
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
           throw new TravianLowerErrorException(ex.toString());
        }
        
    }
    
    public void post(String path, RequestData[] requestDatas) throws TravianLowerErrorException{
        
         String dataString="";
        
        if(requestDatas != null && requestDatas.length !=0 ){
            
            for(RequestData requestData: requestDatas)
               dataString+=requestData.name + "=" + requestData.value + "&";
            dataString = dataString.substring(0, dataString.length()-1);
        }
        try {
            makeConnect("POST", path, dataString);
        } catch (LoadHttpPageException ex) {
            throw new TravianLowerErrorException(ex.toString());
        }
        
    }
    
}
