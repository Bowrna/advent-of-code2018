import java.util.Map;
import java.util.Scanner;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
class Day2Part1
{

   /*  To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID containing exactly two of any letter and then separately counting those with exactly three of any letter. You can multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.

For example, if you see the following box IDs:

    abcdef contains no letters that appear exactly two or three times.
    bababc contains two a and three b, so it counts for both.
    abbcde contains two b, but no letter appears exactly three times.
    abcccd contains three c, but no letter appears exactly two times.
    aabcdd contains two a and two d, but it only counts once.
    abcdee contains two e.
    ababab contains three a and three b, but it only counts once.

Of these box IDs, four of them contain a letter which appears exactly twice, and three of them contain a letter which appears exactly three times. Multiplying these together produces a checksum of 4 * 3 = 12.

What is the checksum for your list of box IDs? */
int twosCounter = 0;
int threesCounter = 0;
    public void incrementTwoCounter()
    {
        twosCounter++;
    }
    public void incrementThreeCounter()
    {
        threesCounter++;
    }
    public static void main(String[] args)
    {
        try {
            Scanner inputread = new Scanner(new File("input/Day2_input.txt"));
        //int[] frequency = new int [10000];
        Day2Part1 run = new Day2Part1();

        while(inputread.hasNextLine())
        {
            //index++;
           String input = inputread.nextLine();
            run.findTwiceThriceChars(input);
        }
        
        run.getOutput();
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        
        //System.out.println("Output:"+(twosCounter*threesCounter));
    }
    public void getOutput()
    {
        System.out.println("Two Counter:"+twosCounter);
        System.out.println("Three Counter:"+threesCounter);
        System.out.println("output:"+(twosCounter*threesCounter));
    }
    public void findTwiceThriceChars(String input)
    {
        
        Map<Character,Integer> details = new HashMap<Character,Integer>();
        for(int index = 0; index < input.length(); index++)
        {
            char key = input.charAt(index);
            if(details.containsKey(key))
            {
                int count = details.get(key);
                details.put(key,++count);
            }
            else
            {
                details.put(key,1);
            }
        }
        boolean twoCountInit = false;
        boolean threeCountInit = false;
        for(Integer count:details.values())
        {
            if(count == 2 && !twoCountInit)
            {
                twosCounter++;
                twoCountInit = true;
            }
            if(count == 3 && !threeCountInit)
            {
                threesCounter++;
                threeCountInit = true;
            }
        }
    }
}