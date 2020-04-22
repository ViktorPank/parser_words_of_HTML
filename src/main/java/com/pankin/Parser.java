package com.pankin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Parser {
    public static void main(String[] args)  {

        URL url = null;
        StringBuilder HTMLString = new StringBuilder();
        String[] text = null;
        String bodyText = null;

        //Адрес в сети
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.out.println("Неверный адрес");
            System.exit(1);
        }

        //создание path для файла html который нужно сохранить на жестком диске
        String pathWork = System.getProperty("user.dir");
        pathWork += "/" + url.getHost() + ".html";

        File files = new File(pathWork);
        files.delete();
        files = null;

        System.setProperty("console.encoding","Cp866");

        //блок считывания web страницы и записи файла на диск
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));


            String inputLine;


            //BufferedWriter writer = new BufferedWriter(new FileWriter(pathWork, true));
            //FileOutputStream fileOutputStream = new FileOutputStream(pathWork);
            FileWriter fileWriter = new FileWriter(pathWork);

            String lineSeparator = System.getProperty("line.separator");

            while ((inputLine = in.readLine()) != null) {

                fileWriter.write(inputLine + lineSeparator);

            }

            //запись в файл test.html
            in.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка считывания или записи HTML файла");
            System.exit(1); }


        //создание обьекта разделителя
        Delimiter delimiter = new Delimiter();
        //Сборка RegExp
        delimiter.buildRegexp();


        //Работа с частями файлов для корректной обработки ОЗУ
        File file = new File(pathWork);

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));

            //Буффер считывания файла
            String line = "";
            //Подготовка базы данных
            try {
                conn.Conn();
                conn.CreateDB();
            } catch (ClassNotFoundException e) {
                System.out.println("Ошибка:класс не найден");
            } catch (SQLException e) {
                System.out.println("Ошибка:неверный SQL");
            }


            while ((line = fileReader.readLine()) != null) {
                HTMLString.append(line);

                //Если осталось мало ОЗУ, то обрабатывает и сохраняет накопленный буфер в БД, после удаляет буфер для освобождения памяти
                if (Runtime.getRuntime().freeMemory() < 100000){


                    //использование Jsoup для отделения тэгов от текста
                    Document html = Jsoup.parse(HTMLString.toString());
                    bodyText = html.text();

                    // Разделения строки str с помощью метода split()
                    text = bodyText.split(delimiter.regExpDelimiter.toString());

                    //Добавление частот для слов в DB
                    for (int i = 0; i < text.length; i++) {
                        //Проверка слова и запись
                        try {
                            conn.selectWordDB(text[i]);
                        } catch (ClassNotFoundException e) {
                            System.out.println("Ошибка:класс не найден");
                        } catch (SQLException e) {
                            System.out.println("Ошибка:неверный SQL");
                        }

                    }

                    //Удаление буфера
                    HTMLString.delete(0,HTMLString.length());
                }

            }

            //использование Jsoup для отделения тэгов от текста
            Document html = Jsoup.parse(HTMLString.toString());
            bodyText = html.text();

            // Разделения строки bodyText с помощью метода split()
            text = bodyText.split(delimiter.regExpDelimiter.toString());

            //Добавление частот для слов в DB
            for (int i = 0; i < text.length; i++) {

                //Проверка слова и запись
                try {
                    conn.selectWordDB(text[i]);
                } catch (ClassNotFoundException e) {
                    System.out.println("Ошибка:класс не найден");
                } catch (SQLException e) {
                    System.out.println("Ошибка:неверный SQL");
                }

            }

            //удаление строкового буфера
            HTMLString.delete(0,HTMLString.length() );

        } catch (IOException e) {
            System.out.println("Ошибка считывания или обработки файла");
        }

        //Вывод слов и их частоту
        try {
            conn.ReadDB();
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка:класс не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка:неверный SQL");
        }

        //закрытие соединения с БД
        try {
            conn.CloseDB();
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка:класс не найден");
        } catch (SQLException e) {
            System.out.println("Ошибка:неверный SQL");
        }
    }


}
