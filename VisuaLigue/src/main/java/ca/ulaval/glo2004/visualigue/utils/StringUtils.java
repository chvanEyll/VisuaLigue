package ca.ulaval.glo2004.visualigue.utils;

public class StringUtils {

    public static String getFirstLetterOfEachWord(String categoryName) {
        StringBuilder abbreviation = new StringBuilder();
        String[] name = categoryName.split(" ");
        for (String word : name) {
            if (word.length() > 0) {
                abbreviation.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return abbreviation.toString();
    }

}
