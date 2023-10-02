package app.util;
public class Strings {
    /**
     * Change the first letter of a string to upper case.
     * @param string The string to transform
     * @return the string with the first letter capitalized
     */
    public static String capitaliseFirstLetter(String string) {
        return string.substring(0, 1)
                .toUpperCase() + string.substring(1);
    }
}
