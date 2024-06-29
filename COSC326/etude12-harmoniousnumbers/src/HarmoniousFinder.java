package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;



/**
 * This class find the harmonious pairs
 * 
 * @author Beckham Wilson
 */
public class HarmoniousFinder {

    public ArrayList<String> getHarmoniousNumbers(){
 
        ArrayList<int[]> listOfHarmoniousNumbers = new ArrayList<int[]>();
        int maxFirstNum = 2000000;
        HashMap<Integer, Integer> numToSumPD = new HashMap<Integer, Integer>();
        
        for (int firstNum = 1; firstNum<=maxFirstNum; firstNum++){
            
            if (!numToSumPD.containsKey(firstNum)){
                numToSumPD.put(firstNum, getSumOfProperDivisors(firstNum));
            }
            int secondNum = numToSumPD.get(firstNum);
            if (!numToSumPD.containsKey(secondNum)){
                numToSumPD.put(secondNum, getSumOfProperDivisors(secondNum));
            }
            if (numToSumPD.get(secondNum).equals(firstNum) && firstNum < secondNum){
                listOfHarmoniousNumbers.add(new int[]{firstNum, secondNum});
            }


            
        }



     
             
        ArrayList<String> result = new ArrayList<>();
        for (int[] arr : listOfHarmoniousNumbers) {
            result.add(arr[0] + " " + arr[1]); 
        }
        return result;
    }   

    public int getSumOfProperDivisors(int num){
        int sum = 0; 
        for (int i = 2; i<= Math.sqrt(num); i++){
            if (num % i == 0){
                sum += i;
                if (i != num/i){
                sum += num/i;
                }
        }
            
        }
        return sum;
    }
}
