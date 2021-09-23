package com.example.demo;

import java.util.ArrayList;

public class PriceParser {
    private static boolean READING_NUMBER = false;
    private static StringBuilder NUMBER_BUILDER = new StringBuilder();

    public static double parsePrice(String str){
        readNumber(str);
        return getDouble();
    }

    public static double parseDelivery(String str) {
        if (str.contains("Бесплатная") || str.contains("не указана")) {
            return 0.0;
        }
        readNumber(str);
        return getDouble();
    }

    private static boolean isNumber(char ch){
        return switch (ch) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',' -> true;
            default -> false;
        };
    }

    private static void startReading(){
        NUMBER_BUILDER.delete(0, NUMBER_BUILDER.length());
        READING_NUMBER = true;
    }

    private static void endReading(){
        READING_NUMBER = false;
    }

    private static void readNumber(String str){

        char[] chars = str.replaceAll("\\s", "")
                .replaceAll("US", "")
                .toCharArray();

        for (char ch : chars) {

            if (ch == '$'){
                if (!READING_NUMBER) {
                    startReading();
                    continue;
                }
            }

            if (READING_NUMBER){
                if (isNumber(ch)){
                    NUMBER_BUILDER.append(ch);
                }
                else {
                    endReading();
                }
            }
        }
    }

    private static double getDouble(){
        return Double.parseDouble(NUMBER_BUILDER.toString().replace(',', '.'));
    }
}












