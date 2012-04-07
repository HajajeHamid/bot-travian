package travianbot;

import travianbot.game.Game;
import java.io.IOException;
import java.util.ArrayList;
import travianbot.Exception.ConfigValueNotFoundException;
import travianbot.Exception.GameException;

/**
 *
 * @author Esr
 */
public class TravianBot {
    
    public static ArrayList games = new ArrayList();
    public static Config botConfig;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            Logger.info("Travian Bot!");

            String botConfigFilename = "src/config.ini";

            if(args.length>0) botConfigFilename = args[0];
            
            Logger.trace("Bot config file name is "+botConfigFilename);
        
            botConfig = new Config(botConfigFilename);
            
            int countGames = botConfig.getInt("main/countGames");
                        
            for(int i=0;i<countGames;i++){
                try {
                    Game game = new Game(botConfig.getString("main/gameName"+i));
                    Logger.info("Loaded "+game.getGameName());
                    games.add(game);
                } catch (GameException ex) {
                   Logger.error(ex.getMessage());
                }
            
            }
            
            countGames = games.size();
            Logger.info("Loaded "+countGames+" games");
            
            while(true){
            
                for(int i=0;i<countGames;i++){
                    
                    ((Game)games.get(i)).refresh();
                    
                }  
            
            }
            
            
        } catch (ConfigValueNotFoundException ex) {
            Logger.fatal(ex.getMessage());
        } catch (IOException ex) {
            
            Logger.fatal("Can't load config file: "+ex.getMessage());
            
        }catch(NullPointerException ex){
            ex.printStackTrace();
            Logger.fatal("Some untitle exception ");
                       
        }               
    }
}
