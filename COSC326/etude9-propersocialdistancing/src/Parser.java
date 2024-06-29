package src;


import java.util.ArrayList;

/**
 * This class handles the I/O of the program
 * 
 * @group BobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 * @author Raaid Taha
 * @author Kevin Albert
 */
public class Parser {
     public static void main(String[] args){
        ArrayList<String> lines = Utils.readSysIn();
        System.out.println(process(lines));
    }

    public static String process(ArrayList<String> lines){
        //Parse input data
        String output = "";

        int[] walkwaySize = new int[]{0,0};
        ArrayList<int[]> seatedPplArrLs = new ArrayList<int[]>();
        boolean isWalkwaySize = true;
        for (String eachLine : lines){

            if (eachLine.equals("")){
                output += getMinAndTotalDistance(walkwaySize, seatedPplArrLs);
                isWalkwaySize = true;
                seatedPplArrLs.clear();
                continue;
            }

            String[] currLine = eachLine.split("\s+");
            int y = Integer.parseInt(currLine[0]);
            int x = Integer.parseInt(currLine[1]);
     
            if (isWalkwaySize){
                isWalkwaySize = false;
                walkwaySize = new int[]{y,x};
            } else{
                seatedPplArrLs.add(new int[]{y,x});
            }
            
            
        }
        if (lines.size() > 0 && lines.get(lines.size()-1) != ""){
            output += getMinAndTotalDistance(walkwaySize, seatedPplArrLs);
        }
        if (output.length()>2 && output.substring(output.length()-2).equals("\n\n")){
            output = output.substring(0, output.length()-2);
        }
        return output;
    }

    public static String getMinAndTotalDistance(int[] walkwaySize, ArrayList<int[]> seatedPplArrLs){
        GenPath genPath = new GenPath(walkwaySize, seatedPplArrLs);
        genPath.findBestPath();
        int[] result = genPath.getMinAndTotalDistance();
        return "min " + result[0] + ", total " + result[1]+"\n\n";
    }
}
