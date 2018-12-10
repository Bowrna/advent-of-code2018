import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.lang.model.util.ElementScanner6;

import java.io.File;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Arrays;

class Day4Part1 {
    // For example, consider the following records, which have already been
    // organized into chronological order:

    // [1518-11-01 00:00] Guard #10 begins shift
    // [1518-11-01 00:05] falls asleep
    // [1518-11-01 00:25] wakes up
    // [1518-11-01 00:30] falls asleep
    // [1518-11-01 00:55] wakes up
    // [1518-11-01 23:58] Guard #99 begins shift
    // [1518-11-02 00:40] falls asleep
    // [1518-11-02 00:50] wakes up
    // [1518-11-03 00:05] Guard #10 begins shift
    // [1518-11-03 00:24] falls asleep
    // [1518-11-03 00:29] wakes up
    // [1518-11-04 00:02] Guard #99 begins shift
    // [1518-11-04 00:36] falls asleep
    // [1518-11-04 00:46] wakes up
    // [1518-11-05 00:03] Guard #99 begins shift
    // [1518-11-05 00:45] falls asleep
    // [1518-11-05 00:55] wakes up

    // Timestamps are written using year-month-day hour:minute format. The guard
    // falling asleep or waking up is always the one whose shift most recently
    // started. Because all asleep/awake times are during the midnight hour (00:00 -
    // 00:59), only the minute portion (00 - 59) is relevant for those events.

    // Visually, these records show that the guards are asleep at these times:

    // Date ID Minute
    // 000000000011111111112222222222333333333344444444445555555555
    // 012345678901234567890123456789012345678901234567890123456789
    // 11-01 #10 .....####################.....#########################.....
    // 11-02 #99 ........................................##########..........
    // 11-03 #10 ........................#####...............................
    // 11-04 #99 ....................................##########..............
    // 11-05 #99 .............................................##########.....

    // The columns are Date, which shows the month-day portion of the relevant day;
    // ID, which shows the guard on duty that day; and Minute, which shows the
    // minutes during which the guard was asleep within the midnight hour. (The
    // Minute column's header shows the minute's ten's digit in the first row and
    // the one's digit in the second row.) Awake is shown as ., and asleep is shown
    // as #.

    // Note that guards count as asleep on the minute they fall asleep, and they
    // count as awake on the minute they wake up. For example, because Guard #10
    // wakes up at 00:25 on 1518-11-01, minute 25 is marked as awake.

    // If you can figure out the guard most likely to be asleep at a specific time,
    // you might be able to trick that guard into working tonight so you can have
    // the best chance of sneaking in. You have two strategies for choosing the best
    // guard/minute combination.

    // Strategy 1: Find the guard that has the most minutes asleep. What minute does
    // that guard spend asleep the most?

    // In the example above, Guard #10 spent the most minutes asleep, a total of 50
    // minutes (20+25+5), while Guard #99 only slept for a total of 30 minutes
    // (10+10+10). Guard #10 was asleep most during minute 24 (on two days, whereas
    // any other minute the guard was asleep was only seen on one day).

    // While this example listed the entries in chronological order, your entries
    // are in the order you found them. You'll need to organize them before they can
    // be analyzed.

    // What is the ID of the guard you chose multiplied by the minute you chose? (In
    // the above example, the answer would be 10 * 24 = 240.)
    int[][] sleptAwakeSlot = new int[1000][60];
    int[] output = new int[60];
    List<String> idList = new ArrayList<String>();
    Map<Integer, Integer> idVsSleptMins = new HashMap<Integer, Integer>();
    Integer maxSleptMinOfGuard = null;
    List<Integer> positionOfMostSleptGuard = new ArrayList<Integer>();

    // Output variables
    int mostSleptMinute = 0;
    Integer mostSleptGuardId = null;

