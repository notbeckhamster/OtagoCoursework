package src;
/**
 * A class that contains a method to check if an integer is in an array
 * @group BobbyTables
 * @author Beckham Wilson
 */

public class Utils {
    /**
     * A method that checks if an integer is in an array
     * 
     * 
     * @param array
     * @param target
     * @return
     */
    public static boolean contains(int[] array, int target){
        for (int eachInt : array){
            if (eachInt == target){
                return true;
            }
        }
        return false;
    }
}
