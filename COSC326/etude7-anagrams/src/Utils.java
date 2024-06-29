package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class containing common functions usually related to I/O
 * 
 * @group TwoBobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class Utils {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static ArrayList<String> readSysIn() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }

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

    public static String toStringSpaceDelmitted(ArrayList<Word> arrLs){
        String result = "";
        for (int i = 0; i< arrLs.size() ; i++){
            result += arrLs.get(i);
            if (i!=arrLs.size()-1){
                result += " ";
            }

        }
        return result;
    }
}