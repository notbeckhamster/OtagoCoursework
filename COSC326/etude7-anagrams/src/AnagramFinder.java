package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;


/**
 * Class to find the best anagram
 * 
 * @group TwoBobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class AnagramFinder {
    private ArrayList<Word> dictionary = new ArrayList<Word>();
    private ArrayList<String> anagramsToFind = new ArrayList<String>();

    public AnagramFinder(ArrayList<String> dictionary, ArrayList<String> anagramsToFind) {
        for (String eachStr : dictionary) {
            eachStr = eachStr.trim();
            this.dictionary.add(new Word(eachStr.toLowerCase().replaceAll("[^a-z]", "")));
        }
        Collections.sort(this.dictionary, Collections.reverseOrder());

        this.anagramsToFind = anagramsToFind;

    }

    public ArrayList<ArrayList<Word>> getFirstAnagrams() {
        ArrayList<ArrayList<Word>> result = new ArrayList<ArrayList<Word>>();
        int resultSizeIfFound = 0;

        for (String eachAnagramToFind : anagramsToFind) {
            resultSizeIfFound++;
            HashSet<Anagram> seen = new HashSet<Anagram>();
            Anagram emptyAnagram = new Anagram(eachAnagramToFind, new ArrayList<Word>());
            seen.add(emptyAnagram);
            recDFT(emptyAnagram, seen, result, resultSizeIfFound, eachAnagramToFind, 0);
            if (result.size() != resultSizeIfFound) {
                result.add(new ArrayList<>());
            }
        }
        return result;
    }





    private void recDFT(Anagram currentAnagram, HashSet<Anagram> seen, ArrayList<ArrayList<Word>> result,
            int resultSizeIfFound, String startingAnagram, int startingIdx) {

        int[] currentAnagramRemainingChars = currentAnagram.remainingCharsCount;
        ArrayList<Word> currentWords = currentAnagram.currentWords;

        if (currentAnagram.isValidAnagram()) {
            result.add(currentAnagram.currentWords);
        }
        if (result.size() == resultSizeIfFound) {
            return;
        }

        for (int i = startingIdx; i < dictionary.size(); i++) {
            Word eachDictWord = dictionary.get(i);
            Anagram nextAnagram = new Anagram(startingAnagram, (ArrayList<Word>) currentWords.clone());
            nextAnagram.addWord(eachDictWord);
            if (!seen.contains(nextAnagram) && eachDictWord.isWordIn(currentAnagramRemainingChars)) {
                seen.add(nextAnagram);
                recDFT(nextAnagram, seen, result, resultSizeIfFound, startingAnagram, i);
            }
            if (result.size() == resultSizeIfFound) {
                return;
            }
        }
  

    }


    public String toStringDictionary() {
        return dictionary.toString();
    }
}
