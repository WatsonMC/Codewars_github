package leetcode;

import org.junit.Test;

public class TestContainers {

    @Test
    public void testContainers(){
        int[] testArray = new int[]{1,8,6,2,5,4,8,3,7};

        ContainerWithMostWater cw = new ContainerWithMostWater();

        cw.maxArea(testArray);

    }
}
