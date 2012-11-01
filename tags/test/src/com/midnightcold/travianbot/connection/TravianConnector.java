package com.midnightcold.travianbot.connection;

import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;

public class TravianConnector extends Connection{
    
    private String username;
    private String password;
    
    public TravianConnector(String server, String username, String password) throws TravianLowerErrorException{
     
        super(server);
        
        this.username = username;
        this.password = password;
        
        login();
        
    }
    
    private boolean login() throws TravianLowerErrorException{
    
        get("/login.php",null);
        
	//String login = getInput("login");
	
        RequestData[] values = new RequestData[5];
        
        values[0] = new RequestData();
        values[0].name="name";     values[0].value=username;
        values[1] = new RequestData();
	values[1].name="password"; values[1].value=password;
        values[2] = new RequestData();
	values[2].name="login";    values[2].value="123456";
        values[3] = new RequestData();
	values[3].name="w";        values[3].value="1280:1024";
        values[4] = new RequestData();
	values[4].name="s1";        values[3].value="Login";
        
	post("/dorf1.php", values);
        
        //throw new TravianLowerErrorException("Не возможно залогинеться");
        //TODO: Сделать проверку 
        return true;
    
    }
    
    public String getDorf1Source() throws TravianLowerErrorException{
        
        get("/dorf1.php",null);
        
        return getLastRequestResult().getHtml();
    
    }
    
    public String getDorf2Source() throws TravianLowerErrorException{
        
        get("/dorf2.php",null);
        
        return getLastRequestResult().getHtml();
    
    }
    
    public void upgradeField(int id){
    
    }
    
    public void upgradeBuilding(int id){
    
    }
    
    public void buildBuilding(int buildId, int place){
    
    }
    
    public void sendArmy(){
    
    }

    public String changeVillage(int id) throws TravianLowerErrorException {
        
        RequestData[] requestDatas = new RequestData[1];
        requestDatas[0] = new RequestData("newdid", id+"");
            
        get("dorf1.php",requestDatas);
        return getLastRequestResult().getHtml();
        
    }
    
}
