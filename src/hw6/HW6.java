/**
 *  CEG4750 HW6
 *  Data Anonymization
 * k-anonymity and l-diversity
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
 * TODO
 */

import java.io.File;
import java.io.FileNotFoundException;
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
        try ( // Read in CSV file
        //Get scanner instance
                Scanner scanner = new Scanner(new File("test.csv"))) {
            //Set the delimiter used in the file
            scanner.useDelimiter(",");
            // Get all tokens and store them in data structure
            // I am just printing them
            while (scanner.hasNext()) {
                System.out.print(scanner.next() + "\t|");
                
            }
            // Get List of columns of quasi-identifires and sensitive attributes
            // Find k & l
        }
    }
    
}
