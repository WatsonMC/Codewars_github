package leetcode;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {
    public List<String> summaryRanges(int[] nums) {
        /**
         * To generate the list of ranges we will:
         * - Interate through the array from start to finish
         * -    If we haven't got an active range, set the current num to be the start of range and the last digit
         * -    If we have an active range, check if the current num is the next in the range
         * -        If it is update current num and continue
         * -        if its not, store the last range and reset range to start and current numb,
         */

        //gotta be my worst solution yet

        List<String> ranges = new ArrayList<>();
        if(nums.length == 0){return ranges;}
        int lastNum = 0, rangeStartNum = 0;
        boolean firstRun = true;
        for (Integer num : nums) {
            if (firstRun) {
                lastNum = num;
                rangeStartNum = num;
                firstRun = false;
            } else {
                if (num != lastNum + 1) {
                    if (lastNum == rangeStartNum) {
                        ranges.add(new String(Integer.toString(lastNum)));
                    } else {
                        ranges.add(new String(
                                Integer.toString(rangeStartNum)) + "->" +
                                Integer.toString(lastNum)
                        );
                    }
                    rangeStartNum =num;
                    lastNum = num;
                }else{
                    lastNum = num;
                }
            }
        }
        if(lastNum !=rangeStartNum){
            ranges.add(new String(
                    Integer.toString(rangeStartNum)) + "->" +
                    Integer.toString(lastNum));
        }else{
            ranges.add(new String(Integer.toString(lastNum)));
        }
        return ranges;
    }
}