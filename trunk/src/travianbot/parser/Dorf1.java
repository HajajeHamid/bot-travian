package travianbot.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tools.RegexpUtils;
import travianbot.Logger;
import travianbot.game.Game;

/**
 *
 * @author Esr
 */
public class Dorf1 {
    
public int[] resurses;
public int cornConsumption;
public int golds;
public int silvers;
public int[] villages;
public int[] prodactions;
public int[] units;
public int[][][] fields;
public int[] fieldsCount;
public int type;
public int time;
public String name;
public int loyalty;
public boolean isBuild;
String html;


//Квесты
//Передвижения войск
//Постройки

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

private void parseName(){

    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/id=\"villageNameField\">(.+?)<\\//", html, rez);

    name = rez.get(0).get(1).trim();
      

}

private void parseLoyalty(){

    
    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/class=\"loyalty high\">(.+?)<\\//", html, rez);

    String res = rez.get(0).get(1);
    String r[] = res.split(":");
    r=r[1].split("%");
    loyalty = Integer.parseInt(r[0].trim());

}
  


private void parseFields(){

    int[][] resource = new int[18][2];
    
    BufferedReader file = getBReader("typeVillages");
    String type=null;

    int f=3;
    List<List<String>> d = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<div id=\"village_map\" class=\"f[0-9]{1}\">/", html, d);
    String s =d.get(0).get(0); 
    f=s.charAt(s.length()-3)-'0';
    this.type=f;
    //System.out.println(f);



    for(int i=0;i<f;i++){

            try {
                type = file.readLine();
            } catch (IOException e) {

               //e.printStackTrace();
            }

    }

    for(int i=0;i<18;i++){

            resource[i][0]=type.charAt(i)-'0';

    }

    List<List<String>> rez = new ArrayList<List<String>>();

    RegexpUtils.preg_match_all("/<area href=\"build[^<]{1,}/", html, rez);

    for(int i=0;i<rez.size();i++) {
            String g=rez.get(i).get(0);
            
            int rezint = g.charAt(g.length()-4)-'0';
            resource[i][1]=rezint;
    }



    fieldsCount=new int[4];
    fieldsCount[0]=fieldsCount[1]=fieldsCount[2]=fieldsCount[3]=0;
    fields = new int[4][18][2];
    for(int i=0;i<18;i++){    
            fields[resource[i][0]-1][fieldsCount[resource[i][0]-1]][0]=resource[i][1];
            fields[resource[i][0]-1][fieldsCount[resource[i][0]-1]++][1]=i+1;
    }
    
   

}

void parseVillages(){

    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/href=\"\\?newdid=(.+?)\"/", html, rez);
			
			
			
    villages = new int[rez.size()];
			
    for(int i=0;i<rez.size();i++) {
				
        villages[i]=Integer.parseInt(rez.get(i).get(1));
				
     
    }
			
    
			
}

void parsePlus(){
    
    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/class=\"gold\" \\/><br \\/>(.+?)</", html, rez);

    golds = Integer.parseInt(rez.get(0).get(1));
    RegexpUtils.preg_match_all("/class=\"silver\" \\/><br \\/>(.+?)</", html, rez);
    silvers = Integer.parseInt(rez.get(0).get(1));
		
}

void parseTime(){
			
    time=0;

    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<span id=\"tp1\">(.+?)<\\/span>/", html, rez);
    String[] s = rez.get(0).get(1).split(":");
    time = Integer.parseInt(s[0])*3600+Integer.parseInt(s[1])*60+Integer.parseInt(s[2]);
    
    
			
}

void parseResourse(){
			
    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<span id=\"l[0-9]\" class=\"value \">(.+?)/[0-9]{1,}<\\/span>/", html, rez);
    resurses = new int[5];
    for(int i=0;i<4;i++){

       resurses[i]=Integer.parseInt(rez.get(i).get(1));

    }

   cornConsumption=Integer.parseInt(rez.get(4).get(1));

}

void  parseProduction(){
			
    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<td class=\"num\">(.+?)<\\/td>/", html, rez);
    System.out.println("pr = " + rez.get(0).get(1));
    prodactions = new int[5];
    for(int i=0;i<4;i++){// TODO Возможно тут 5, а не 4

            prodactions[i]=Integer.parseInt(rez.get(i).get(1).trim());

    }

}


//TODO Вообще не понятно работает или нет
void  parseUnits(){
			
    units = new int[2];

    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<img class=\"unit u(.+?)\" [^о]{1,} class=\"num\">(.+?)<\\/td>/", html, rez);
    System.out.println("u = " + rez.get(0).get(1));
    System.out.println("n = " + rez.get(0).get(2));

    

}

void parseBuildingProduction(){
    List<List<String>> t = new ArrayList<List<String>>();
    isBuild = RegexpUtils.preg_match_all("/building_contac(.+?)/", html, t);
    
    if(isBuild){
    List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/<span id=\"timer1\">(.+?)</span>/", html, rez);
    System.out.println("u1 = " + rez.get(0).get(1));
    //System.out.println("n = " + rez.get(0).get(2));
    }

}


    public Dorf1(String html){
    
        Logger.info(html);
        this.html=html;
        parseFields();
        parseVillages();
        parsePlus();
        parseTime();
        parseResourse();
        parseProduction();
        parseUnits();
        parseBuildingProduction();
        parseName();
        parseLoyalty();
        


//public int[][][] fields;
//public int[] fieldsCount;



        /*
        Logger.trace("Сейчас "+time);
        Logger.trace("У игрока голда "+golds);
        Logger.trace("У игрока серебра "+silvers);
        Logger.trace("Деревни");
        for(int i=0;i<villages.length;i++){
           Logger.trace("Деревня с id "+villages[i]); 
        }

        Logger.trace("Деревня "+name);
        Logger.trace("Лоялность равна "+loyalty);
        Logger.trace("Тип деревни "+type);
        
        for(int i=0;i<resurses.length;i++){
           Logger.trace("Ресурса "+i+" в деревне "+resurses[i]); 
        }
        
        Logger.trace("Потребление зерна"+cornConsumption);

        for(int i=0;i<prodactions.length;i++){
           Logger.trace("Ресурса "+i+" производиться "+prodactions[i]); 
        }
        
        for(int i=0;i<4;i++){
            for(int j=0;j<fieldsCount[i];j++){
                
                    Logger.trace("Поле тип "+i+" имеет id "+fields[i][j][1]+" имеет лвл "+fields[i][j][0]);
                    
                
                
            }
            
        }
	//*/		
         
     }
    
    
}
