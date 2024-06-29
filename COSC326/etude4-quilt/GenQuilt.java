import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * A GUI program desinged to generate quilt patterns (recursive squares drawn on corners) for Etude4 COSC326
 * @author Beckham Wilson 7564267
 */
public class GenQuilt {
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ArrayList<String> lines = getStdInLines();
                    ArrayList<Double> scalingArrLs = new ArrayList<Double>();
                    ArrayList<Color> depthToColorArrLs = new ArrayList<Color>();
                    // Parse input into scale and color
                    for (int i = 0; i < lines.size(); i++) {
                        
                        String eachLine = lines.get(i);
                        if (eachLine.isEmpty()) break;
                        String[] wordsInLine = eachLine.split("\s+");
                        scalingArrLs.add(Double.parseDouble(wordsInLine[0]));
                        Color color = new Color(Integer.parseInt(wordsInLine[1]), Integer.parseInt(wordsInLine[2]),
                                Integer.parseInt(wordsInLine[3]));
                                depthToColorArrLs.add(color);
        }
                //Reverse both lists
                Collections.reverse(scalingArrLs);
                Collections.reverse(depthToColorArrLs);
                double[] scalingArr = new double[scalingArrLs.size()];
                    for (int i = 0; i < scalingArrLs.size(); i++) {
                        scalingArr[i] = scalingArrLs.get(i);
                    }
                    Color[] depthToColorArr = new Color[depthToColorArrLs.size()];
                    for (int i = 0; i < depthToColorArrLs.size(); i++) {
                        depthToColorArr[i] = depthToColorArrLs.get(i);
                    }
                    new QuiltFrame(scalingArr, depthToColorArr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }


    /**
     * Read stdin lines 
     * @return list of lines from stdin
     */
    public static ArrayList<String> getStdInLines(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return lines; 
    }

}
