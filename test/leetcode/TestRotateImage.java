package leetcode;

import org.junit.Test;

public class TestRotateImage {

    @Test
    public void testRotate(){
        int[] r1 = new int[]{1,2,3};
        int[] r2 = new int[]{4,5,6};
        int[] r3 = new int[]{7,8,9};

        int[][] matrix = new int[][]{r1,r2,r3};

        RotateImage rt = new RotateImage();
        rt.rotate(matrix);
    }
}
