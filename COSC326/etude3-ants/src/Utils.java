package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains utility methods that are used in the AntSimulatorApp
 * 
 * @author Beckham Wilson
 */
public class Utils {
    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * This class reads user input from system in
     * 
     * @return an ArrayList of strings from the System.in
     */
    public static ArrayList<String> readSysIn() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }

    /**
     * This method reads a file and returns the contents as a string
     * @param filePath
     * @return a string of the file contents
     */
    public static String outputFileAsString(String filePath) {
        try {
            Scanner in = new Scanner(new File(filePath));
            StringBuilder sb = new StringBuilder();
            while (in.hasNextLine()) {
                sb.append(in.nextLine());
                if (in.hasNextLine()){
                    sb.append(System.lineSeparator());
                }
            }
            in.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This method reads a file and returns the contents as an ArrayList of strings
     * @param filePath
     * @return an ArrayList of strings of the file contents
     */
    public static ArrayList<String> outputFileAsArrLs(String filePath) {
        try {
            Scanner in = new Scanner(new File(filePath));
            ArrayList<String> arrLs = new ArrayList<String>();
            while (in.hasNextLine()) {
                arrLs.add(in.nextLine());
            }
            in.close();
            return arrLs;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}
