package com.pankin;

public class Delimiter {
    //СОздание разделителей
    String[] delimiterSymbol = {" ", ",", "\\.", "\\!", "\\?", "\"", ";", ":", "\\[", "\\]", "\\(", "\\)", "\\n", "\\r", "\\t"};
    StringBuilder regExpDelimiter = new StringBuilder();

    public Delimiter() {
    }

    //Сборка RegExp, который разделяет слова по указанным разделителям
    public void buildRegexp(){
        //Добавление символа ИЛИ для RegExp
        for (int i = 0; i < delimiterSymbol.length; i++) {
            regExpDelimiter.append(delimiterSymbol[i]);
            if (delimiterSymbol.length - 1 != i) regExpDelimiter.append("|");
        }

    }
}
