package src;


public class Strip {
    private String stripStr;
    private Strip reversedStrip = null;

    public Strip(String stripStr){
        this.stripStr = stripStr;
    }


   
    @Override
    public boolean equals(Object o){
        if (o==null || getClass() != o.getClass())return false;
        Strip other = (Strip) o;
        return other.toString().equals(stripStr);
    }

    @Override
    public int hashCode(){
        return stripStr.hashCode();
    }
    public String toString(){
        return stripStr;
    }
    public int getMatches(Strip otherStrip){
        int matches = 0;
        String otherStripStr = otherStrip.toString();
        for (int colIdx = 0; colIdx < stripStr.length(); colIdx++) {
            if (otherStripStr.charAt(colIdx) == stripStr.charAt(colIdx)) {
                matches++;
            }
        }
        return matches;
    }

    public Strip reverse(){
        if (reversedStrip == null){
            reversedStrip = new Strip(new StringBuilder(stripStr).reverse().toString());
        }
        return reversedStrip;
    }


    public int getLength() {
        return stripStr.length(); // Return the length of the string
    }

    
    

}
