package src.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.Parser;
import src.Utils;
public class Testing{

    @Test
    void testingi0o0() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = Parser.process(linesArrLs);
        System.out.println(actualOutput);
        String expectedOutput = Utils.outputFileAsString("assets/o0.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }



   
}