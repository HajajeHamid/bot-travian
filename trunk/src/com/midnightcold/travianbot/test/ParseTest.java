package com.midnightcold.travianbot.test;

import com.midnightcold.travianbot.parser.BuildParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseTest {
    
    public static void main(String[] args){
        try {
            String s = "";
            Scanner in = new Scanner(new File("C:\\http\\TravianBot\\src\\com\\midnightcold\\travianbot\\test\\build.php"));
            while(in.hasNext()){
                s += in.nextLine() + "\r\n";
            }
            in.close();
            System.out.println(BuildParser.upgradeFieldResourse(s)[1]);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
