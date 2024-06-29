package src;

import java.util.ArrayList;


public class Anagram implements Comparable<Anagram>{
    /** Array to store the count of remaining characters available for forming words. */
    public int[] remainingCharsCount = new int[26];

    // List of words currently forming the anagram.
    public ArrayList<Word> currentWords = new ArrayList<Word>();

     /**
     * Constructs an Anagram object with the given anagram string and list of current words.
     *
     * @param anagramToFind The string representing the anagram to find.
     * @param currentWords  The list of current words forming the anagram.
     */
    public Anagram(String anagramToFind, ArrayList<Word> currentWords){
      // Initialize remainingCharsCount based on the characters in anagramToFind
        for (int i = 0; i<anagramToFind.length(); i++){
            char currentChar = anagramToFind.charAt(i);
            int index = currentChar - 'a';
            if (index < 26 && index >= 0){
            remainingCharsCount[currentChar - 'a']++;
            }
        }

        // Add each word in currentWords to the anagram
  
        for (Word eachWord : currentWords){
            addWord(eachWord);
        }


    }  

   /**
     * Adds a word to the current list of words 
     * forming the anagram and updates remainingCharsCount.
     * @param wordToAdd The word to add to the anagram.
     */ 
    public void addWord(Word wordToAdd){
        currentWords.add(wordToAdd);
        String wordStr = wordToAdd.str;
        // Update remainingCharsCount based on the characters in wordToAdd
        for (int i = 0; i<wordStr.length(); i++){
            char currentChar = wordStr.charAt(i);
            int index = currentChar - 'a';
            if (index < 26 && index >= 0){
            remainingCharsCount[currentChar - 'a']--;
            }
        }
    }

    
    public int compareTo(Anagram otherAnagram){
        //Check which anagram is more complete
        int countRemainingChars = getNumOfRemainingChars();
        int countRemainingCharsOther = otherAnagram.getNumOfRemainingChars();
        if (countRemainingChars < countRemainingCharsOther){
            return -1;
        } else if (countRemainingCharsOther < countRemainingChars){
            return 1;
        } else{
            //If same length then check each each in anagram
            for (int i = 0; i< currentWords.size(); i++){
                Word word = currentWords.get(i);
                Word otherWord = otherAnagram.currentWords.get(i);
                int result = word.compareTo(otherWord);
                if (result != 0) return result;

            }
        }
        return 0;
    }

    /**
     * Calculates the total number of remaining characters available for forming words in the anagram.
     *
     * @return The total number of remaining characters.
     */
    public int getNumOfRemainingChars(){
        int count =0 ; 
        for (int i = 0; i<remainingCharsCount.length; i++){
            count += remainingCharsCount[i];
        }
        return count;

    }



    @Override
    public boolean equals(Object o){
        if (o==null || getClass() != o.getClass())return false;
        Anagram other = (Anagram) o;
        return currentWords.equals(other.currentWords);
    }

    @Override
    public int hashCode(){
        return currentWords.hashCode();
    }

    public String toString(){
        return currentWords.toString();
    }

    public boolean isValidAnagram(){
        return getNumOfRemainingChars() == 0;
    }   


}