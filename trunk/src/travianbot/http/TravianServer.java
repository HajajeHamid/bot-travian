package travianbot.http;

import travianbot.Logger;

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
    
        rec("/login.php");
        
	String login = getInput("login");
		
        String[][] values = new String[4][2];
		
	values[0][0]="name";     values[0][1]=username;
	values[1][0]="password"; values[1][1]=password;
	values[2][0]="login";    values[2][1]=login;
	values[3][0]="w";        values[3][1]="1280:1024";
        
	recPOST("/dorf1.php", values);
        
        // TODO проверка на самом деле не работает
         
        return true;
    
    }
    
    public String getHost(){
        
        return host;
    
    }
    
    public String getUserName(){
    
        return username;
    
    }
    
    public String getPassword(){
    
        return password;
    
    }
    
    public int getPort(){
    
        return port;
    }
    
}
