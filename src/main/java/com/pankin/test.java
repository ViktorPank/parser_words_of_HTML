package com.pankin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        StringBuilder HTMLString = new StringBuilder();

        String pathWork = "C:\\data.txt";

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(pathWork)));

            String line = "";
            while ((line = fileReader.readLine()) != null) {
                HTMLString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OK");

/*
        conn.Conn();
        conn.CreateDB();
        conn.WriteDB();
        conn.selectWordDB("Готов");
        //conn.ReadDB();
        conn.CloseDB();
    */
    }
}
