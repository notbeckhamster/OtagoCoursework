package src;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Class to parse anagrams
 * 
 * @group TwoBobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class AnagramParser {
    public static void main(String[] args){
        ArrayList<String> lines = Utils.readSysIn();
        System.out.println(process(lines));
    }

    public static String process(ArrayList<String> lines){
        //Parse input data
        ArrayList<String> anagramsToFind = new ArrayList<String>();
        ArrayList<String> anagramsToFindUnfiltered = new ArrayList<String>();
        HashSet<String> dictionary = new HashSet<String>();
        boolean isDictionary = false;
        for (String eachLine : lines){
            //Ignore empty line in the dictionary
            if (isDictionary == true && eachLine.length() > 0){
                dictionary.add(eachLine.toLowerCase().replaceAll("[^a-z]", ""));
            }else{
                if (eachLine.equals("")){
                    isDictionary = true;
                    continue;
                }
                anagramsToFindUnfiltered.add(eachLine);
                anagramsToFind.add(eachLine.toLowerCase().replaceAll("[^a-z]", ""));

            }
        }
       
        //Find best anagrams
        AnagramFinder anagramFinder = new AnagramFinder(new ArrayList<String>(dictionary), anagramsToFind);
        ArrayList<ArrayList<Word>> bestAnagrams = anagramFinder.getFirstAnagrams();
    

        //Convert bestAnagrams to string
        String result = "";
        for (int i = 0; i< anagramsToFind.size(); i++){
            result += anagramsToFindUnfiltered.get(i) + ": " + Utils.toStringSpaceDelmitted(bestAnagrams.get(i)) + "\n";
        }
        if (result.length() > 0){
            result = result.substring(0, result.length()-1);
        }   
        return result;
    }

}
