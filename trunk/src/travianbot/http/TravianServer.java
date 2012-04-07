package travianbot.http;

import travianbot.http.Connection;

/**
 *
 * @author Esr
 */
public class TravianServer extends Connection{
    
    private String username;
    private String password;
    
    public TravianServer(String host, int port, String username, String password){
        super(host,port);
        
        this.username=username;
        this.password=password;
        
        
    
    }
    
    public boolean login(){
    
         return false;
    
    }
    
}
