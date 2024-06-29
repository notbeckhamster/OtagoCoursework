package test;
/**
 * A class that tests the Random class
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
import java.util.Random;

import src.RandomRoller;
import src.Rollin;


public class ExtendedTestingRandom {
    static Random R = new Random();

    public static void main (String[] args) {

        

        double average = 0;
        int testRuns = 1000000;
        for(int i = 0; i < testRuns; i++) {
            
            average += test();
        }
        System.out.println(average/testRuns);
       
    }


    public static int test(){
        Rollin ourStrat = new RandomRoller();

        int[] d = new int[6];
        for(int i = 0; i < d.length; i++) {
            d[i] = R.nextInt(6) + 1;
        }

        int roll = R.nextInt(6) + 1;

        int count = 0;
        
        //Does this check if complete at start?
        while (!Rollin.isComplete(d)) {
            roll = R.nextInt(6) + 1;
            int toChange = ourStrat.handleRoll(roll, d);
            count++;
            if (toChange == -1) continue;
            d[toChange] = roll;
            
        }
            return count;
    }

}