package test;



import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.*;

/**
 * Class to test anagram parser
 * 
 * @group TwoBobbyTables
 * @author Beckham Wilson
 * @author Dyrel Lumiwes
 */
class TestingAnagramsParser {

    @Test
    void testingi0o0() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = AnagramParser.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    void testingi1o0() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i1.txt");
        String actualOutput = AnagramParser.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o1.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    void testingi2o2() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i2.txt");
        String actualOutput = AnagramParser.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o2.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }

    @Test
    void testingi3o3() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i3.txt");
        String actualOutput = AnagramParser.process(linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o3.txt");
        assertTrue(actualOutput.equals(expectedOutput));
    }



}