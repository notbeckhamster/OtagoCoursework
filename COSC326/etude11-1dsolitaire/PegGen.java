
import java.util.*;

/**
 * PegGen class represents a game where the goal is to remove pegs from a board
 * until only one peg remains.
 * This class provides methods to calculate and display the solvable states of
 * the game board.
 * 
 * 
 * @author Dyrel Lumiwes
 * @author Beckham Wilson
 */
public class PegGen {

    private static ArrayList<TreeSet<StringBuilder>> wins;

    /**
     * Main method to execute the PegGen game.
     * 
     * @param args Command-line arguments. The first argument should be the target
     *             number of pegs.
     */
    public static void main(String[] args) {
        int targetNum = 0;
        try {
            targetNum = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Usage java PegGen.App <targetNum>");
        }

        wins = new ArrayList<TreeSet<StringBuilder>>();
        calculateWins(targetNum);
   
       // System.out.println("The number of solvable states with " + targetNum + " pegs is : "
        //        + wins.get(targetNum - 1).size() + "\nLeading and trailing blanks are removed");

        for (StringBuilder t : wins.get(targetNum - 1)) {
            System.out.println(t);
        }
    }

    /**
     * Method to retrieve the winning states.
     * for the GUI class
     * 
     * @return The collection of winning states.
     */
    public static List<TreeSet<StringBuilder>> getWins() {
        return wins;
    }



    /**
     * Method to initialize the wins collection.
     * for the GUI class
     */
    public static void initWins() {
        wins = new ArrayList<TreeSet<StringBuilder>>();
    }

    /**
     * Calculates the winning states for the given target number of pegs.
     * 
     * @param targetNum The target number of pegs.
     */

    public static void calculateWins(int targetNum) {
        TreeSet<StringBuilder> pegwins = new TreeSet<StringBuilder>();
        pegwins.add(new StringBuilder("●")); // ● is peg ○ is blank
        wins.add(pegwins);

        for (int i = 1; i < targetNum; i++) {
            TreeSet<StringBuilder> currWin = new TreeSet<StringBuilder>();
            for (StringBuilder b : wins.get(i - 1)) {
                for (int j = 0; j < b.length(); j++) {
                    currWin.add(unhopLeft(b, j));
                    currWin.add(unhopRight(b, j));
                }
            }

            currWin = removeDuplicates(currWin, i + 1);
            wins.add(i, currWin);
            //System.out.println("States to hop from" + wins.get(i));

        }

    }

    /**
     * Moves a peg to the left, if possible.
     * 
     * @param curr  The current state of the board.
     * @param index The index of the peg to move.
     * @return The new state of the board after moving the peg to the left.
     */
    public static StringBuilder unhopLeft(StringBuilder curr, int index) {
        StringBuilder result = new StringBuilder(curr);
        if (index == 0) {
            // BASE
            result = new StringBuilder("●●○").append(result.substring(1));
        } else {

            if (result.charAt(index) == '●' && result.charAt(index - 1) == '○' && result.charAt(index - 2) == '○') {
                result = new StringBuilder(result.substring(0, index - 2));
                result.append("●●○");
                result.append(curr.substring(index + 1, curr.length()));

            } else {
                result = new StringBuilder(curr);
            }
        }
        result = cleanUpBlanks(result);

        return result;
    }

    /**
     * Moves a peg to the right, if possible.
     * 
     * @param curr  The current state of the board.
     * @param index The index of the peg to move.
     * @return The new state of the board after moving the peg to the right.
     */
    public static StringBuilder unhopRight(StringBuilder curr, int index) {
        StringBuilder result = new StringBuilder(curr);
        if (index == result.length() - 1) {
            result = new StringBuilder(result.substring(0, result.length() - 1)).append("○●●");
        } else {
            if (result.charAt(index) == '●' && result.charAt(index + 1) == '○' && result.charAt(index + 2) == '○') {
                result = new StringBuilder(result.substring(0, index));
                result.append("○●●");
                result.append(curr.substring(index + 3, curr.length()));

            } else {
                result = new StringBuilder(curr);
            }
        }
        result = cleanUpBlanks(result);

        return result;
    }

    /**
     * Removes duplicate board states and ensures that the number of pegs in each
     * state matches the specified count.
     * 
     * @param input The collection of board states.
     * @param pegs  The number of pegs each board state should have.
     * @return The collection of unique board states with the specified number of
     *         pegs.
     */
    public static TreeSet<StringBuilder> removeDuplicates(TreeSet<StringBuilder> input, int pegs) {
        TreeSet<StringBuilder> result = new TreeSet<StringBuilder>();
        for (StringBuilder b : input) {
            StringBuilder reverse = new StringBuilder(b);
            reverse.reverse();
            if (b.length() > 0 && !result.contains(reverse) && countPegs(b) == pegs) {
                result.add(b);
            }
        }
        return result;
    }

    /**
     * Counts the number of pegs in the given board state.
     * 
     * @param input The board state.
     * @return The number of pegs in the board state.
     */
    public static int countPegs(StringBuilder input) {
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '●') {
                result++;
            }
        }
        //
        //System.out.println(input + " has " + result + " pegs");
        //
        return result;
    }

    /**
     * Removes trailing and leading empty spaces from the board state.
     * 
     * @param input The board state to clean up.
     * @return The cleaned-up board state.
     */
    public static StringBuilder cleanUpBlanks(StringBuilder input) {
        int end = input.length() - 1;
        int start = 0;
        StringBuilder result;

        if (input.length() == 0) {
            return new StringBuilder();
        }
        while (input.charAt(start) == '○') {
            start++;
        }
        while (input.charAt(end) == '○') {
            end--;
        }
        result = new StringBuilder(input.substring(start, end + 1));

        return result;
    }
}