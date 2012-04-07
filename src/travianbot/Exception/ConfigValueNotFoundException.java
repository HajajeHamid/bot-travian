package travianbot.Exception;

/**
 *
 * @author Esr
 */
public class ConfigValueNotFoundException extends Exception{
    
    private String path;
    private String value;
    
    public ConfigValueNotFoundException(String path, String value){
        
        this.path=path;
        this.value=value;
    
    }
    
    public ConfigValueNotFoundException(String value){
        
        this.path="";
        this.value=value;
    
    }
    
    @Override
    public String getMessage(){
        String localpath = "";
        if(!this.path.equals("")) localpath = this.path+"/";
        return "Can't find value "+localpath+value;    
    }
    
    @Override
    public String toString(){
        String localpath = "";
        if(!this.path.equals("")) localpath = this.path+"/";
        return "Can't find value "+localpath+value;    
    }
    
    public String getValue(){
    
        return value;
        
    }
    
    public String getPath(){
    
        return path;
    
    }
    
}
