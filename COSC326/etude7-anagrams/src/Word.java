package src;


/**
 * Word class for use in hashing and sorting
 * 
 * @group TwoBobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class Word implements Comparable<Word>{
    String str;
    int[] charCount = new int[26];

    public Word(String str){
        this.str = str;
        for (int i = 0; i<str.length(); i++){
            char currentChar = str.charAt(i);
            int index = currentChar - 'a';
            if (index < 26 && index >= 0){
                charCount[currentChar - 'a']++;
            }
        }
    }
    @Override
    public int compareTo(Word otherWord){
        String otherStr = otherWord.str;
        if (str.length() > otherStr.length()){
            return 1;
        } else if (str.length() < otherStr.length()){
            return -1;
        } else{
            return (-1)*str.compareTo(otherStr);
        }
    }


    public String toString(){
        return str;
    }
    public int getLength(){
        return str.length();
    }

    public boolean isWordIn(int[] remainingCharsCount){
        for (int i = 0; i<charCount.length; i++){
            if (remainingCharsCount[i] < 0 || charCount[i] > remainingCharsCount[i]){
                return false;
            }
        }
        return true;
        }
    

        @Override
        public boolean equals(Object o){
            if (o==null || getClass() != o.getClass())return false;
            Word other = (Word) o;
            return str.equals(other.str);
        }

        @Override
        public int hashCode(){
            return str.hashCode();
        }
    
    
}
