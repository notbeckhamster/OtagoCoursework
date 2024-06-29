package src;

import java.util.*;

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
public class CarpetGen {
    private HashMap<Strip, Integer> stockCountHM = new HashMap<Strip, Integer>();
    public static HashMap<CarpetPair, Integer> carpetPairToMatches = new HashMap<CarpetPair, Integer>();
    public static HashMap<CarpetPair, Integer> carpetPairToMaxMatches = new HashMap<CarpetPair, Integer>();
    public static int stripLength = 0;
    private int carpetSize = 0;
    private HashSet<Carpet> seen;
    private int maxMatches;
    private Carpet maxMatchCarpet;
    private TreeMap<Strip, Integer> stockCountTM = new TreeMap<>(new Comparator<Strip>() {
        @Override
        public int compare(Strip strip1, Strip strip2) {
            int count1 = stockCountHM.get(strip1);
            int count2 = stockCountHM.get(strip2);
            if (count1 != count2) {
                return Integer.compare(count2, count1); // Sort by count in descending order
            } else {
                // If counts are equal, compare by strip objects
                return strip1.toString().compareTo(strip2.toString());
            }
        }
    });

    public CarpetGen(ArrayList<String> strips) {
        // Count how many of each piece we have
        stripLength = strips.get(0).toString().length();
        for (String eachStrip : strips) {
            Strip key = new Strip(eachStrip);
            stockCountHM.put(key, stockCountHM.getOrDefault(key, 0) + 1);

        }
        // Precompute pairs
        Set<Strip> setOfStrips = stockCountHM.keySet();
        Strip[] stripsArr = setOfStrips.toArray(new Strip[setOfStrips.size()]);
        for (int i = 0; i < stripsArr.length; i++) {
            for (int j = i; j < stripsArr.length; j++) {
                CarpetPair carpetPair = new CarpetPair(stripsArr[i], stripsArr[j]);
                if (!carpetPairToMatches.keySet().contains(carpetPair)) {
                    int matches = Utils.getNumberOfMatches(carpetPair.strip1, carpetPair.strip2);
                    carpetPairToMatches.put(carpetPair, matches);
                }
            }
        }
        // Precompute reversed pairs
        for (int i = 0; i < stripsArr.length; i++) {
            Strip reversedStrip = new Strip(Utils.reverseString(stripsArr[i].toString()));
            for (int j = i; j < stripsArr.length; j++) {
                CarpetPair carpetPair = new CarpetPair(reversedStrip, stripsArr[j]);
                if (!carpetPairToMatches.keySet().contains(carpetPair)) {
                    int matches = Utils.getNumberOfMatches(carpetPair.strip1, carpetPair.strip2);
                    carpetPairToMatches.put(carpetPair, matches);
                }
            }
        }
        //Precomputer max matches for pair accounting for reversal (optimisations for estimateMatches)
        stripsArr = setOfStrips.toArray(new Strip[setOfStrips.size()]);
        for (int i = 0; i < stripsArr.length; i++) {
            Strip reversedStrip = stripsArr[i].reverse();
            for (int j = i; j < stripsArr.length; j++) {
                CarpetPair carpetPair = new CarpetPair(stripsArr[i], stripsArr[j]);
                CarpetPair carpetPairReversed = new CarpetPair(reversedStrip, stripsArr[j]);
                carpetPairToMaxMatches.put(carpetPair, Math.max(carpetPairToMatches.get(carpetPair), carpetPairToMatches.get(carpetPairReversed)));
            }
        }
        stockCountTM.putAll(stockCountHM);
    }


    
    public Carpet getMaxMatches(int carpetSize) {
        this.carpetSize = carpetSize;
        // Add empty carpet so after greedy carpet backtracks it will explore different
        // starting strips
        @SuppressWarnings("unchecked")
        Carpet emptyCarpet = new Carpet(new ArrayList<Strip>(), (HashMap<Strip, Integer>) stockCountHM.clone());
        this.seen = new HashSet<>();
        seen.add(emptyCarpet);

        Carpet currentCarpet = emptyCarpet;
        // Greedy strat append strip that matches the last strip best
        while (currentCarpet.getUsedStrips().size() != carpetSize) {
            Carpet bestCarpet = null;
            int maxNumberOfMatches = 0;

            // Find best carpet in local sense (by considering appending a strip from stock)
            for (Carpet eachCarpet : currentCarpet.getNeighbours()) {
                int numOfMatches = eachCarpet.getNumberOfMatches();
                if (numOfMatches >= maxNumberOfMatches) {
                    bestCarpet = eachCarpet;
                    maxNumberOfMatches = numOfMatches;
                }
            }
            // Carpet size > number of strips in stock
            if (bestCarpet == null)
                return null;
            currentCarpet = bestCarpet;

        }

        this.maxMatches = currentCarpet.getNumberOfMatches();
        this.maxMatchCarpet = currentCarpet;

        // Recursive DFS + Pruning
        dfsForMax(emptyCarpet);
        return maxMatchCarpet;
    }

    public void dfsForMax(Carpet carpet){
        
            int carpetMatches = carpet.getNumberOfMatches();
            if (carpet.getUsedStrips().size() == carpetSize && carpetMatches > maxMatches) {
                maxMatches = carpetMatches;
                maxMatchCarpet = carpet;
            } else if (carpet.getUsedStrips().size() < carpetSize){
               // Pruning
                int estimatedMaxMatches = carpet.estimateRemainingMatches(carpetSize, carpetMatches);
                if (estimatedMaxMatches <= maxMatches) {
                return;
                }

            }else{
                return;
            }
            for (Carpet neighbour : carpet.getNeighbours()) {
                if (!seen.contains(neighbour)) {

                    seen.add(neighbour);
                    dfsForMax(neighbour);

                }
            }
        
    }

