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
        
        atex.extractSet1("\\data\\infected\\bot-june-1-b-v2.csv", "C:\\Users\\leo\\Desktop\\set-1-bot-june-1-b.csv");
          
        atex.extractSet2("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 2\\infected\\set-2-bot-june-1.csv");
        
        atex.extractSet3("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 3\\infected\\set-3-bot-june-1.csv");        
        
        atex.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-1.csv");
        
        /*csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-1-b-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-1-b.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-2-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-2.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-4-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-4.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-5-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-5.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-june-6-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-june-6.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-idle-june-7-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-idle-june-7.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-idle-june-7-b-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-idle-june-7-b.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-idle-june-8-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-idle-june-8.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\bot-may-31-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-bot-may-31.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\infected\\testing-banks-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\infected\\set-4-testing-banks.csv");*/
        
        /*csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\clean\\clean-june-9-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\clean\\set-4-clean-june-9.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\clean\\clean-june-11-b-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\clean\\set-4-clean-june-11-b.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\clean\\clean-june-11-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\clean\\set-4-clean-june-11.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\clean\\clean-june-10-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\clean\\set-4-clean-june-10.csv");
        csvr.extractSet4("C:\\Users\\leo\\Desktop\\full captures\\clean\\clean-june-12-v2.csv", "C:\\Users\\leo\\Desktop\\data analysis\\set 4\\clean\\set-4-clean-june-12.csv");*/
    }
}
    

