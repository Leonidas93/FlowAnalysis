package flowanalysis;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 *
 * @author leo
 */

public class FlowAnalysis {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        AttrExtraction atex = new AttrExtraction ();
        atex.superSet("data\\input\\bot 1.csv", "data\\output\\intermediate\\superset bot 1.csv");     //or whatever .csv file you want...  
    }
}
    

