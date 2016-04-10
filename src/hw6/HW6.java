/**
 *  CEG4750 HW6
 *  Data Anonymization
 * k-anonymity and l-diversity
 * 
 * by Thomas Platt
 */
package hw6;

/**
 * INSTRUCTIONS!!!
 * The concepts of k-anonymity and l-diversity are central to data anonymization. 
 * In this assignment we will explore those concepts and the degree to which they 
 * hold in publicly available datasets in more detail. Write a program to accept a 
 * comma separated value file containing a dataset and a list of which columns in 
 * the dataset are to be considered quasi-identifiers and sensitive attributes. 
 * Your program should find k and l for the dataset. Find a publicly available 
 * dataset and test your code on it.
 * Turn in your source code, the dataset on which you ran your program (include 
 * a link to the original source), the quasi-identifier you used, and the values
 * for k and l.
 * 
 * NOTES:
 * 
 * Separate the functionality into methods. Use classes if necessary or sensible.
 * Need to get the design points
 * 
 * References:
 *  For scanning input: 
 * http://stackoverflow.com/questions/15183761/how-to-check-the-end-of-line-using-scanner
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Thomas Platt
 */
public class HW6 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException{
        //Set the delimiter used in the file
        List<List <String>> initialData = new ArrayList<>();
        
        Scanner keyboardInput = new Scanner(System.in);
        //Input prompt
        // TAKE INPUT HERE, name of dataset file.
        
        try ( // Read in CSV file
                //Get  File scanner instance
                Scanner fileScanner = new Scanner(new File("test.csv"))) {
            
            
            while(fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                List<String> newRow = new ArrayList<>();
                
                try (Scanner lineScanner = new Scanner(line)) {
                    while(lineScanner.hasNext())
                    {
                        lineScanner.useDelimiter(",");
                        String token = lineScanner.next();
                        newRow.add(token);
                        //System.out.print(token + "\t|");
                    }
                    
                    initialData.add(newRow);
                    //System.out.println();
                }
            } 
            fileScanner.close();
            
            //Display initialData
            printData(initialData);
            
            // Get List of columns of quasi-identifires and sensitive attributes
            // Find k & l
            
            //Suppress name @ Column 2 (1 in Java)
            
        }
    }
    
    static void printData(List<List <String>> dataset)
    {
            //System.out.print("\n\n");
            
            //Test by displaying
            // Iterate over dataset ArrayList
            Iterator iterator = dataset.iterator();
            while(iterator.hasNext())
            {
                //System.out.println(iterator.next());
                List<String> nextList = (List<String>) iterator.next();
                Iterator nextIterator = nextList.iterator();
                while(nextIterator.hasNext())
                {
                    System.out.print(nextIterator.next() + "\t|");
                }
                System.out.println();
            }
            
            //System.out.print("\n\n");
    }
    
    static boolean suppressColumn(List<List <String>> dataset, String colName)
    {
        boolean suppressed = false;
        
        List<List <String>> anonymizedData = dataset;
            String columnSensitive = "Name";
            int columnSuppressed = -1;
            List<String> colNames = anonymizedData.get(0);
            System.out.println(columnSensitive);
            
            for(int i=0; i < colNames.size(); i++)
            {
                System.out.println(colNames.get(i));
                if(columnSensitive.toLowerCase().compareTo(colNames.get(i).toLowerCase()) == 0)
                {
                    columnSuppressed = i;
                }
            }
            
            System.out.println("Column to suppress = " + columnSuppressed);
            
            Iterator iterator = anonymizedData.iterator();
            while(iterator.hasNext())
            {
                //System.out.println(iterator.next());
                List<String> nextList = (List<String>) iterator.next();
                Iterator nextIterator = nextList.iterator();
                while(nextIterator.hasNext())
                {
                    String token = (String) nextIterator.next();
                    //System.out.print(nextIterator.next() + "\t|");
                }
                //System.out.println();
            }
        
        return suppressed;
    }
    
    static boolean generalizeCol(List <List <String>> dataSet, String colName)
    {
        boolean generalized = false;
        
        //TODO: Implement generalization algorithm
        
        return false;
    }
    
}
