package leetcode;

import code.AssemblerInterpreter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
public class testSumN {
    @Test
    public void testsimple(){
       leetcode.sumN cls = new leetcode.sumN();
       int[] testRes = cls.twoSum2(new int[]{3,3},6);
       System.out.println(testRes);
    }
}
