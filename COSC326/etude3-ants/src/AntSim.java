package src;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.*;



/**
 * This class simulates the movements of an ant on a grid
 * 
 * 
 * 
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class AntSim {
    private StringBuilder sb = new StringBuilder();

    private HashMap<Character, char[][]> stateToDNA = new HashMap<Character, char[][]>();

    //coordsToState is a hashmap that stores the state of each square
    private Map<Coordinate, Character> cordsToState = new HashMap<Coordinate, Character>();
    private HashMap<Character, Integer> directionToIndex = new HashMap<Character, Integer>();
    private boolean isFirstDNALine = true;
    private char defaultSquareState = 'd';
    //currentCordsWrapper is a wrapper for the current coordinates of the ant
    private Coordinate currentCordsWrapper = new Coordinate(0,0);
    private ArrayList<Character> antHistory = new ArrayList<Character>();


    

    private Character lastDirection = 'N';
    public AntSim() {

        directionToIndex.put('N', 0);
        directionToIndex.put('E', 1);
        directionToIndex.put('S', 2);
        directionToIndex.put('W', 3);
    }

    /**
     * This method processes the input lines and simulates the ant's movements
     * It first checks if the line is a comment or empty line and skips it
     * Then it checks if the line is an integer and runs the simulation
     * Else, it adds the DNA line to the stateToDNA hashmap
     * 
     * @param lines
     * @return the output of the ant simulation into sysout
     */
    public String process(ArrayList<String> lines) {
        int linesSize = lines.size();

        for (int i = 0; i < linesSize; i++) {
            String eachLine = lines.get(i);
            if (eachLine.length() > 0 && eachLine.charAt(0) == '#') {
                continue;
            } else if (eachLine.isEmpty()) {
                // Reset
                stateToDNA.clear();
                cordsToState.clear();
                isFirstDNALine = true;
                currentCordsWrapper = new Coordinate(0,0);
                lastDirection = 'N';
                if (i != lines.size() - 1) {
                    sb.append("\n");
                }

            } else if (Utils.isLong(eachLine)) {
                // Run simulation of the scenario
                long numOfSteps = Long.parseLong(eachLine);
                Coordinate endCords = simulateMoves(numOfSteps);
                sb.append(eachLine + "\n");
                sb.append("# " + endCords.x + " " + endCords.y + "\n");

            } else {

                parseDnaLine(eachLine);
                sb.append(eachLine + "\n");
            }
        }
        String result = sb.toString();
        return result.substring(0,result.length()-1);
    }
    /**
     * Simulates the moves of the ant for a given number of iterations
     * 
     * @param numOfIterations
     * @return the final coordinates of the ant
     */
    public Coordinate simulateMoves(long numOfIterations) {

        for (long stepIdx = 1; stepIdx <= numOfIterations; stepIdx++) {
      
            Character currentState = cordsToState.getOrDefault(currentCordsWrapper, defaultSquareState);
            char[][] dna = stateToDNA.get(currentState);
            char[] nextDirections = dna[0];
            char[] newTileStates = dna[1];

            int lastDirectionIndex = directionToIndex.get(lastDirection);
            Character nextDirection = nextDirections[lastDirectionIndex];


            
            // Update the tile state of the previous square
            Character newTileState = newTileStates[lastDirectionIndex];
            cordsToState.put(currentCordsWrapper, newTileState);       
            // Update the tile state of the new square
            Coordinate newCordsWrapper = currentCordsWrapper.move(nextDirection);
            cordsToState.put(newCordsWrapper, cordsToState.getOrDefault(newCordsWrapper, defaultSquareState));
            // //Update for new iteration
            currentCordsWrapper = newCordsWrapper;
            lastDirection = nextDirection;


            antHistory.add(nextDirection);
            if (stepIdx == 6000000){
                if (isPattern(numOfIterations-stepIdx) == true){
                    break;
                }
            }




        }

    return currentCordsWrapper;

    }


    public boolean isPattern(long stepsRemaining){
        List<Character> lastChars =  antHistory.subList(antHistory.size() -5000000, antHistory.size());

        for (int i = 10; i < 500; i++){
           
         
                                
                List<Character>  basePattern =  lastChars.subList(lastChars.size()-i, lastChars.size());
                List<Character>  comparsionPattern =  lastChars.subList(lastChars.size()-i*2, lastChars.size()-i);

                if (basePattern.equals(comparsionPattern)){
                    //Check this pattern continues on for the window size
                    boolean isPattern = true;
                    for (int j = 2; j<5000000/i -i ; j++){
                        comparsionPattern =  lastChars.subList(lastChars.size()-j*i-i, lastChars.size()-j*i);
                        if (comparsionPattern.equals(basePattern) == false){
                            isPattern = false;
                            break;
                        }
                    }
                    if (isPattern == false){
                        break;
                    }
                    long[] movement = getPatternMovement(basePattern, i);
                    long times = stepsRemaining / i;
      
                    currentCordsWrapper.x += (long) movement[0] * times;
                    currentCordsWrapper.y +=(long)movement[1] * times;

                    movement = getPatternMovement(basePattern,(long) stepsRemaining%i);

                    currentCordsWrapper.x += (long) movement[0] ;
                    currentCordsWrapper.y +=(long)movement[1];

                    return true;
                }

          
        }
        return false;
    }

    public static long[] getPatternMovement(List<Character> subset, long size){
        long[] movement = {0, 0};

        for (long i = 0; i < size; i++){
            char direction = subset.get((int)i);
            switch (direction){
                case 'N':
                movement[1] = movement[1] + 1;
                    break;
                case 'S':
                movement[1] = movement[1] - 1;
                    break;
                case 'E':
                movement[0] = movement[0] + 1;
                    break;
                case 'W':
                movement[0] = movement[0] - 1;
                    break;
            }
        }
        return movement;
    }

    /**
     * Classifies each line into the stateToDNA hashmap.
     * First checks if the line is the first DNA line and sets the defaultSquareState
     * Else, adds the DNA line to the stateToDNA hashmap
     * 
     * @param line
     */
    public void parseDnaLine(String line) {
        // add dna line to hashmap
        char firstChar = line.charAt(0);
        if (isFirstDNALine) {
            isFirstDNALine = false;
            defaultSquareState = firstChar;
            cordsToState.put(new Coordinate(0,0), firstChar);
        }
        stateToDNA.put(firstChar,
                new char[][] { line.substring(2, 2 + 4).toCharArray(), line.substring(7, 7 + 4).toCharArray() });
    }

}