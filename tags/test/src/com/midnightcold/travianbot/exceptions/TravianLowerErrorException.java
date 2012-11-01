package com.midnightcold.travianbot.exceptions;

// Не возможно подконектиться
// Не верный логин\пароль
// Не верная страница (404)
// Запрешен доступ(403)
// ....

public class TravianLowerErrorException extends Exception{
    
    public TravianLowerErrorException(String error){
    
        super(error);
        
    }

    public TravianLowerErrorException() {
        
    }
    
}
