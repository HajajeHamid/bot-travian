package travianbot.Exception;

/**
 *
 * @author Esr
 */
public class GameException  extends Exception{
    
    private String gamename;
    
    public GameException(String gamename){
    
        this.gamename=gamename;
        
    }
    
    public String getGameName(){
    
        return gamename;
    
    }
    
    @Override
    public String getMessage(){
    
        return "Can't create game "+gamename;
    
    }
    
    @Override
    public String toString(){
    
        return "Can't create game "+gamename;
    
    }
    
}
