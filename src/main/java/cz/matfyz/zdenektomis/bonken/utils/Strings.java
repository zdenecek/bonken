package cz.matfyz.zdenektomis.bonken.utils;

/**
 * Utility class for string manipulation.
 */
public class Strings {

    // Padding methods from
    // https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java


    /**
     * @param s String to be padded
     * @param n Length of the resulting string
     * @return String padded with spaces on the right side
     */
    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    /**
     * @param s String to be padded
     * @param n Length of the resulting string
     * @return String padded with spaces on the left side
     */
    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}
