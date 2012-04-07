package travianbot;

import java.io.IOException;
import travianbot.Exception.GameException;

/**
 *
 * @author Esr
 */
public class Game {
    
  //  private Connection connection;
    private Config config;
    private String gamename;
    
   Game(String gamename) throws GameException{
        try {
                this.gamename=gamename;
               config = new Config("src/"+gamename+".ini");
               /*connection =  new Connection(config.getString("host/host"),
                                            config.getInt("host/port"),
                                            config.getString("user/username"),
                                            config.getString("user/password")
                                                   );
               */
        /*} catch (ConfigValueNotFoundException ex) {
            Logger.fatal(ex.getMessage());
            throw new GameException(gamename);*/
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
