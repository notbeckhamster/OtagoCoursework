package src;
import java.util.*;

/**
 * 
 * Appplication class for AntSimulator
 * 
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
public class AntSimulatorApp{
    public static void main(String[] args){
        ArrayList<String> lines = Utils.readSysIn();
        AntSim antSim = new AntSim();
        System.out.println(antSim.process(lines));
    }


}