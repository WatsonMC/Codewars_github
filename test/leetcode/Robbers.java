package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Robbers {
    Map<Integer, Integer> indexValues;


    // this would not work if the same Robbers class will be called for multi
    public int rob(int[] nums) {
        indexValues = new HashMap<>();

        return robRecurse(nums,0);
    }

    public int robRecurse(int[] nums, int i){
        int n = nums.length - i;
        if(n == 1 ){
            return nums[i];
        }else if(n == 2){
            return Math.max(nums[i], nums[i+1]);
        }else{
            if(!indexValues.containsKey(i+2)) {
                indexValues.put(i+2, robRecurse(nums,i+2));
            }
            if(!indexValues.containsKey(i+1)) {
                indexValues.put(i + 1, robRecurse(nums, i + 1));
            }
                return Math.max(
                    nums[i] + indexValues.get(i+2),
                    indexValues.get(i+1)
            );
        }

    }
}
