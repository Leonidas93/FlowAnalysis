package flowanalysis;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 *
 * @author leo
 */

public class FlowAnalysis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        AttrExtraction atex = new AttrExtraction ();
        
        atex.extractSet1("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 1\\infected\\set-1-bot-june-1.csv");
          
        atex.extractSet2("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 2\\infected\\set-2-bot-june-1.csv");
        
        atex.extractSet3("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 3\\infected\\set-3-bot-june-1.csv");        
        
        atex.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-1.csv");
   
    }
}
    

