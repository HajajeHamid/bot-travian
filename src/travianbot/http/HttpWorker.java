package travianbot.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import travianbot.Logger;

/**
 *
 * @author Esr
 */
public class HttpWorker {
    
    protected String[][] head;
    protected String html;
    protected  String host;
    protected  int port;
    private BufferedReader in;
    private PrintWriter  out;
    protected int error;
    private String[][] cookie = new String[0][0];
    protected int headCount;
    
    
    
     protected HttpWorker(String server, int port){

            host=server;
            this.port=port;
            Logger.trace("Создали подключени к "+host+":"+port);

    }
      
	
    private String mkCookie(String[][] cookie){

            String cookies="";

            for(int i=0;i<cookie.length;i++){

                    cookies += cookie[i][0]+"="+cookie[i][1]+"; ";

            }

            cookies= cookies.substring(0, cookies.length()-2);

            return cookies;

    }	

    private String mkHead(int len){

            String head = "";

            head +="Host: "+host+"\n";
            head +="User-Agent: TravianBot (Win16) TravianBot/20111016 TravianBot/0.0.1\n";
            head +="Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n";
            head +="Accept-Language: ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3\n";
            head +="Accept-Encoding: deflate\n";
            head +="Accept-Charset: windows-1251,utf-8;q=0.7,*;q=0.7\n";
            head +="Connection: close\n";
            if(cookie.length!=0) head +="Cookie: "+mkCookie(cookie)+"\n";
            if(len!=0) head +="Content-length: "+len+"\nContent-Type: application/x-www-form-urlencoded;\n";

            return head;

    } 

    private boolean alreadyCookie(String name){

            for(int i=0;i<cookie.length;i++){

                    if(cookie[i][0].equals(name))return true;

            }

            return false;
    }

    private int findCookieNumber(String name){

            for(int i=0;i<cookie.length;i++){

                    if(cookie[i][0].equals(name))return i;

            }

            return -1;
    }

    private void setCookie(String[][] head){

            int n=0;
            String[] cook;

            for(int i=0;i<headCount;i++){

                    if(head[i][0].equals("Set-Cookie")){

                            cook = head[i][1].split("=");
                            if(!alreadyCookie(cook[0])) n++; 

                    } 

            }

            String[][] newCookie = new String[n+cookie.length][2];
            System.arraycopy(cookie, 0, newCookie, 0, cookie.length);
            int num=cookie.length;
            for(int i=0;i<headCount;i++){

                    if(head[i][0].equals("Set-Cookie")){
                            cook = head[i][1].split("=");

                            if(!alreadyCookie(cook[0])){

                                    newCookie[num][0]=cook[0];
                                    String[] full = cook[1].split(";");
                                    newCookie[num][1]=full[0];
                                    num++;

                            }else{

                                    int nom = findCookieNumber(cook[0]);
                                    String[] full = cook[1].split(";");
                                    newCookie[nom][0]=full[0];

                            }



                    }

            }

            cookie=newCookie;


    }

    protected void makeConnect(String first, String text){

            error = 0;
            Socket fromserver;

            try {

                    fromserver = new Socket(host,port);

                    in = new BufferedReader(new	InputStreamReader(fromserver.getInputStream()));

                    out = new	PrintWriter(fromserver.getOutputStream(),true);

                    String fserver;

                    out.println(first + "\n"+ mkHead(text.length()) +"\n"+text);
                    int i=0,n=0;

                    int headers=1;

                    head = new String[128][128]; //НЕТ ТРУ!!!
                    html = "";

                    while((fserver = in.readLine())!=null){

                            if(fserver.equals(""))
                                    headers = 0;
                            if(headers == 1){

                                    if(i!=0){

                                            String[] test = fserver.split(":");
                                            head[n++][0]=test[0];
                                            head[n-1][1]=test[1];
                                            head[n-1][1] = head[n-1][1].trim();
                                    }

                                    i++;

                            }else{

                                    html += fserver+"\n";

                            }

                    }

                    headCount=i-1;


                    setCookie(head);

            } catch (IOException e) {

                    error=1;

            }



    }
    
}
