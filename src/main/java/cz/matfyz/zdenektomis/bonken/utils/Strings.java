package com.bonken.utils;

public class Strings {

    // Padding methods from
    // https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}
