package travianbot.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import travianbot.http.TravianServer;
import java.io.IOException;
import travianbot.Config;
import travianbot.Exception.ConfigValueNotFoundException;
import travianbot.Exception.GameException;
import travianbot.Logger;
import travianbot.parser.Dorf1;

/**
 *
 * @author Esr
 */
public class Game {
    
    private TravianServer connection;
    private Config config;
    private String gamename;
    private int[] villages;
    
    private BufferedReader getBReader(String fileName){
		FileReader fReader;
		try {
			fReader = new FileReader(Game.class.getResource(fileName).getFile());
			return (new BufferedReader(fReader));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	String get_file(String name){
		
		BufferedReader file = getBReader(name);
		String content="",line="";
		
		try {
			while((line=file.readLine())!= null)
				content+=line;
		} catch (IOException e) {
			
			//e.printStackTrace();
		}
		return content;
		
	}

    
   public Game(String gamename) throws GameException{
       
       //try {
           
           /* this.gamename=gamename;
            config = new Config("src/"+gamename+".ini");
            Logger.trace("Загружаем конфиг для игры "+gamename+" из "+"src/"+gamename+".ini");

            connection =  new TravianServer(config.getString("host/host"),
                                        config.getInt("host/port"),
                                        config.getString("user/username"),
                                        config.getString("user/password")
                                             );
            
            if(!connection.login()) throw new GameException(gamename,"Can't login");
            
            Logger.info("Залогинелись к http://"+ connection.getHost()+":"+ connection.getPort()+"/ Логин: "+ connection.getUserName()+" пароль "+ connection.getPassword());
*/
           Dorf1 dorf1 = new Dorf1(get_file("dorf1"));
           villages=dorf1.villages;
         
       /* 
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
       */
    
    }   
   
   
    
   public void refresh(){
   
       
   
   }
   
   public String getGameName(){
   
       return gamename;
   
   }
    
}
