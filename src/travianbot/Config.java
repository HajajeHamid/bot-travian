package travianbot;

import java.io.File;
import java.io.IOException;
import org.ini4j.Ini;
import travianbot.Exception.ConfigValueNotFoundException;

/**
 *
 * @author Esr
 */
public class Config {
    
    private Ini configfile;
  
    
    public Config(String filename) throws IOException{
           
         configfile = new Ini(new File(filename));
         
    }    
       

    
    private String getValue(String key) throws ConfigValueNotFoundException{
            
        String[] path = key.split("/");
        
        String value = configfile.get(path[0], path[1]);
        
        if(value==null) throw new ConfigValueNotFoundException(path[0],path[1]);
        
        return value;
        
    }
    
    public int getInt(String key) throws ConfigValueNotFoundException{
        
        return Integer.parseInt(getValue(key));
    
    }
    
    public String getString(String key) throws ConfigValueNotFoundException{
    
        return getValue(key);
        
    }
    
    public boolean getBool(String key) throws ConfigValueNotFoundException{
    
        return getValue(key).equals("true") || getValue(key).equals("1");
        
    }
    
    public double getDouble(String key) throws ConfigValueNotFoundException{
    
        return Double.parseDouble(getValue(key));
        
    }

    
       
}