    public Carpet getNoMatches(int carpetSize) {
        this.carpetSize = carpetSize;
        @SuppressWarnings("unchecked")
        Carpet emptyCarpet =new Carpet(new ArrayList<Strip>(),(HashMap<Strip, Integer>) stockCountHM.clone());
        this.seen = new HashSet<>();
        seen.add(emptyCarpet);
        
        // Pure DFS
        return dfsForNoMatch(emptyCarpet);

    }

    public Carpet dfsForNoMatch(Carpet currentCarpet){
            int currentCarpetMatches = currentCarpet.getNumberOfMatches();
            if (currentCarpet.getUsedStrips().size() == carpetSize && currentCarpetMatches == 0){
                return currentCarpet;
            }
            for (Carpet neighbour : currentCarpet.getNoMatchNeighbours()) {
                if (!seen.contains(neighbour)) {

                    seen.add(neighbour);
                    Carpet foundCarpet =  dfsForNoMatch(neighbour);
                    if (foundCarpet != null){
                        return foundCarpet;
                    }

                }
            }
           return null;
            
        
    
}

    public Carpet generateRandomCarpet(int carpetSize) {
        ArrayList<Strip> randomStrips = new ArrayList<>();
        Random random = new Random();
        TreeMap<Strip, Integer> remainingStock = new TreeMap<>(stockCountTM); // Create a copy of the stock with counts

        for (int i = 0; i < carpetSize; i++) {
            if (remainingStock.isEmpty()) {
                break;
            }

            int randomIndex = random.nextInt(remainingStock.size());
            Strip randomStrip = (Strip) remainingStock.keySet().toArray()[randomIndex];
            randomStrips.add(randomStrip);
            Utils.decrementStock(remainingStock, randomStrip);
        }

        return new Carpet(randomStrips, new HashMap<>(remainingStock));
    }

    public Carpet getBalancedCarpet(int carpetSize) {

        Carpet randomCarpet = generateRandomCarpet(carpetSize);
        int bestPossibleBalancedScore = randomCarpet.bestPossibleBalancedScore();
        int bestScore = randomCarpet.calculateBalanceScore() - bestPossibleBalancedScore;
        HashSet<Carpet> seen = new HashSet<Carpet>();
        Carpet currentCarpet = randomCarpet;
        seen.add(currentCarpet);
        while (!currentCarpet.isBalanced()) {

            CarpetPair pairToChange = currentCarpet.findWorstPair();

            ArrayList<Carpet> nextCarpets = new ArrayList<Carpet>();

            // Find best strip to swap from stock
            for (Strip strip : currentCarpet.getStockHM().keySet()) {

                // non reversed
                Carpet carpetStockToIndex1 = currentCarpet.addStripToCarpet(pairToChange.index1, strip);
                nextCarpets.add(carpetStockToIndex1);
                Carpet carpetStockToIndex2 = currentCarpet.addStripToCarpet(pairToChange.index2, strip);
                nextCarpets.add(carpetStockToIndex2);

                // reversed
                Carpet carpetStockToIndex1Reversed = currentCarpet.addStripToCarpet(pairToChange.index1,
                        strip.reverse());
                nextCarpets.add(carpetStockToIndex1Reversed);
                Carpet carpetStockToIndex2Reversed = currentCarpet.addStripToCarpet(pairToChange.index2,
                        strip.reverse());
                nextCarpets.add(carpetStockToIndex2Reversed);

            }

            // Find best item to swap from existing carpet
            for (int idxToSwap = 0; idxToSwap < carpetSize; idxToSwap++) {

                // Non Reversed
                Carpet carpetSwap1 = currentCarpet.swapStripInCarpet(idxToSwap, pairToChange.index1);
                nextCarpets.add(carpetSwap1);

                Carpet carpetSwap2 = currentCarpet.swapStripInCarpet(idxToSwap, pairToChange.index2);
                nextCarpets.add(carpetSwap2);
                //Evaluate all possible options to improve worst pair
                for (Carpet nextCarpet : nextCarpets) {
                    int newCarpetScore = nextCarpet.calculateBalanceScore() -bestPossibleBalancedScore;
                    if (!seen.contains(nextCarpet) && newCarpetScore <= bestScore) {
                        currentCarpet = nextCarpet;
                        bestScore = newCarpetSco
                Carpet carpetSwapBothReversed = currentCarpet.swapStripInCarpetReverseBoth(idxToSwap,
                        pairToChange.index2);
                nextCarpets.add(carpetSwapBothReversed);

            }

            //Evaluate all possible options to improve worst pair
            for (Carpet nextCarpet : nextCarpets) {
                int newCarpetScore = nextCarpet.calculateBalanceScore() -bestPossibleBalancedScore;
                if (!seen.contains(nextCarpet) && newCarpetScore <= bestScore) {
                    currentCarpet = nextCarpet;
                    bestScore = newCarpetScore;
                }
            }
            if (seen.contains(currentCarpet)) {
                break;
            }
            seen.add(currentCarpet);
        }
        return currentCarpet;
    }

    public Strip findStripWithLargestCount() {
        Strip maxStrip = null;
        int maxCount = Integer.MIN_VALUE;

        for (Map.Entry<Strip, Integer> entry : stockCountTM.entrySet()) {
            Strip strip = entry.getKey();
            int count = entry.getValue();

            if (count > maxCount) {
                maxCount = count;
                maxStrip = strip;
            }
        }

        return maxStrip;
    }

}
