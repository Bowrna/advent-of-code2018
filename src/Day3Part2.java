import java.util.ArrayList;
import java.util.Scanner;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.io.File;
import java.io.PrintStream;

class Day3Part2 {

    // A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle 3
    // inches from the left edge, 2 inches from the top edge, 5 inches wide, and 4
    // inches tall. Visually, it claims the square inches of fabric represented by #
    // (and ignores the square inches of fabric represented by .) in the diagram
    // below:

    // ...........
    // ...........
    // ...#####...
    // ...#####...
    // ...#####...
    // ...#####...
    // ...........
    // ...........
    // ...........

    // The problem is that many of the claims overlap, causing two or more claims to
    // cover part of the same areas. For example, consider the following claims:

    // #1 @ 1,3: 4x4
    // #2 @ 3,1: 4x4
    // #3 @ 5,5: 2x2

    // Visually, these claim the following areas:

    // ........
    // ...2222.
    // ...2222.
    // .11XX22.
    // .11XX22.
    // .111133.
    // .111133.
    // ........

    // The four square inches marked with X are claimed by both 1 and 2. (Claim 3,
    // while adjacent to the others, does not overlap either of them.)

    // If the Elves all proceed with their own plans, none of them will have enough
    // fabric. How many square inches of fabric are within two or more claims?
    int[][] fabric = new int[2000][2000];
    int overlapCount = 0;

    public static void main(String[] args) {
        int elfCount = 0;
        Day3Part2 run = new Day3Part2();

        try {
            // PrintStream fileOut = new PrintStream("./out1.txt");
            // System.setOut(fileOut);
            Scanner inputread = new Scanner(new File("input/Day3_input.txt"));
            ArrayList<String> inputData = new ArrayList<String>();
            while (inputread.hasNextLine()) {
                elfCount++;
                String input = inputread.nextLine();
                inputData.add(input);
                String[] inputBroken = input.split("@");
                String id = inputBroken[0];
                id = id.replace("#", "");
                String measurements = inputBroken[1];
                String[] sides = measurements.split(":");
                String leftTop = sides[0];
                String widthHeight = sides[1];
                String[] leftAndTop = leftTop.split(",");
                String[] widthAndHeight = widthHeight.split("x");
                int left = Integer.valueOf(leftAndTop[0].trim());
                int top = Integer.valueOf(leftAndTop[1].trim());
                int width = Integer.valueOf(widthAndHeight[0].trim());
                int height = Integer.valueOf(widthAndHeight[1].trim());
                run.markMeasurement(left, top, width, height);
                // run.markMeasurementAndFindUniqueId(id, left, top, width, height);
            }
            for (int size = 0; size < inputData.size(); size++) {
                String input = inputData.get(size);
                String[] inputBroken = input.split("@");
                String id = inputBroken[0];
                id = id.replace("#", "");
                String measurements = inputBroken[1];
                String[] sides = measurements.split(":");
                String leftTop = sides[0];
                String widthHeight = sides[1];
                String[] leftAndTop = leftTop.split(",");
                String[] widthAndHeight = widthHeight.split("x");
                int left = Integer.valueOf(leftAndTop[0].trim());
                int top = Integer.valueOf(leftAndTop[1].trim());
                int width = Integer.valueOf(widthAndHeight[0].trim());
                int height = Integer.valueOf(widthAndHeight[1].trim());
                // run.markMeasurement(left, top, width, height);
                run.markMeasurementAndFindUniqueId(id, left, top, width, height);
            }
            run.printOutput();
            // run.printGrid();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void printOutput() {
        System.out.println("Overlap Count:" + overlapCount);

    }

    public void printGrid() {
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < 2000; j++) {
                System.out.printf("%5d ", fabric[i][j]);
            }
            System.out.println();
        }
    }

    // #123 @ 3,2: 5x4
    // ...........
    // ...........
    // ...#####...
    // ...#####...
    // ...#####...
    // ...#####...
    // ...........
    // ...........
    // ...........
    public void markMeasurement(int left, int top, int width, int height) {
        int col = left;
        int rw = top;
        int maxRowSize = top + height;
        int maxColSize = left + width;
        for (int row = rw; row < maxRowSize; row++) {
            for (int column = col; column < maxColSize; column++) {
                int point = fabric[row][column];
                point++;
                if (point == 2) {
                    overlapCount++;
                }
                // point++;
                fabric[row][column] = point;
            }
        }
    }

    public void markMeasurementAndFindUniqueId(String id, int left, int top, int width, int height) {
        int col = left;
        int rw = top;
        int maxRowSize = top + height;
        int maxColSize = left + width;
        boolean overLappingDone = false;
        /*
         * for (int row = rw; row < maxRowSize; row++) { for (int column = col; column <
         * maxColSize; column++) { int point = fabric[row][column]; point++; if (point
         * == 2) { overlapCount++; } fabric[row][column] = point; } }
         */
        for (int row = rw; row < maxRowSize; row++) {
            for (int column = col; column < maxColSize; column++) {
                int point = fabric[row][column];
                if (point > 1) {
                    overLappingDone = true;
                    break;
                }
            }
            if (overLappingDone) {
                break;
            }
        }
        if (!overLappingDone) {
            System.out.println("ID with no overlap:" + id);
        }
    }
}