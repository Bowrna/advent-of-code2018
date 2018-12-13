import java.io.File;
import java.util.LinkedHashSet;
import java.util.Scanner;
import  java.util.Set;
import  java.io.PrintStream;

class Day5Part1{
    boolean tryAgain = true;
    public static void main(String[] args)
    {
        Day5Part1 run = new Day5Part1();
        try{
            //PrintStream o = new PrintStream(new File("A.txt"));
            //System.setOut(o);
            Scanner inputread = new Scanner(new File("input/Day5_input.txt"));
            StringBuilder input = new StringBuilder();
            while (inputread.hasNextLine()) {
                String data = inputread.nextLine();
                input.append(data.trim());
            }
            String process = input.toString();

            run.processPolymer(process);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String removeChar(String data,char element)
    {
        String copy = new String(data);
        copy.replaceAll(String.valueOf(element),"");
        copy.replaceAll(String.valueOf(element).toLowerCase(),"");
        return  copy;
    }
    public void processPolymer(String copydata)
    {
        StringBuilder data = new StringBuilder(copydata);
        int len = data.length();
        int index = 0;
        boolean tryAgain = true;
        boolean matchedOnce = false;
        while(tryAgain)
        {
            if(index < 0)
            {
                index = 0;
            }
            int hold = index+1;
            if(hold < data.length() && hold > 0) {
                char first = data.charAt(index);
                char second = data.charAt(hold);
                int first_ascii = (int) first;
                int second_ascii = (int) second;
                int diff = Math.abs(first_ascii - second_ascii);
                if (diff == 32) {
                    matchedOnce = true;
                    //System.out.println("First:"+first+" second:"+second);
                    data.deleteCharAt(index);
                    data.deleteCharAt(index);
                    //System.out.println("data:"+data);
                    index--;
                }
                else {
                    index++;
                }
            }
            else
            {
                break;
            }
            //System.out.println("index:"+index+" data len:"+data.length());
        }

        if(matchedOnce)
        {
            processPolymer(data.toString());
        }
        else
        {
            //System.out.println("Processed Data:"+data.toString());
            System.out.println("Processed Data length:"+data.length());
        }
    /*if(tryAgain)
    {
        tryAgain = false;
        processPolymer(data);
    }
    else
    {
        System.out.println("End Data:"+data);
        System.out.println("len:"+data.length());
        System.exit(1);
    }*/
    }
}