    public static void main(String[] args) {
        Day4Part1 run = new Day4Part1();
        SortedMap<Long, String> inputData = new TreeMap<Long, String>();
        try {
            Scanner inputread = new Scanner(new File("input/Day4_input.txt"));
            while (inputread.hasNextLine()) {
                String data = inputread.nextLine();
                String[] infos = data.split("]");
                String time = infos[0].replace("[", "");
                Long milliTime = run.convertDateToMillis(time);
                String detail = infos[1];
                inputData.put(milliTime, detail);
            }
            run.plotTheData(inputData);
            run.mostSleptGuard();
            run.indexOfMaxSleptGuard();
            run.alwaysSleepingMin();
            run.output();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public Long convertDateToMillis(String date) {
        Long millis = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date mydate = sdf.parse(date);
            millis = Long.valueOf(mydate.getTime());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return millis;
    }

    public void plotTheData(SortedMap<Long, String> data) {
        ListIterator<Long> entries = new LinkedList(data.keySet()).listIterator();
        int index = 0;
        while (entries.hasNext()) {
            Long time = entries.next();
            String detail = data.get(time).trim();
            // System.out.println("Detail retrieved:" + detail);
            if (detail.contains("Guard")) {
                int firstSpace = detail.indexOf(" ");
                String id = detail.substring(detail.indexOf("#") + 1, detail.indexOf(" ", firstSpace + 1));
                idList.add(id);
                boolean proceed = true;
                int sleptMin = 0;
                boolean sleptMinUpdated = false;
                boolean wokeMinUpdated = false;
                int wokeMin = 0;
                while (proceed && entries.hasNext()) {
                    Long timeDetail = entries.next();
                    String sleepWakeDetail = data.get(timeDetail);
                    if (sleepWakeDetail != null
                            && (sleepWakeDetail.contains("falls asleep") || sleepWakeDetail.contains("wakes up"))) {
                        if (sleepWakeDetail.contains("falls asleep")) {
                            sleptMinUpdated = true;
                            sleptMin = convertMillisToDateAndFindMinute(timeDetail);
                        }
                        if (sleepWakeDetail.contains("wakes up")) {
                            wokeMinUpdated = true;
                            wokeMin = convertMillisToDateAndFindMinute(timeDetail);
                        }
                    } else {
                        proceed = false;
                        entries.previous();
                    }
                    if (sleptMinUpdated && wokeMinUpdated) {
                        plotDetailInArray(index, sleptMin, wokeMin);
                    }
                }
            }
            index++;
        }
    }

    public void mostSleptGuard() {
        for (int index = 0; index < idList.size(); index++) {
            Integer id = Integer.valueOf(idList.get(index));
            int count = 0;
            if (idVsSleptMins.containsKey(id)) {
                count = idVsSleptMins.get(id);
            }
            for (int in = 0; in < 60; in++) {
                int val = sleptAwakeSlot[index][in];
                if (val == 1) {
                    count++;
                }
            }
            idVsSleptMins.put(id, count);
        }
        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry<Integer, Integer> entry : idVsSleptMins.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        mostSleptGuardId = maxEntry.getKey();
        maxSleptMinOfGuard = maxEntry.getValue();
        // System.out.println("id vs slept min:" + maxEntry.getKey() + " " +
        // maxEntry.getValue());
        // System.out.println("idVsSleptMins:" + idVsSleptMins);
        // System.out.println("idlist:" + idList);
    }

    public void output() {
        System.out.println("Output is:" + mostSleptGuardId * mostSleptMinute);
    }

    public void indexOfMaxSleptGuard() {
        // System.out.println("idlist:" + idList);
        for (int pos = 0; pos < idList.size(); pos++) {
            if (Integer.valueOf(idList.get(pos)).equals(mostSleptGuardId)) {
                positionOfMostSleptGuard.add(pos);
            }
        }
    }

    public void alwaysSleepingMin() {
        int[] mins = new int[60];
        for (int index = 0; index < positionOfMostSleptGuard.size(); index++) {
            int pos = positionOfMostSleptGuard.get(index);
            for (int j = 0; j < 60; j++) {
                // System.out.print(sleptAwakeSlot[pos][j]);
                mins[j] += sleptAwakeSlot[pos][j];
            }
            // System.out.println();
        }
        int max = mins[0];
        int maxposition = 0;
        for (int size = 1; size < 60; size++) {
            if (mins[size] > max) {
                max = mins[size];
                maxposition = size;
            }
        }
        mostSleptMinute = maxposition;
    }

    public void plotDetailInArray(int row, int startColumnPosition, int endColumnPosition) {
        for (int index = startColumnPosition; index < endColumnPosition; index++) {
            sleptAwakeSlot[row][index] = 1;
        }
    }

    public int convertMillisToDateAndFindMinute(Long millis) {
        int minutes = 0;
        try {
            Date yourdate = new Date(millis);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yourdate);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return minutes;
    }
}