package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 * Class to hold useful functions between etudes
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class Utils {

    public static ArrayList<String> readSysIn() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines;
    }

    public static String reverseString(String input) {
        StringBuilder reversedString = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            reversedString.append(input.charAt(i));
        }
        return reversedString.toString();
    }

    public static String outputFileAsString(String filePath) {
        try {
            Scanner in = new Scanner(new File(filePath));
            StringBuilder sb = new StringBuilder();
            while (in.hasNextLine()) {
                sb.append(in.nextLine());
                if (in.hasNextLine()) {
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

    public static int getNumberOfMatches(Strip strip1, Strip strip2) {
        String strip1Str = strip1.toString();
        String strip2Str = strip2.toString();
        int matches = 0;
        for (int colIdx = 0; colIdx < strip1Str.length(); colIdx++) {
            if (strip2Str.charAt(colIdx) == strip1Str.charAt(colIdx)) {
                matches++;
            }
        }

        return matches;
    }

    public static void decrementStock(Map<Strip, Integer> stripToStock, Strip stripToDecrement){
        try{
     
            if (stripToStock.containsKey(stripToDecrement) == false){
                stripToDecrement = stripToDecrement.reverse();
            }
        }catch (Exception e){
            stripToDecrement = stripToDecrement.reverse();
        }
        int countOfEachStrip = stripToStock.get(stripToDecrement);
            if (countOfEachStrip == 1){
                stripToStock.remove(stripToDecrement);
            } else{
                stripToStock.put(stripToDecrement, countOfEachStrip-1);
            }
        }

        public static int getStock(Map<CarpetPair, Integer> pairToStock, CarpetPair pair){
            try{
         
                if (pairToStock.containsKey(pair) == false){
                    pair = new CarpetPair(pair.strip1, pair.strip2.reverse());
                }
            }catch (Exception e){
                pair = new CarpetPair(pair.strip1, pair.strip2.reverse());
            }
            return pairToStock.get(pair);
            
            }

  
}