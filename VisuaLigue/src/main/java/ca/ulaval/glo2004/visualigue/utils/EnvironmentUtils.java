package ca.ulaval.glo2004.visualigue.utils;

public class EnvironmentUtils {

    public static String getAppDataDirectory() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN")) {
            return System.getenv("AppData");
        } else {
            return System.getProperty("user.home") + "/Library/Application Support";
        }
    }
}
