package src;

import java.util.ArrayList;


/**
 * This class runs the harmoniousfinder to search for pairs and outputs them
 * 
 * @author Beckham Wilson
 */
public class HarmoniousApp {
    public static void main(String[] args){
        HarmoniousFinder hf = new HarmoniousFinder();
        ArrayList<String> harmoniousNumbers = hf.getHarmoniousNumbers();
        System.out.println(String.join("\n", harmoniousNumbers));


    }

}