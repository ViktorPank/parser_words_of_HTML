package com.pankin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db.s3db");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'words' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'word' text, 'count' INT);");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException {
        //тест
        statmt.execute("INSERT INTO 'words' ('word', 'count') VALUES ('слово', 1); ");


    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT word, count FROM words");

        while (resSet.next()) {
            String word = resSet.getString("word");
            String count = resSet.getString("count");
            System.out.println(word + " - " + count);

        }

    }

    //-------------Добавление новых слов и обновление счетчика существующих в базе данных-------------
    public static void selectWordDB(String wordS) throws ClassNotFoundException, SQLException {

        try {
            resSet = statmt.executeQuery("SELECT word, count  FROM words WHERE word='" + wordS + "'");


            if (resSet.next()) {
                int count = resSet.getInt("count");
                count++;
                statmt.executeUpdate("UPDATE 'words' SET 'count' =" + count + " WHERE word = '" + wordS + "' ");

            } else {
                statmt.execute("INSERT INTO 'words' ('word', 'count') VALUES ('" + wordS + "', 1); ");

            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        }

        /*
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  word = resSet.getString("word");
            String  count = resSet.getString("count");
            System.out.println( "ID = " + id );
            System.out.println( "word = " + word );
            System.out.println( "count = " + count );
        }
        */
        //System.out.println("Таблица выведена");


    }

    // --------Закрытие и удаление таблицы для следующей обработки--------
    public static void CloseDB() throws ClassNotFoundException, SQLException {
        //statmt.executeUpdate("DROP TABLE words");
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }
}
