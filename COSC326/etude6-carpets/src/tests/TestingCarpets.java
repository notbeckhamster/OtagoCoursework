package src.tests;



import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import src.CarpetsApp;
import src.Utils;

class TestingCarpets {

    @Test
    void testingo0n5() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = CarpetsApp.process("-n", 5, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0n5.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo0n6() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = CarpetsApp.process("-n", 6, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0n6.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo0m6() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = CarpetsApp.process("-m", 6, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0m6.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo0m5() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = CarpetsApp.process("-m", 5, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0m5.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo3m3() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i3.txt");
        String actualOutput = CarpetsApp.process("-m", 3, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o3m3.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }
    
    @Test
    void testingo3n3() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i3.txt");
        String actualOutput = CarpetsApp.process("-n", 3, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o3n3.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo3b3() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i0.txt");
        String actualOutput = CarpetsApp.process("-b", 3, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o0b3.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo6n5() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i6.txt");
        String actualOutput = CarpetsApp.process("-n", 5, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o6n5.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }
    @Test
    void testingo8m10() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i8.txt");
        String actualOutput = CarpetsApp.process("-m", 10, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o8m10.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo8n10() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i8.txt");
        String actualOutput = CarpetsApp.process("-n", 10, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o8n10.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo8b10() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i8.txt");
        String actualOutput = CarpetsApp.process("-b", 10, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o8n10.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo7n20() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i7.txt");
        String actualOutput = CarpetsApp.process("-n", 20, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o7n20.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    @Test
    void testingo7m10() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i7.txt");
        String actualOutput = CarpetsApp.process("-m", 10, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o7m10.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }
    @Test
    void testingo9b10() {
        ArrayList<String> linesArrLs = Utils.outputFileAsArrLs("assets/i9.txt");
        String actualOutput = CarpetsApp.process("-b", 10, linesArrLs);
        String expectedOutput = Utils.outputFileAsString("assets/o9b10.txt");
        assertTrue(compareLastLine(actualOutput, expectedOutput));
    }

    private boolean compareLastLine(String s1, String s2){
        String[] lines1 = s1.split("\n");
        String[] lines2 = s1.split("\n");
        String lastline1 = lines1[lines1.length-1];
        String lastline2 = lines2[lines2.length-1];
        return lastline1.equals(lastline2);
    }

}
