package com.midnightcold.travianbot.travianWorker;

public class TravianConnector extends Connection{
    
    private String username;
    private String password;
    
    public TravianConnector(String server, String username, String password){
     
        super(server);
        
        this.username = username;
        this.password = password;
        
        login();
        
    }
    
    private boolean login(){
    
        get("/login.php",null);
        
	//String login = getInput("login");
	
        RequestData[] values = new RequestData[4];
        
        values[0] = new RequestData();
        values[0].name="name";     values[0].value=username;
        values[1] = new RequestData();
	values[1].name="password"; values[1].value=password;
        values[2] = new RequestData();
	values[2].name="login";    //values[2].value=login;
        values[3] = new RequestData();
	values[3].name="w";        values[3].value="1280:1024";
        
        
	post("/dorf1.php", values);
        
        // TODO проверка на самом деле не работает
         
        return true;
    
    }
    
    public String getDorf1Source(){
        
        return null;
    
    }
    
    public String getDorf2Source(){
        
        return null;
    
    }
    
    public void upgradeField(int id){
    
    }
    
    public void upgradeBuilding(int id){
    
    }
    
    public void buildBuilding(int buildId, int place){
    
    }
    
    public void sendArmy(){
    
    }
    
}
