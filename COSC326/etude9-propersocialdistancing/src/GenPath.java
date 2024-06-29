package src;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;



/**
 * This class performs all the searching for best carpets according to the
 * selected option
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class GenPath {

    private int[] endPoint;
    private ArrayList<int[]> seatedPplArrLs;
    private int bestMinDist = Integer.MIN_VALUE;
    private int bestCaseMinDist;
    private int bestTotalMinDist = Integer.MIN_VALUE;
    private int[][] grid;
    private ArrayList<Integer> seatToBestCaseMaxDist = new ArrayList<Integer>();
    // Inclusive upperbound
    private ArrayList<Integer> seatToUpperBoundMinDist = new ArrayList<Integer>();

    public GenPath(int[] walkwaySize, ArrayList<int[]> seatedPplArrLs) {
        this.endPoint = new int[] { walkwaySize[0] - 1, walkwaySize[1] - 1 };
        this.seatedPplArrLs = seatedPplArrLs;
        setSeatMinDistToEdges();
        setMinDist();
        setSeatUpperBound();
    }

    private void setMinDist() {

        for (int i = bestCaseMinDist; i >= 0; i--) {
            ArrayList<Integer> seatsMinDist = new ArrayList<Integer>();
            for (int[] eachSeat : seatedPplArrLs) {
                seatsMinDist.add(i);
            }
            boolean pathExists = isPathPossible(seatsMinDist);
            if (pathExists == true) {
                bestTotalMinDist = i * seatedPplArrLs.size();
                bestMinDist = i;
                break;
            }
        }
    }

    private void setSeatUpperBound() {
        for (int i = 0; i < seatedPplArrLs.size(); i++) {
            seatToUpperBoundMinDist.add(seatToBestCaseMaxDist.get(i));
            for (int currSeatMinDist = bestMinDist; currSeatMinDist <= seatToBestCaseMaxDist
                    .get(i); currSeatMinDist++) {
                ArrayList<Integer> seatsMinDist = new ArrayList<Integer>();
                for (int[] eachSeat : seatedPplArrLs) {
                    seatsMinDist.add(bestMinDist);
                }
                seatsMinDist.set(i, currSeatMinDist);
                boolean pathExists = isPathPossible(seatsMinDist);
                if (pathExists == false) {
                    // inclusive upper bound hence -1...
                    seatToUpperBoundMinDist.set(i, currSeatMinDist - 1);
                    break;
                }
            }
        }
    }

    public void findBestPath() {

        ArrayDeque<IntList> queue = new ArrayDeque<IntList>();
        ArrayList<Integer> startingSeatsMinDist = new ArrayList<Integer>();
        for (int minDist : seatToUpperBoundMinDist) {
            startingSeatsMinDist.add(minDist);
        }

        HashSet<IntList> seen = new HashSet<IntList>();
        queue.add(new IntList(startingSeatsMinDist));
        
        while (!queue.isEmpty()) {

            ArrayList<Integer> seatsToMinDist = queue.poll().list;
            if (isPathPossible(seatsToMinDist) == true) {
                int totalMinDist = 0;
                for (Integer value : seatsToMinDist) {
                    totalMinDist += value;
                }
                bestTotalMinDist = totalMinDist;
                break;
            }

            for (int i = 0; i < startingSeatsMinDist.size(); i++) {
                @SuppressWarnings("unchecked")
                ArrayList<Integer> nextSeatsMinDist = (ArrayList<Integer>) seatsToMinDist.clone();
                if (nextSeatsMinDist.get(i) - 1 >= bestMinDist) {

                    nextSeatsMinDist.set(i, nextSeatsMinDist.get(i) - 1);
                    IntList intList = new IntList(nextSeatsMinDist);
                    if (!seen.contains(intList)) {
                        queue.add(intList);
                        seen.add(intList);
                    }
                }
            }
        }
       

    }

    private boolean isPathPossible(ArrayList<Integer> seatsMinDist) {
        grid = new int[endPoint[0] + 1][endPoint[1] + 1];
        precomputeBoundaries(grid, seatsMinDist);
        floodFill(grid, seatsMinDist);
        return grid[endPoint[0]][endPoint[1]] == 1;

    }

    private void precomputeBoundaries(int[][] grid, ArrayList<Integer> seatsMinDist) {
        for (int i = 0; i < seatedPplArrLs.size(); i++) {
            int[] seat = seatedPplArrLs.get(i);
            int minDist = seatsMinDist.get(i) - 1;
            int lx = seat[1];
            int rx = seat[1];
            for (int y = seat[0] - minDist; y <= seat[0]; y++) {
                if (y >= 0 && y <= endPoint[0]) {

                    if (lx >= 0 && lx <= endPoint[1]) {
                        grid[y][lx] = -1;
                    }
                    if (rx >= 0 && rx <= endPoint[1]) {
                        grid[y][rx] = -1;
                    }
                }
                lx--;
                rx++;

            }

            lx = seat[1];
            rx = seat[1];
            for (int y = seat[0] + minDist; y > seat[0]; y--) {
                if (y >= 0 && y <= endPoint[0]) {
                    if (lx >= 0 && lx <= endPoint[1]) {
                        grid[y][lx] = -1;
                    }
                    if (rx >= 0 && rx <= endPoint[1]) {
                        grid[y][rx] = -1;
                    }
                }
                lx--;
                rx++;
            }
        }
    }

    private void floodFill(int[][] grid, ArrayList<Integer> seatsMinDist) {

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[] { 0, 0 });

        while (!stack.isEmpty()) {
            int[] currCoords = stack.pop();
            int y = currCoords[0];
            int x = currCoords[1];
            if (y < 0 || x < 0 || y > endPoint[0] || x > endPoint[1]) {
                continue;
            }

            if (grid[y][x] == 1 || grid[y][x] == -1) {
                continue;
            }

            grid[y][x] = 1;

            if (y == endPoint[0] && x == endPoint[1]) {
                // for (int[] eachIntArr : grid){
                // System.out.println(Arrays.toString(eachIntArr));
                // }
                return;
            }

            stack.push(new int[] { y + 1, x });
            stack.push(new int[] { y, x + 1 });
            stack.push(new int[] { y - 1, x });
            stack.push(new int[] { y, x - 1 });

        }

    }

    public int[] getMinAndTotalDistance() {
        return new int[] { bestMinDist, bestTotalMinDist };
    }

    private void setSeatMinDistToEdges() {
        // Max Distance between left and top edge
        int minDist = Integer.MAX_VALUE;
        for (int[] eachSeat : seatedPplArrLs) {
            int currentMinDist = Math.max(eachSeat[0], eachSeat[1]);
            if (currentMinDist < minDist) {
                minDist = currentMinDist;
            }
            seatToBestCaseMaxDist.add(currentMinDist);

        }

        // Max Distance between right and bottom edge
        for (int i = 0; i < seatedPplArrLs.size(); i++) {
            int[] eachSeat = seatedPplArrLs.get(i);
            int currentMinDist = Math.max(endPoint[1] - eachSeat[1], endPoint[0] - eachSeat[0]);
            if (currentMinDist < minDist) {
                minDist = currentMinDist;
            }
            if (currentMinDist < seatToBestCaseMaxDist.get(i)) {
                seatToBestCaseMaxDist.set(i, currentMinDist);
            }
        }

        bestCaseMinDist = minDist;

    }

}