package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import src.Utils; 
import src.AntSim;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class Testing {


    @Test
    void testingi0o1() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        AntSim antSim = new AntSim();
        String actualOutput = antSim.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }
    
    @Test
    void testingi1o1() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i1.txt");
        AntSim antSim = new AntSim();
        String actualOutput = antSim.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o1.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }

        
    @Test
    void testingi2o2() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i2.txt");
        AntSim antSim = new AntSim();
        String actualOutput = antSim.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o2.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }


        
    @Test
    void testingi3o3() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i3.txt");
        AntSim antSim = new AntSim();
        String actualOutput = antSim.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o3.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }


}

