package com.midnightcold.travianbot.exception;

/**
 * Ошибка открытия файла конфигурации
 *
 * @author Esr
 */
public class ConfigFileException extends Exception {

    /**
     * Ошибка.
     */
    String error;

    /**
     * Создать новое исключение
     *
     * @param error Ошибка
     */
    public ConfigFileException(String error) {
        super(error);
        this.error = error;

    }

    /**
     * Получить ошибку.
     *
     * @return Ошибка
     */
    public String getError() {

        return error;

    }

    /**
     * Получить ошибку.
     *
     * @return Ошибка
     */
    @Override
    public String toString() {

        return getMessage();

    }
}
