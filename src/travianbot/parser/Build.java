/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package travianbot.parser;

import java.util.ArrayList;
import java.util.List;
import tools.RegexpUtils;
import travianbot.Logger;

/**
 *
 * @author Esr
 */
public class Build {
    
    int time;
    public int[] resurses;
    String html;
    
    public void parseResurses(){
        resurses = new int[5];
    List<List<String>> rez;
    for(int i=1;i<6;i++){
          rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/class=\"resources r"+i+"\"(.+?)<\\//", html, rez);
   
 
    String res = rez.get(0).get(0);
    rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/\\/>(.+?)</", res, rez);
 
         res = rez.get(0).get(1);
        
         resurses[i-1]=Integer.parseInt(res.trim());
    }
    } 
    
    public void parseTime(){
        
        List<List<String>> rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/class=\"clock\"(.+?)<\\//", html, rez);
   
  
    String res = rez.get(0).get(0);
    rez = new ArrayList<List<String>>();
    RegexpUtils.preg_match_all("/\\/>(.+?)</", res, rez);
 
         res = rez.get(0).get(1);
         String r[]=res.split(":");
         time=Integer.parseInt(r[0])*3600+Integer.parseInt(r[1])*60+Integer.parseInt(r[2]);
         
    
    }
    
    public Build(String html){
        
        this.html=html;    
        parseResurses();
        parseTime();
    
    }
    
}
