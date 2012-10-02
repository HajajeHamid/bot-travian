package com.midnightcold.travianbot.system;

import com.midnightcold.travianbot.exception.ConfigValueNotFoundException;
import java.io.*;
//TODO: Сделать нормальные коментарии
/**
 *
 * Логирования программы.
 *
 *
 *
 * Поддерживается логирование в файл и в консоль Уровни логирования: fatal,
 *
 * error, warn, info, trace Настройка способа логирования и уровней логирования
 *
 * производится в конфигурационном файле Пример Logger.info("Hello, World!");
 *
 *
 *
 * @author Esr
 *
 * @version 1.0
 *
 */
public class Logger {

    /**
     *
     * Включено ли логировние в файл.
     *
     */
    private static boolean toFile;
    /**
     *
     * Включено ли логировние в консоль.
     *
     */
    private static boolean toConsole;
    /**
     *
     * Число определяющее включеные уровни логирования.
     *
     */
    private static int mode;
    /**
     *
     * Булевый массив, определяющий включеные уровни. Каждому номеру элемента
     *
     * соотвествует порядковый номер уровня
     *
     */
    private static boolean[] modes;
    /**
     *
     * Название файла, в которвый будет производиться логирование.
     *
     */
    private static String fileName;
    /**
     *
     * Указатель на поток в файл.
     *
     */
    private static PrintWriter loggerFile;
    /**
     * Созданный лог
     */
    public final static Logger log = new Logger();

    /**
     *
     * Создать новый логер.
     *
     *
     *
     * @throws ConfigValueNotFoundException
     *
     * @throws FileNotFoundException
     *
     * @throws UnsupportedEncodingException
     *
     */
    public Logger(){
        try {
            toFile = Config.toBool("LoggerToFile");

            toConsole = Config.toBool("LoggerToConsole");

            mode = Config.toInt("LoggerMode");

            modes = makeModes(mode);

            if (toFile) {

                fileName = Config.toStr("LoggerFileName");

                loggerFile = openFile();

                fileName = Config.toStr("LoggerFileName");

            }
        } catch (Exception ex) {
            System.out.println("Logger fail loaded:" + ex.toString());
        }
               

    }

    /**
     *
     * Преобразование числа, в массив включеных уровней логирования.
     *
     *
     *
     * @param mode Число, указывающие на включеные уровни
     *
     * @return Булевый массив, определяющий включеные уровни. Каждому номеру
     *
     * элемента соотвествует порядковый номер уровня
     *
     */
    private boolean[] makeModes(int mode) {

        boolean[] modes = new boolean[6];

        int n = 0, s;

        mode += 16;

        while (mode >= (s = (int) Math.pow(2, n++))) {

            modes[n - 1] = (((mode & s) / s) == 1) ? true : false;

        }

        return modes;

    }

    /**
     *
     * Открывает файл для чтения.
     *
     *
     *
     * @return Указатель на поток в файл
     *
     */
    private PrintWriter openFile() throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter file = null;

        file = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Logger.fileName), "utf-8"));

        return file;

    }

    /**
     *
     * Записывает сообщение в файл.
     *
     *
     *
     * @param message Сообщение, которое надо записать
     *
     */
    private void writeToFile(String message) {

        loggerFile.write(message + '\n');

    }

    /**
     *
     * Определяет требуется запись, если да то в куда.
     *
     *
     *
     * @param message Сообщение, которое надо записать
     *
     * @param mode Номер уровня логирования
     *
     */
    private void msg(String message, int mode) {

        if (modes[mode]) {

            String date = (new java.text.SimpleDateFormat("dd-MMM-yy hh:mm:ss aaa")).format(java.util.Calendar.getInstance().getTime());

            message = date + " " + message;

            if (toFile) {

                writeToFile(message);

            }

            if (toConsole) {

                System.out.println(message);

            }

        }

    }

    /**
     *
     * Записать ошибку.
     *
     *
     *
     * @param string Ошибка
     *
     * @param thrwbl
     *
     */
    public void error(String string, Throwable thrwbl) {

        msg("Error: " + string + ": " + thrwbl.getMessage(), 3);

    }

    /**
     *
     * Записать ошибку.
     *
     *
     *
     * @param thrwbl
     *
     */
    public void error(Throwable thrwbl) {

        msg("Error: " + thrwbl.getMessage(), 3);

    }

    /**
     *
     * Записать ошибку.
     *
     *
     *
     * @param string
     *
     */
    public void error(String string) {

        msg("Error: " + string, 3);

    }

    /**
     *
     * Записать предупреждение.
     *
     *
     *
     * @param string Предупреждение
     *
     */
    public void warn(String string) {

        msg("Warn: " + string, 2);

    }

    /**
     *
     * Записать предупреждение.
     *
     *
     *
     * @param string Предупреждение
     *
     * @param thrwbl
     *
     */
    public void warn(String string, Throwable thrwbl) {

        msg("Warn: " + string + ": " + thrwbl.getMessage(), 2);

    }

    /**
     *
     * Записать информационное сообщение.
     *
     *
     *
     * @param string Сообщение
     *
     */
    public void info(String string) {

        msg("Info: " + string, 1);

    }

    /**
     *
     * Записать отладочное сообщение.
     *
     *
     *
     * @param string Сообщение
     *
     */
    public void debug(String string) {

        msg("Debug: " + string, 0);

    }
}
