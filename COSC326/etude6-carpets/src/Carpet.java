package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * Class to represent a carpet
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class Carpet {
    private ArrayList<Strip> strips = new ArrayList<Strip>();
    private HashMap<Strip, Integer> stockHM = new HashMap<Strip, Integer>();
    private TreeMap<Strip, Integer> stockTM = new TreeMap<Strip, Integer>(new Comparator<Strip>() {
        @Override
        public int compare(Strip strip1, Strip strip2) {
            int count1 = stockHM.get(strip1);
            int count2 = stockHM.get(strip2);
            if (count1 != count2) {
                return Integer.compare(count2, count1); // Sort by count in descending order
            } else {
                // If counts are equal, compare by strip objects
                return strip1.toString().compareTo(strip2.toString());
            }
        }
    });

    public Carpet(ArrayList<Strip> strips, HashMap<Strip, Integer> stockHM) {
        this.strips = strips;
        this.stockHM.putAll(stockHM);
        this.stockTM.putAll(stockHM);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Carpet otherCarpet = (Carpet) o;
        return strips.equals(otherCarpet.getUsedStrips());
    }

    @Override
    public int hashCode() {
        return strips.hashCode();
    }

    public static ArrayList<String> toStringArrLs(ArrayList<Strip> stripArrLs) {
        ArrayList<String> strArrLs = new ArrayList<String>();
        for (Strip eachStrip : stripArrLs) {
            strArrLs.add(eachStrip.toString());
        }
        return strArrLs;
    }

    public ArrayList<Strip> getUsedStrips() {
        return strips;
    }

    public TreeMap<Strip, Integer> getStockTM() {
        return stockTM;
    }

    public HashMap<Strip, Integer> getStockHM() {
        return stockHM;
    }


    public int getNumberOfMatches() {
        int matches = 0;
        if (strips.size() == 0)
            return 0;
        Strip lastStrip = strips.get(0);
        for (int stripIdx = 1; stripIdx < strips.size(); stripIdx++) {
            Strip currStrip = strips.get(stripIdx);
            matches += CarpetGen.carpetPairToMatches.get(new CarpetPair(currStrip, lastStrip));
            lastStrip = currStrip;
        }
        return matches;
    }

    public List<Carpet> getNoMatchNeighbours() {
        LinkedHashSet<Carpet> neighbours = new LinkedHashSet<>();
        if (stockTM == null || strips == null)
            return new ArrayList<Carpet>();
        // Returns all neightbors carpets by appending strips from stock
        for (Strip stripToAppend : stockTM.keySet()) {
            addIfNoMatchWithLastStrip(stripToAppend, neighbours);
            addIfNoMatchWithLastStrip(stripToAppend.reverse(), neighbours);
        }

        List<Carpet> sortedNeighbours = new ArrayList<Carpet>(neighbours);

        Comparator<Carpet> comparatorNumberOfNonMatches = (carpet1, carpet2) -> {
            Integer numOfNoMatches1 = carpet1.getNumberOfNoMatchesWithStock();
            Integer numOfNoMatches2 = carpet2.getNumberOfNoMatchesWithStock();
            return numOfNoMatches1.compareTo(numOfNoMatches2); // Ascending order 1->2->3
        };
        sortedNeighbours.sort(comparatorNumberOfNonMatches);
        return sortedNeighbours;
    }

    private int getNumberOfNoMatchesWithStock() {
        int numOfNoMatchesWithStock = 0;
        Strip lastStrip = strips.get(strips.size() - 1);
        for (Strip stripFromStock : stockTM.keySet()) {
            if (CarpetGen.carpetPairToMatches.get(new CarpetPair(lastStrip, stripFromStock)) == 0) {
                numOfNoMatchesWithStock++;
            }
            if (CarpetGen.carpetPairToMatches.get(new CarpetPair(lastStrip, stripFromStock.reverse())) == 0) {
                numOfNoMatchesWithStock++;
            }
        }
        return numOfNoMatchesWithStock;
    }

    private void addIfNoMatchWithLastStrip(
            Strip stripToAppend, LinkedHashSet<Carpet> neighbours) {
        int matchesLastStripAndStripToAppend;
        if (strips.size() == 0) {
            matchesLastStripAndStripToAppend = 0;
        } else {
            Strip lastStrip = strips.get(strips.size() - 1);
            matchesLastStripAndStripToAppend = CarpetGen.carpetPairToMatches
                    .get(new CarpetPair(lastStrip, stripToAppend));
        }

        if (matchesLastStripAndStripToAppend == 0) {
            @SuppressWarnings("unchecked")
            ArrayList<Strip> newStrips = (ArrayList<Strip>) strips.clone();
            newStrips.add(stripToAppend);
            @SuppressWarnings("unchecked")
            HashMap<Strip, Integer> copyOfStock = (HashMap<Strip, Integer>) stockHM.clone();
            Utils.decrementStock(copyOfStock, stripToAppend);

            Carpet newCarpet = new Carpet(newStrips, copyOfStock);
            neighbours.add(newCarpet);

        }
    }

    private void addNeighbourToList(
            Strip stripToAppend, LinkedHashSet<Carpet> neighbours) {

        @SuppressWarnings("unchecked")
        ArrayList<Strip> newStrips = (ArrayList<Strip>) strips.clone();
        newStrips.add(stripToAppend);
        @SuppressWarnings("unchecked")
        HashMap<Strip, Integer> copyOfStock = (HashMap<Strip, Integer>) stockHM.clone();
        Utils.decrementStock(copyOfStock, stripToAppend);

        Carpet newCarpet = new Carpet(newStrips, copyOfStock);
        neighbours.add(newCarpet);

    }

    public LinkedHashSet<Carpet> getNeighbours() {
        LinkedHashSet<Carpet> neighbours = new LinkedHashSet<>();
        if (stockHM == null || strips == null)
            return neighbours;
        // Returns all neightbors carpets by appending strips from stock
        for (Strip eachStrip : stockTM.keySet()) {

            addNeighbourToList(eachStrip, neighbours);
            addNeighbourToList(eachStrip.reverse(), neighbours);

        }

        return neighbours;
    }

    @SuppressWarnings("unchecked")
    public Carpet getNeighbourMatchesLastStripBest() {
        if (stockHM == null || strips == null)
            return this;

        // Use the first element of stock since it has highest number of same strip
        if (strips.size() == 0) {
            HashMap<Strip, Integer> copyOfStock = (HashMap<Strip, Integer>) stockHM.clone();
            Strip stripWithHighestStockCount = stockTM.firstKey();
            Utils.decrementStock(copyOfStock, stripWithHighestStockCount);

            ArrayList<Strip> newStrips = (ArrayList<Strip>) strips.clone();
            newStrips.add(stripWithHighestStockCount);
            Carpet newCarpet = new Carpet(newStrips, copyOfStock);
            return newCarpet;
        }
        int maxNumberOfMatches = 0;
        Carpet maxCarpet = this;
        Strip lastStrip = strips.get(strips.size() - 1);

        for (Strip stripToAppend : stockTM.keySet()) {

            HashMap<Strip, Integer> copyOfStock = (HashMap<Strip, Integer>) stockHM.clone();
            Utils.decrementStock(copyOfStock, stripToAppend);

            ArrayList<Strip> newStrips = (ArrayList<Strip>) strips.clone();
            newStrips.add(stripToAppend);
            Carpet carpet = new Carpet(newStrips, copyOfStock);
            int carpetNumberOfMatches = CarpetGen.carpetPairToMatches.get(new CarpetPair(lastStrip, stripToAppend));
            if (carpetNumberOfMatches > maxNumberOfMatches) {
                maxCarpet = carpet;
                maxNumberOfMatches = carpetNumberOfMatches;
                if (carpetNumberOfMatches == CarpetGen.stripLength) {
                    return carpet;
                }
            }

            String reverseStringToAppend = Utils.reverseString(stripToAppend.toString());
            Strip reverseStripToAppend = new Strip(reverseStringToAppend);

            ArrayList<Strip> stripswithReverse = (ArrayList<Strip>) strips.clone();
            stripswithReverse.add(reverseStripToAppend);
            Carpet carpetwithReverse = new Carpet(stripswithReverse, copyOfStock);
            int reverseCarpetNumberOfMatches = CarpetGen.carpetPairToMatches.get(new CarpetPair(lastStrip, reverseStripToAppend));
            if (reverseCarpetNumberOfMatches > maxNumberOfMatches) {
                maxCarpet = carpetwithReverse;
                maxNumberOfMatches = reverseCarpetNumberOfMatches;
                if (reverseCarpetNumberOfMatches == CarpetGen.stripLength) {
                    return carpetwithReverse;
                }
            }

        }

        return maxCarpet;
    }

    public boolean isBalanced() {
        return calculateBalanceScore() == bestPossibleBalancedScore();

    }

    public int bestPossibleBalancedScore() {
        int numberOfSeams = (strips.size() - 1) * CarpetGen.stripLength;
        int bestPossibleBalancedScore = numberOfSeams % 2;
        return bestPossibleBalancedScore;
    }

    public int calculateBalanceScore() {
        if (strips.size() == 0)
            return Integer.MAX_VALUE;
        int stripLength = CarpetGen.stripLength;
        int matches = getNumberOfMatches();
        int nonMatches = (strips.size() - 1) * stripLength - matches;
        return Math.abs(matches - nonMatches);
    }



    public String toString() {
        return "Strips: " + strips.toString() + "\t StripFromStock: " + stockTM + "\n";
    }

    public String formattedToString() {
        ArrayList<String> stripsAsStr = Carpet.toStringArrLs(strips);
        return String.join("\n", stripsAsStr) + "\n" + getNumberOfMatches();
    }

    public String toStringBalanced() {
        ArrayList<String> stripsAsStr = Carpet.toStringArrLs(strips);
        return String.join("\n", stripsAsStr) + "\n" + calculateBalanceScore();
    }

    public Carpet swapStripInCarpet(int indexToSwap1, int indexToSwap2) {
        @SuppressWarnings("unchecked")
        ArrayList<Strip> stripsArrLs = (ArrayList<Strip>) strips.clone();
        @SuppressWarnings("unchecked")
        HashMap<Strip, Integer> stockCopy = (HashMap<Strip, Integer>) stockHM.clone();
        Collections.swap(stripsArrLs, indexToSwap1, indexToSwap2);
        return new Carpet(stripsArrLs, stockCopy);
    }

    public Carpet swapStripInCarpetReverse(int indexToSwap1, int indexToSwap2) {
        @SuppressWarnings("unchecked")
        ArrayList<Strip> stripsArrLs = (ArrayList<Strip>) strips.clone();
        @SuppressWarnings("unchecked")
        HashMap<Strip, Integer> stockCopy = (HashMap<Strip, Integer>) stockHM.clone();
        stripsArrLs.set(indexToSwap1, stripsArrLs.get(indexToSwap1).reverse());
        Collections.swap(stripsArrLs, indexToSwap1, indexToSwap2);
        return new Carpet(stripsArrLs, stockCopy);
    }

    public Carpet swapStripInCarpetReverseBoth(int indexToSwap1, int indexToSwap2) {
        @SuppressWarnings("unchecked")
        ArrayList<Strip> stripsCopy = (ArrayList<Strip>) strips.clone();
        @SuppressWarnings("unchecked")
        HashMap<Strip, Integer> stockCopy = (HashMap<Strip, Integer>) stockHM.clone();
        stripsCopy.set(indexToSwap1, stripsCopy.get(indexToSwap1).reverse());
        stripsCopy.set(indexToSwap2, stripsCopy.get(indexToSwap2).reverse());
        Collections.swap(stripsCopy, indexToSwap1, indexToSwap2);
        return new Carpet(stripsCopy, stockCopy);
    }

    public Carpet addStripToCarpet(int index, Strip newStrip) {
        @SuppressWarnings("unchecked")
        ArrayList<Strip> stripsCopy = (ArrayList<Strip>) strips.clone();
        @SuppressWarnings("unchecked")
        HashMap<Strip, Integer> stockCopy = (HashMap<Strip, Integer>) stockHM.clone();
        stripsCopy.set(index, newStrip);
        return new Carpet(stripsCopy, stockCopy);
    }

    public CarpetPair findWorstPair() {
        int totalMatches = getNumberOfMatches();
        int totalNonMatches = strips.size() - totalMatches - CarpetGen.stripLength;
        boolean tooManyMatches = totalMatches > totalNonMatches;

        CarpetPair bestPair = null;

        int bestScore=0;
        int stripLength = CarpetGen.stripLength;
        ArrayList<Strip> usedStrips = strips;
        for (int i = 0; i < usedStrips.size()-1; i++) {
                int j = i+1;
                Strip strip1 = usedStrips.get(i);
                Strip strip2 = usedStrips.get(j);
                CarpetPair pair = new CarpetPair(strip1, strip2, i, j);

             
                int numOfMatches = CarpetGen.carpetPairToMatches.get(pair);
                int numOfNonMatches = stripLength - numOfMatches;

                if ((tooManyMatches && numOfMatches > bestScore)) {

                    bestPair = pair;
                    bestScore = numOfMatches;
                } else if (!tooManyMatches && numOfNonMatches > bestScore) {
                    bestPair = pair;
                    bestScore = numOfNonMatches;
                }
            
        }

        return bestPair;
    }

    public int estimateRemainingMatches(int maxSize,
            int currentNumberOfMatches) {
  
        // Calculate the maximum number of matches each strip can acheive in stock and
        // with currentCarpet last strip
        int carpetSize = strips.size();

        Strip lastStrip = null;
        if (carpetSize > 0) {
            lastStrip = strips.get(strips.size() - 1);
        }

        Map<Strip, Integer> stripToMaxMatches = new HashMap<Strip, Integer>();
        for (Strip strip1 : stockHM.keySet()) {
            int maxNumberOfMatches = 0;
            for (Strip strip2 : stockHM.keySet()) {
                if (strip1.equals(strip2)) {
                    continue;
                }
                int numOfMatches = CarpetGen.carpetPairToMaxMatches.get(new CarpetPair(strip1, strip2));
                if (numOfMatches > maxNumberOfMatches) {
                    maxNumberOfMatches = numOfMatches;
                }
                
            }
            if (carpetSize > 0) {
                int numOfMatches = Utils.getStock(CarpetGen.carpetPairToMaxMatches, new CarpetPair(strip1, lastStrip));
                if (numOfMatches > maxNumberOfMatches) {
                    maxNumberOfMatches = numOfMatches;
                }
            }

            

            stripToMaxMatches.put(strip1, maxNumberOfMatches);
        }

        List<Map.Entry<Strip, Integer>> stripToMaxMatchList = new ArrayList<>(stripToMaxMatches.entrySet());

        Collections.sort(stripToMaxMatchList, new Comparator<Map.Entry<Strip, Integer>>() {
            @Override
            public int compare(Map.Entry<Strip, Integer> o1, Map.Entry<Strip, Integer> o2) {
                // Sort by values in descending order
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        int remainingSize = maxSize - carpetSize;
        int estimatedPossibleMatches = currentNumberOfMatches;
        for (Map.Entry<Strip, Integer> entry : stripToMaxMatchList) {
            Strip strip = entry.getKey();
            int stripMaxMatches = entry.getValue();
            int countOfStrips = stockTM.get(strip);
            for (int i = 0; i < countOfStrips; i++) {
                if (remainingSize <= 0) {
                    break;
                }
                remainingSize--;
                estimatedPossibleMatches += stripMaxMatches;

            }
            if (remainingSize <= 0) {
                break;
            }

        }
        return estimatedPossibleMatches;

    }

}
