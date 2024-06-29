package src;

import java.util.ArrayList;

/**
 * Entry point into Carpet generation app
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class CarpetsApp {

    private static int carpetSize;
    private static String flags;

    public static void main(String[] args) {
    // Delay so I can connect visualVM
    // System.out.println("Going to sleep for 5 seconds...");
    // try {
    //   Thread.sleep(5000);
    // } catch (InterruptedException e) {
    //   System.out.println("Thread interrupted while sleeping!");
    // }
    // Print a message after sleep
    // System.out.println("Woke up!");
        carpetSize = -1;
        flags = "";

        if (args.length == 0) {
            System.out.println("No arguments given");
            return;
        }
        if (args.length >= 1 && args[0] != null) {
            flags = args[0];
        }
        if (args.length >= 2 && args[1] != null) {
            try {
                carpetSize = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Error invalid size");
                return;
            }

        }
        if (carpetSize <= 0) {
            return;
        }
        ArrayList<String> lines = Utils.readSysIn();
        System.out.println(process(flags, carpetSize, lines));

    }

    public static String process(String flags, int carpetSize, ArrayList<String> lines) {
        if (carpetSize > lines.size()){
            return "not possible";
        }
        lines.removeIf(s -> s.isEmpty());
        if (flags.equals("-m")) {
            CarpetGen carpetGen = new CarpetGen(lines);
            Carpet maxMatchCarpet = carpetGen.getMaxMatches(carpetSize);
            if (maxMatchCarpet == null) {
                return "not possible";
            } else {
                return maxMatchCarpet.formattedToString();
            }

        }

        if (flags.equals("-n")) {
            CarpetGen carpetGen = new CarpetGen(lines);
            Carpet noMatchCarpet = carpetGen.getNoMatches(carpetSize);
            if (noMatchCarpet == null) {
                return "not possible";
            } else {
                return noMatchCarpet.formattedToString();
            }

        }

        if (flags.equals("-b")) {
            CarpetGen carpetGen = new CarpetGen(lines);
            Carpet balancedCarpet = carpetGen.getBalancedCarpet(carpetSize);
            if (balancedCarpet == null) {
                return "not possible";
            } else {
                return balancedCarpet.toStringBalanced();
            }
        }

        return "Invalid Option";
    }
}
