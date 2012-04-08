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
    public long nextRefresh=0;
    public int nextBuild=-1;
    public boolean isBuild;
    
        
      
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
    
    public int findNextBuiling(){
        
        int min=9999;
        int num=1;
        int num1=0;
        for(int j=0;j<fieldsCount[1];j++)
                if(min>fields[1][j][0]){min=fields[1][j][0];num=1;num1=j;}
        for(int j=0;j<fieldsCount[3];j++)
                if(min>fields[3][j][0]){min=fields[3][j][0];num=3;num1=j;}
        for(int j=0;j<fieldsCount[0];j++)
                if(min>fields[0][j][0]){min=fields[0][j][0];num=0;num1=j;}
        for(int j=0;j<fieldsCount[2];j++)
                if(min>fields[2][j][0]){min=fields[2][j][0];num=2;num1=j;}
        return fields[num][num1][1];
        
           //Logger.trace("Поле тип "+i+" имеет id "+fields[i][j][1]+" имеет лвл "+fields[i][j][0]);
           
    
    }
    
    public void refreshInformation(){
         connection.changeVillage(id);           
        Dorf1 dorf1 = new Dorf1(getDorf1());
         
        resurses = dorf1.resurses;
        cornConsumption = dorf1.cornConsumption;
        prodactions = dorf1.prodactions;
        units =dorf1.units;
        fields = dorf1.fields;
        fieldsCount = dorf1.fieldsCount;
        type = dorf1.type;
        time = dorf1.time;
        isBuild=dorf1.isBuild;
        nextRefresh=System.currentTimeMillis()+3600*1000;
        if(!isBuild){
            if(nextBuild==-1) nextBuild=findNextBuiling();
            if(nextBuild!=-1) buildField(nextBuild);
        }else{
          nextRefresh=System.currentTimeMillis()+(500+dorf1.timeEnd)*1000;
        }
    }
    
    public boolean checkResurces(Build b){
    
        if(prodactions[3]<b.resurses[4]+2){
            for(int i=0;i<4;i++){
                if(b.resurses[i]>resurses[i]){
                 nextRefresh=System.currentTimeMillis()+(500+whenResurses(i,b))*1000;
                    return false; 
                }
            }
            return true;
        }else{
            int min=999;int num=0;
             for(int j=0;j<fieldsCount[3];j++)
                if(min>fields[3][j][0]){min=fields[3][j][0];num=j;}
             buildField(fields[3][num][1]);
            return false;
        
        }
    
    }
    
    public int whenResurses(int n, Build b){
    
        int d = b.resurses[n]-resurses[n];
        double time=prodactions[n]/d*3600;
        return (int)time;
    
    }
    
    public void buildField(int id){
        
        Build b = new Build(connection.getBuildInfo(id));
        if(checkResurces(b))
            connection.buildField(id);
        
            
        
    }
    
    public void refreshVillage(){
    
        refreshInformation();
        
    
    }
    
    public void refresh(){
    
         if(nextRefresh<System.currentTimeMillis())
             refreshVillage();
    
    }
    
}
