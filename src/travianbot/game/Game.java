package travianbot.game;

import travianbot.http.TravianServer;
import java.io.IOException;
import travianbot.Config;
import travianbot.Exception.ConfigValueNotFoundException;
import travianbot.Exception.GameException;
import travianbot.Logger;

/**
 *
 * @author Esr
 */
public class Game {
    
    private TravianServer connection;
    private Config config;
    private String gamename;
    
   public Game(String gamename) throws GameException{
       
       try {
           
            this.gamename=gamename;
            config = new Config("src/"+gamename+".ini");


            connection =  new TravianServer(config.getString("host/host"),
                                        config.getInt("host/port"),
                                        config.getString("user/username"),
                                        config.getString("user/password")
                                                );
            
            if(!connection.login()) throw new GameException(gamename,"Can't login");

        
        } catch (ConfigValueNotFoundException ex) {
            Logger.fatal(ex.getMessage());
            throw new GameException(gamename);
        } catch (IOException ex) {
            
            Logger.fatal("Can't load config file: "+ex.getMessage());
            throw new GameException(gamename);
        }catch(NullPointerException ex){
            ex.printStackTrace();
            Logger.fatal("Some untitle exception ");
             throw new GameException(gamename);          
        }
       
    
    }   
   
   
    
   public void refresh(){
   
       
   
   }
   
   public String getGameName(){
   
       return gamename;
   
   }
    
}
