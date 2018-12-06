import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;

public class Day2Part2 {
    /*
     * Confident that your list of box IDs is complete, you're ready to find the
     * boxes full of prototype fabric.
     * 
     * The boxes will have IDs which differ by exactly one character at the same
     * position in both strings. For example, given the following box IDs:
     * 
     * abcde fghij klmno pqrst fguij axcye wvxyz
     * 
     * The IDs abcde and axcye are close, but they differ by two characters (the
     * second and fourth). However, the IDs fghij and fguij differ by exactly one
     * character, the third (h and u). Those must be the correct boxes.
     * 
     * What letters are common between the two correct box IDs? (In the example
     * above, this is found by removing the differing character from either ID,
     * producing fgij.)
     */

    /*
     * Here we have to iterate each line through the entire list of data after its
     * position and find out if they differ only by one char and pick the other
     * chars.
     */
    public static void main(String[] args) {
        try {

            Scanner inputread = new Scanner(new File("input/Day2_input.txt"));
            // int[] frequency = new int [10000];
            ArrayList<String> inputData = new ArrayList<String>();
            Day2Part2 run = new Day2Part2();
            while (inputread.hasNextLine()) {
                // index++;
                String input = inputread.nextLine();
                inputData.add(input);
            }
            ArrayList<String> copyData = new ArrayList<String>(inputData);
            for (int index = 0; index < inputData.size(); index++) {
                String dataToMatch = inputData.get(index);
                for (int len = index + 1; len < inputData.size(); len++) {
                    run.compareTwoStringForSingleCharMatch(dataToMatch, inputData.get(len));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public String compareTwoStringForSingleCharMatch(String one, String two) {
        if (one.length() != two.length()) {
            return null;
        }
        int matchcount = 0;
        int thatonechar_position = 0;
        int stringsize = one.length();
        for (int index = 0; index < one.length(); index++) {
            if (one.charAt(index) != two.charAt(index)) {
                thatonechar_position = index;
                matchcount++;
                if (matchcount > 1) {
                    break;
                }
            }
            if (index == (one.length() - 1) && matchcount == 1) {
                System.out.println("Success:" + one);
                StringBuilder sb = new StringBuilder(one);
                sb.deleteCharAt(thatonechar_position);
                System.out.println(sb.toString());
                return sb.toString();
            }
        }
        return null;
    }
}