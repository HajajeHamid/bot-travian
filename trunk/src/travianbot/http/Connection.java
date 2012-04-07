package travianbot.http;

import travianbot.Logger;

/**
 *
 * @author Esr
 */


public class Connection extends HttpWorker {
    
    protected Connection(String host, int port){
        super(host,port);
       
    }
    	
	
    protected boolean getError(){

            if(error!=0) return true; else return false;

    }

    protected String getFullError(){

            switch(error){

                    case 1: return "Can't connect";
                    default: return "No error";

            }		

    }




    protected String rec(String url){

            makeConnect("GET "+url+" HTTP/1.1", "");
            Logger.trace("GET "+url+" HTTP/1.1");
            Logger.trace(html);
            return html;

    }

    protected String recGet(String url,String[][] values){

            int l = values.length;
            String get="";
            for(int i=0;i<l;i++){

                    get += values[i][0]+"="+values[i][1]+"&";

            }
            get = get.substring(0, get.length()-1);

            makeConnect("GET "+url+"?"+get+" HTTP/1.1", "");
            Logger.trace("GET "+url+"?"+get+" HTTP/1.1");
            return html;

    }

    protected String recPOST(String url,String[][] values){

            int l = values.length;
            String post="";
            for(int i=0;i<l;i++){

                    post += values[i][0]+"="+values[i][1]+"&";

            }
            post = post.substring(0, post.length()-1);

            makeConnect("POST "+url+" HTTP/1.1", post);
            Logger.trace("POST "+url+" HTTP/1.1"+post);
            return html;
            
    }

    protected String getInput(String name){

            String[] hid=html.split("name=\"login\" value=\"");

            String[] hid1=hid[1].split("\"");
            return hid1[0]; 
    }
    
}
