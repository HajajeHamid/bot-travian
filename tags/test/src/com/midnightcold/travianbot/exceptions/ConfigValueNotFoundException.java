package com.midnightcold.travianbot.exceptions;

/**
 * Запрашиваемое значение не найдено
 *
 * @author Esr
 */
public class ConfigValueNotFoundException extends Exception {

    /**
     * Название значения, которое не смогли найти/
     */
    private String valueName;

    /**
     * Создает новое исключание/
     *
     * @param valueName Название значения, которое не смогли найти
     */
    public ConfigValueNotFoundException(String valueName) {

        this.valueName = valueName;

    }

    /**
     * Получить ошибку.
     *
     * @return Ошибка
     */
    @Override
    public String getMessage() {

        return "Ошибка при загрузке параметра " + valueName;

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
