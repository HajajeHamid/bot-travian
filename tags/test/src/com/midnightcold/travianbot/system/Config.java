package com.midnightcold.travianbot.system;

import com.midnightcold.travianbot.exceptions.ConfigFileException;
import com.midnightcold.travianbot.exceptions.ConfigValueNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Загрузка конфигурации из файла. типа данных создана своя функция. В качесте
 * аргумента передается имя значение, которые надо получить Для записи булевых
 * значений используте строки, true и false Функция для загрузки значений
 * load();
 *
 * @author Esr
 *
 */
public class Config {

    /**
     * Массив значений, загруженных из файла.
     */
    private static List<String[]> configs = null;
    public final static boolean loaded = Config.load("../config/MainConfig.cnf", true);

    /**
     * Получить значение из массива.
     *
     * Функция проходит все значения, находящиеся в массиве, и находит нужное
     *
     * @param value Имя значения, которое ищется
     * @return Значение записи, если имя не найдено - null
     */
    private static String value(String value) throws ConfigValueNotFoundException {

        for (int i = 0; i < configs.size(); i++) {
            String[] val = configs.get(i);
            if (val[0].equals(value)) {
                return val[1];
            }
        }
        throw new ConfigValueNotFoundException(value);

    }

    /**
     * Получает значение в типе int.
     *
     * Для преобразование из строки используется Integer.parseInt
     *
     * @param value Имя значения, которое ищется
     * @return Значение записи
     */
    public static int toInt(String value) throws ConfigValueNotFoundException {

        return Integer.parseInt(value(value));

    }

    /**
     * Получить значение в типе String.
     *
     * @param value Имя значения, которое ищется
     * @return Значение записи
     */
    public static String toStr(String value) throws ConfigValueNotFoundException {

        return value(value);

    }

    /**
     * Получить значение в типе boolean. Для преобразования используется
     * стравнение строки через equals
     *
     * @param value Имя значения, которое ищется
     * @return Значение записи @note Если в файле записано страка отличная от
     * true или false, то функция возращает false
     */
    public static boolean toBool(String value) throws ConfigValueNotFoundException {

        if (value(value).equals("true")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Чтение файла, и загрузка значений в массив.
     *
     * @param fileName Путь до файла
     * @param fromJar Загружается ли файл из jar. true - да, false - нет
     */
    private static void loadFromFile(String fileName, boolean fromJar) throws ConfigFileException {

        List<String[]> configs = new ArrayList<String[]>();

        try {
            BufferedReader reader;
            if (fromJar) {

                InputStream inStream = Config.class.getResourceAsStream(fileName);
                reader = new BufferedReader(new InputStreamReader(inStream));

            } else {

                reader = new BufferedReader(new FileReader(fileName));

            }

            String line;

            while ((line = reader.readLine()) != null) {

                if (!(line.trim().isEmpty() || line.trim().charAt(0) == '#')) {

                    String[] value = line.split("=");
                    configs.add(value);
                }
            }

        } catch (FileNotFoundException e) {
            throw new ConfigFileException("Файл конфигурации не найден");
        } catch (IOException e) {
            throw new ConfigFileException("Ошибка чтения файла конфигурации");
        } catch (Exception e) {
            throw new ConfigFileException("Ошибка открытия файла конфигурации");
        }

        Config.configs = configs;

    }

    /**
     * Загрузить конфигуратор.
     *
     * @param fileName Путь до файла
     * @param fromJar Загружается ли файл из jar. true - да, false - нет
     */
    public static boolean load(String configFile, boolean fromJar) {
        try {
            loadFromFile(configFile, fromJar);
            return true;
        } catch (ConfigFileException ex) {
            System.out.println("Config fail load " + ex.toString());
        }
        return false;

    }
}
