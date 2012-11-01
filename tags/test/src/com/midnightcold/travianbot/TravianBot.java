package com.midnightcold.travianbot;

import com.midnightcold.travianbot.exceptions.ConfigValueNotFoundException;
import com.midnightcold.travianbot.system.Config;
import com.midnightcold.travianbot.system.Logger;
import com.midnightcold.travianbot.travian.Account;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TravianBot {

    private static TravianBot travianBot;

    private class UserInfo {

        public String host;
        public String username;
        public String password;

        private UserInfo(String host, String username, String password) {
            
            this.host = host;
            this.username = username;
            this.password = password;

        }
    }

    private UserInfo[] loadUsersInfo(String path) throws IOException {

        path = "./config/" + path;

        ArrayList<UserInfo> usersInfoList = new ArrayList<UserInfo>();
        UserInfo[] usersInfo;

        BufferedReader reader;
        InputStream inStream = TravianBot.class.getResourceAsStream(path);
        reader = new BufferedReader(new InputStreamReader(inStream));

        String line;

        while ((line = reader.readLine()) != null) {

            if (!(line.trim().isEmpty() || line.trim().charAt(0) == '#')) {

                String[] values = line.split(":");
                String pass = "";
                for(int i=2;i<values.length;i++)
                    pass+=values[i];
                
                usersInfoList.add(new UserInfo(values[0], values[1], pass));

            }

        }
        
        usersInfo = usersInfoList.toArray(new UserInfo[usersInfoList.size()]);

        return usersInfo;

    }

    private TravianBot() {

        Logger.log.info("Travian Bot");
        UserInfo[] usersInfo;

        try {

            usersInfo = loadUsersInfo(Config.toStr("UsersInfo"));

        } catch (ConfigValueNotFoundException ex) {
            Logger.log.error("Не возможно загрузить имя файла пользователей");
            return;
        } catch (IOException ex) {
            Logger.log.error("Не возможно загрузить файл пользователей");
            return;
        }

        Account[] accounts = new Account[usersInfo.length];

        for (int i = 0; i < accounts.length; i++) {
         //   try {
                accounts[i] = new Account(usersInfo[i].host, usersInfo[i].username, usersInfo[i].password);
                Logger.log.info("Создали аккаунт " + usersInfo[i].host + " " + usersInfo[i].username + " " + usersInfo[i].password);
           // } catch (TravianLowerErrorException ex) {
           //     accounts[i] = null;
           //     Logger.log.error("Не верный логин и пароль у аккаунта " + usersInfo[i].host + "!");
          //  }
        }

    }

    public static void main(String[] args) {

        travianBot = new TravianBot();

    }

    public static TravianBot getTravianBot() {

        return travianBot;

    }
}