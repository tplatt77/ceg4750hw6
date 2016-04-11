/**
 * CEG4750 HW6 Data Anonymization k-anonymity and l-diversity
 *
 * by Thomas Platt
 */
package hw6;

/**
 * INSTRUCTIONS!!! The concepts of k-anonymity and l-diversity are central to
 * data anonymization. In this assignment we will explore those concepts and the
 * degree to which they hold in publicly available datasets in more detail.
 * Write a program to accept a comma separated value file containing a dataset
 * and a list of which columns in the dataset are to be considered
 * quasi-identifiers and sensitive attributes. Your program should find k and l
 * for the dataset. Find a publicly available dataset and test your code on it.
 * Turn in your source code, the dataset on which you ran your program (include
 * a link to the original source), the quasi-identifier you used, and the values
 * for k and l.
 *
 * NOTES:
 *
 * References: For scanning input:
 * http://stackoverflow.com/questions/15183761/how-to-check-the-end-of-line-using-scanner
 * 
 * Dataset source :
 * http://nflsavant.com/about.php
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
    public static void main(String[] args) throws FileNotFoundException {
        //Set the delimiter used in the file
        List<List<String>> initialData = new ArrayList<>();
        List<List<String>> suppressedData, anonymizedData;
        
        //Scanner keyboardInput = new Scanner(System.in);
        //Input prompt
        // TAKE INPUT HERE, name of dataset file.

        try ( // Read in CSV file
                //Get  File scanner instance
                Scanner fileScanner = new Scanner(new File("test.csv"))) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                List<String> newRow = new ArrayList<>();

                try (Scanner lineScanner = new Scanner(line)) {
                    while (lineScanner.hasNext()) {
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
            suppressedData = initialData;
            boolean suppressed = (suppressColumn(suppressedData, "Name")
                    &   (suppressColumn(suppressedData, "first_name")
                    &   (suppressColumn(suppressedData,"last_name"))));
            if (suppressed) {
                System.out.println("With data suppressed: ");
                printData(suppressedData);
            } else {
                System.out.println("Data was not successfully suppressed");
            }
               
        }
        List<List<String>> generalizedData = suppressedData;
        generalizeCol(generalizedData, "Age", true);
        
        printData(generalizedData);
    }

    
    /**
     * Function: printData
     * This function takes a dataset and prints it to the screen. It is used
     * for testing with smaller datasets.
     * 
     * @param dataset 
     */
    static void printData(List<List<String>> dataset) {
            //System.out.print("\n\n");

            //Test by displaying
        // Iterate over dataset ArrayList
        Iterator iterator = dataset.iterator();
        while (iterator.hasNext()) {
            //System.out.println(iterator.next());
            List<String> nextList = (List<String>) iterator.next();
            Iterator nextIterator = nextList.iterator();
            while (nextIterator.hasNext()) {
                System.out.print(nextIterator.next() + "\t|");
            }
            System.out.println();
        }

        //System.out.print("\n\n");
    }
    
    /**
     * Function: printList
     * This function takes a list and prints its contents to the screen in order.
     * @param list 
     */
    static void printList(List list)
    {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
           System.out.print(iterator.next() + " ");
        }
    }

    
    /**
     * Function: suppressColumn
     * This function suppresses the data in the column by name and replaces all
     * values with an asterisk (*).
     * @param dataSet
     * @param colName
     * @return Returns true if the suppression executes successfully
     */
    static boolean suppressColumn(List<List<String>> dataSet, String colName) {
        if (dataSet.isEmpty() || "".equals(colName)) {
            return false;
        }
        List<List<String>> anonymizedData = dataSet;

        int columnSuppressed = getColNum(dataSet, colName);
        
        if(columnSuppressed >= 0)
        {
        Iterator iterator = anonymizedData.iterator();
        iterator.next(); // Skip the Column Names!
        while (iterator.hasNext()) {
            //System.out.println(iterator.next());
            List<String> row = (List<String>) iterator.next();
            row.set(columnSuppressed, "*");
        }
        
        return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Function: generalizeCol
     * This function generalizes the data for the column passed by name. It takes
     * a boolean value of whether the data is numeric. 
     * @param dataSet
     * @param colName
     * @param isNumeric
     * @return True if the generalization succeeds, false otherwise.
     */
    static boolean generalizeCol(List<List<String>> dataSet, String colName, boolean isNumeric) {
        if (dataSet.isEmpty() || "".equals(colName)) {
            return false;
        }

        int columnGeneralized = getColNum(dataSet, colName);
        if(columnGeneralized < 0)
        {
            return false;
        }
        
        List<List <String>> alteredDataSet;
        //TODO: Implement generalization algorithm
        if (isNumeric) {
            //Build list of numbers
            List<Integer> numbers = new ArrayList<>();
            int min=0, max=0;
            boolean firstRow = true;
            Iterator iterator = dataSet.iterator();
            iterator.next(); // Skip column names

            while (iterator.hasNext()) {
                //System.out.println(iterator.next());
                List<String> row = (List<String>) iterator.next();
                int thisNum = Integer.parseInt(row.get(columnGeneralized));
                numbers.add(thisNum);
                
                if(firstRow)
                {
                    min = max = thisNum;
                    firstRow = false;
                }
                else
                {
                    if(thisNum < min)
                    {
                        min = thisNum;
                    }
                    else if(thisNum > max)
                    {
                        max = thisNum;
                    }
                }
            }
           
            printList(numbers);
            
            //Set up ranges
            
            //Min
            System.out.println("\nMin value for age: " + min);
            
            int rangeMin = min-min%10;
            System.out.println("Min range: " + rangeMin);
            
            //Max
            System.out.println("Max value for age: " + max);
            
            int rangeMax = max + (10 - max%10);
            System.out.println("Max range: " + rangeMax);
            
            // Ranges of 10
            int range = rangeMax - rangeMin;
            int interval = 2*range / numbers.size();
            
            System.out.println("\nRange: " + range + "\nInterval: " + interval);
            System.out.println("Age range 1: " + rangeMin + " - " + (rangeMin+interval));
            System.out.println("Age range 2: " + (rangeMax-interval) + " - " + rangeMax);
            
            alteredDataSet = dataSet;
            iterator = alteredDataSet.iterator();
            List<String> topRow = (List<String>) iterator.next(); // Skip column names
            topRow.set(columnGeneralized, "Age Range");
            
            while (iterator.hasNext()) {
                //System.out.println(iterator.next());
                String ageRange;
                List<String> row = (List<String>) iterator.next();
                int age = Integer.parseInt(row.get(columnGeneralized));
                if(age < (rangeMin+interval))
                {
                    ageRange = "" + rangeMin + " - " + (rangeMin+interval);
                }
                else
                {
                    ageRange = "" + (rangeMax-interval) + " - " + rangeMax;
                }
                row.set(columnGeneralized, ageRange);
            }
        } 
        else 
        {

        }
        return true;
    }

    /**
     * Function: getColNum
     * This function gets the column number/index for the column name passed to it
     * that is within the dataset passed.
     * @param dataSet
     * @param colName
     * @return The index of the column for the column name.
     */
    static int getColNum(List<List<String>> dataSet, String colName) {
        int colNum = -1;

        List<String> colNames = dataSet.get(0);
        System.out.println(colName);

        for (int i = 0; i < colNames.size(); i++) {
            System.out.println(colNames.get(i));
            if (colName.toLowerCase().compareTo(colNames.get(i).toLowerCase()) == 0) {
                colNum = i;
            }
        }

        //TESTING
        System.out.println("Column to suppress = " + colNum);

        return colNum;
    }
    
    static int computeK()
    {
        int k = 0;
        
        return k;
    }
    
    static int computeL()
    {
        int l = 0;
        
        return l;
    }
}
