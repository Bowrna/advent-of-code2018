import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

public class Day1Part1{
    public static void main(String[] args) {
        try{
        Scanner inputread = new Scanner(new File("input/Day1_input.txt"));
        //int[] frequency = new int [10000];
        int index = 0;
        int sum = 0;
        while(inputread.hasNextInt())
        {
            index++;
            sum += inputread.nextInt();
        }
        System.out.println("Size:"+index);
        System.out.println("Output is:"+sum);
    }
    catch(Exception ex)
    {
        System.out.println("Error in reading");
    }
    }
}