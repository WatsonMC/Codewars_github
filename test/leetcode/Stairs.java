package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Stairs {
    Map<Integer, Integer> ways = new HashMap<>();
    public int climbStairs(int n) {

        if(n ==1 || n == 2) {
            return n;
        }
        else if(!ways.containsKey(n)){
            ways.put(n,climbStairs(n-1) + climbStairs(n-2));
        }
        return ways.get(n);
    }
}
