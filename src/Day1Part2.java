import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;

public class Day1Part2{
    public static void main(String[] args) {
        try{
        Scanner inputread = new Scanner(new File("input/Day1_input.txt"));
        ArrayList<Integer> frequency_changes = new ArrayList<Integer>(1016);
        ArrayList<Integer> dataCopy = new ArrayList<Integer>(1016);
       // ArrayList initial_position_frequency = new ArrayList(1016);
        //ArrayList double_position_holder = new ArrayList(1016);
        //ArrayList twice_found_frequency = new ArrayList(1016);
        //Store consecutive frequency changes
        //+3, +3, +4, -2, -4
        int element = 0;
        HashSet seenValues = new HashSet();
        while(inputread.hasNextInt())
        {
            element = inputread.nextInt();
            frequency_changes.add(Integer.valueOf(String.valueOf(element)));
            dataCopy.add(Integer.valueOf(String.valueOf(element)));
        }
        
      
        //dataCopy = (ArrayList)frequency_changes.clone();
        //System.out.println("dataCopy:"+dataCopy.get(0));
        Integer sum = 0;
        while(!seenValues.contains(sum))
        {
            seenValues.add(sum);
            if(dataCopy.size() == 0)
            {
                //System.out.println("Going here:"+frequency_changes.size());
                dataCopy = new ArrayList(frequency_changes);
            }
            //System.out.println("dataCopy size:"+dataCopy.size());
            sum += (int)dataCopy.get(0);
            //System.out.println("sum:"+sum);
            dataCopy.remove(0);
            //System.out.println("seenValues:"+seenValues);
        }
        //System.out.println("set :"+seenValues);
        System.out.println("sum:"+sum);
       /* Hashtable tableToFindDuplicate = new Hashtable();
        for(int size=0;size <frequency_changes.size();size++)
        {
            int element = Integer.valueOf((String)frequency_changes.get(size));
            String elements = String.valueOf(element);
            // If not put in the table, then put that element with count as 1
            if(!tableToFindDuplicate.contains(elements))
            {
                tableToFindDuplicate.put(elements,1);
                //twice_found_frequency.add(elements);
                //initial_position_frequency.add(String.valueOf(size));
                //int position = frequency_changes.indexOf(elements);
                //double_position_holder.add(String.valueOf(position));
                //System.out.println("First element to reach twice is:"+elements);
            }
            else
            {
                System.out.println("Found the first element with duplicate:"+elements);
            }
        }
        System.out.println("Table detail:"+tableToFindDuplicate.size());*/
       /* ArrayList difference = new ArrayList(1016);
        for(int ind = 0; ind < initial_position_frequency.size(); ind++)
        {
            Integer initval = Integer.valueOf(String.valueOf(initial_position_frequency.get(ind)));
            Integer doubleval = Integer.valueOf(String.valueOf(double_position_holder.get(ind)));

            Integer diff = doubleval - initval;
            difference.add(String.valueOf(diff));
        }
        
        System.out.println("frequency_changes:"+frequency_changes);
        System.out.println("Twice found element:"+twice_found_frequency);
        System.out.println("Element position holder:"+initial_position_frequency);
        System.out.println("double the element position holder:"+double_position_holder);
        System.out.println("Diff in position:"+difference);*/
        //System.out.println("Sum array:"+frequency_changes.toString());
        //System.out.println("Output is:"+sum);
    }
    catch(Exception ex)
    {
        System.out.println("Error in reading:"+ex);
        ex.printStackTrace();
    }
    }
    
}