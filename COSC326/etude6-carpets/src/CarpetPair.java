package src;

import java.util.HashSet;

/**
 * Class to represent a pair of Strips in a Carpet
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class CarpetPair {
    public Strip strip1;
    public Strip strip2;
    public int index1;
    public int index2;
    private HashSet<Strip> setForHashing = new HashSet<Strip>();
    /**
     * Constructor for class
     * 
     */
    
    public CarpetPair(Strip strip1, Strip strip2) {
        this.strip1 = strip1;
        this.strip2 = strip2;
        setForHashing.add(strip1);
        setForHashing.add(strip2);
        setForHashing.add(new Strip(Utils.reverseString(strip1.toString())));
        setForHashing.add(new Strip(Utils.reverseString(strip2.toString())));

    }

    public CarpetPair(Strip strip1, Strip strip2, int index1, int index2){
        this(strip1, strip2);
        this.index1 = index1;
        this.index2 = index2;
    }


      
    @Override
    public boolean equals(Object o){
        if (o==null || getClass() != o.getClass())return false;
        CarpetPair other = (CarpetPair) o;
        //Uncommented since can't implement hashcode to account for reverses
        boolean isEqualSamePosition = (strip1.equals(other.strip1) && strip2.equals(other.strip2)) || ( strip1.reverse().equals(other.strip1.reverse()) && strip2.reverse().equals(other.strip2.reverse())) || ( strip1.reverse().equals(other.strip1) && strip2.reverse().equals(other.strip2)) ;
        boolean isEqualDifferntPosition = (strip1.equals(other.strip2) && strip2.equals(other.strip1)) || (strip1.reverse().equals(other.strip2.reverse()) && strip2.reverse().equals(other.strip1.reverse())) || (strip1.reverse().equals(other.strip2) && strip2.reverse().equals(other.strip1));
        return isEqualSamePosition || isEqualDifferntPosition;
    }

    @Override
    public int hashCode(){
        //Uses this since Set uses sum of hashcodes (we need sum of hashcode since we want to keep hashcode same for reversed strips)
        return setForHashing.hashCode();
    }

    @Override
    public String toString(){
        return strip1.toString() + " " + strip2.toString();
    }

   

}


 