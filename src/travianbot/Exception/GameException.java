package travianbot.Exception;

/**
 *
 * @author Esr
 */
public class GameException  extends Exception{
    
    private String gamename;
    private String msg;
    
    public GameException(String gamename){
    
        this(gamename,"");
        
    }
    
    public GameException(String gamename,String msg){
    
        this.gamename=gamename;
        this.msg=msg;
        
    }
    
    public String getGameName(){
    
        return gamename;
    
    }
    
    @Override
    public String getMessage(){
    
        return "Can't create game '"+gamename+"' "+msg;
    
    }
    
    @Override
    public String toString(){
    
        return "Can't create game '"+gamename+"' "+msg;
    
    }
    
}
