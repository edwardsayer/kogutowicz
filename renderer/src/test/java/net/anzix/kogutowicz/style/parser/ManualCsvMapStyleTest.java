package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.*;
import java.io.File;

/**
 * Manual test for CsvMapStyle to verify error handling.
 */
public class ManualCsvMapStyleTest {

    public static void main(String[] args) {
        testValidFile();
        testInvalidFile();
    }

    private static void testValidFile() {
        System.out.println("Testing with valid CSV file:");
        try {
            CsvMapStyle st = new CsvMapStyle();
            st.setSource(new File("src/test/osmstyle.csv"));
            Cartographer c = new Cartographer(null);
            st.applyStyle(c);
            System.out.println("Success! Layers: " + c.getLayers().size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void testInvalidFile() {
        System.out.println("Testing with invalid CSV file:");
        try {
            CsvMapStyle st = new CsvMapStyle();
            st.setSource(new File("src/test/invalid_test.csv"));
            Cartographer c = new Cartographer(null);
            st.applyStyle(c);
            System.out.println("Success! Layers: " + c.getLayers().size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // Only print stack trace for unexpected errors
            if (!(e instanceof IllegalArgumentException) && 
                !(e instanceof IllegalStateException)) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}