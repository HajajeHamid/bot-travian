package com.midnightcold.travianbot.connection;

import com.midnightcold.travianbot.exceptions.ResourceException;
import com.midnightcold.travianbot.exceptions.TravianLowerErrorException;
import com.midnightcold.travianbot.parser.BuildParser;
import com.midnightcold.travianbot.travian.Account;
import com.midnightcold.travianbot.travian.Village;

public class TravianConnector extends Connection{
    
    private String username;
    private String password;
    private Account account;
    
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
    
    public String upgradeField(int id) throws TravianLowerErrorException, ResourceException {
        
        RequestData[] requestDatas = new RequestData[1];
        requestDatas[0] = new RequestData("id", id+"");
            
        get("/build.php",requestDatas);
        
        String html = getLastRequestResult().getHtml();
        
        int[] r = BuildParser.upgradeFieldResourse(html);
        Village v = account.getCurrectVillage();
        if(!(v.resources[0].amount>r[0] &&v.resources[1].amount>r[1] &&v.resources[2].amount>r[2] &&v.resources[3].amount>r[3])){
           throw new ResourceException(); 
        }
        
        String link = BuildParser.upgradeFieldLink(html);
        get("/"+link, null);
        return getLastRequestResult().getHtml();
        
    
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
            
        get("/dorf1.php",requestDatas);
        return getLastRequestResult().getHtml();
        
    }
    
    public void setAcount(Account account){
        this.account = account;
    }
    
}
