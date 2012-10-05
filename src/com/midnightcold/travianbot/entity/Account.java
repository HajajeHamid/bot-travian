package com.midnightcold.travianbot.entity;

import com.midnightcold.travianbot.entity.travian.*;
import com.midnightcold.travianbot.exception.ConfigFileException;
import com.midnightcold.travianbot.exception.TravianLowerErrorException;
import com.midnightcold.travianbot.travianWorker.TravianConnector;
import com.midnightcold.travianbot.travianWorker.TravianParser;
import java.io.*;

public class Account {

    TravianConnector connetion;
    Village[] villages;
    public int gold;
    public int silver;

    public Account(String hostname, String username, String password) throws TravianLowerErrorException {

//      connetion = new TravianConnector(hostname, username, password);
        String html1 = "";
        String html2 = "";
        try {

            BufferedReader reader1;
            BufferedReader reader2;
            
            reader1 = new BufferedReader(new FileReader("D:\\Other\\TravianBot\\src\\com\\midnightcold\\travianbot\\pages\\dorf1.htm"));
             reader2 = new BufferedReader(new FileReader("D:\\Other\\TravianBot\\src\\com\\midnightcold\\travianbot\\pages\\dorf2.htm"));

            String line;

            while ((line = reader1.readLine()) != null) {
                html1 += line;

            }
            
             while ((line = reader2.readLine()) != null) {
                html2 += line;

            }
            

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        
         
             villages= TravianParser.updateAccount(html1, html2);
             
             for(Village village: villages)
                 print_village(village);

    }

    public void update() {
    }

    private void print_village(Village village) {
        for(Building building : village.buildings){
            System.out.println("Здание типа "+building.type+" имеет уровень "+building.level+"");
        }
        for(Field field : village.fields){
            System.out.println("Поле типа "+field.type+" имеет уровень "+field.level+"");
        }
        for(Production production : village.productions){
            System.out.println("Производство ресурса "+production.type+" равняеется "+production.production+"");
        }
        for(Resource resource : village.resourses){
            System.out.println("Количество ресурса "+resource.type+" на складах равно "+resource.amount+"");
        }
            System.out.println("Количество секундт "+village.time);
            //TODO: Вывод войск
        //for(Troop troop : village.troops){
        
        //}
        
    }
}
