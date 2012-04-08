package travianbot.game;

import travianbot.Logger;
import travianbot.http.TravianServer;
import travianbot.parser.Build;
import travianbot.parser.Dorf1;

/**
 *
 * @author Esr
 */

public class Village {
    
    int id;
    TravianServer connection;
    public int[] resurses;
    public int cornConsumption;
    public int[] prodactions;
    public int[] units;
    public int[][][] fields;
    public int[] fieldsCount;
    public int type;
    public int time;
    public Game game;
        
      
    public Village(int id, TravianServer connection, Game game){
        
        this.connection=connection;
        this.id=id;  
        this.game=game;
        //refreshInformation(); 
        Build build = new Build(FileWorker.get_file("build"));
        buildField(1);
    
    }
    
    public String getDorf1(){
        
        //connection.changeVillage(id);
        //return connection.getHtml();
        
        return FileWorker.get_file("dorf1");
    
    }
    
    public void refreshInformation(){
                    
        Dorf1 dorf1 = new Dorf1(getDorf1());
         
        resurses = dorf1.resurses;
        cornConsumption = dorf1.cornConsumption;
        prodactions = dorf1.prodactions;
        units =dorf1.units;
        fields = dorf1.fields;
        fieldsCount = dorf1.fieldsCount;
        type = dorf1.type;
        time = dorf1.time;
        Logger.trace(null);
                
    }
    
    public void buildField(int id){
    
        connection.buildField(id);
        
    }
    
}
