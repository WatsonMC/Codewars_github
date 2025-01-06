package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class sumN {
    public int[] twoSum(int[] nums, int target) {
        Integer numLength = nums.length;
        for (int i = 0; i < numLength; i++) {
            for (int j = i + 1; j < numLength; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public int[] twoSum2(int[] nums, int target) {
        Integer numLength = nums.length;
        Map<Integer, Integer> targetNegs = new HashMap<>();
        for (int i = 0; i < numLength; i++) {
            if (targetNegs.containsKey(target - nums[i]) &&  targetNegs.get(target - nums[i])!= i) {
                return new int[]{ targetNegs.get(target - nums[i]),i};
            }
            targetNegs.put(nums[i], i);
        }
        return new int[]{};
    }
}