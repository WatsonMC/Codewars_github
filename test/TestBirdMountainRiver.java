import code.BirdMountainRiver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestBirdMountainRiver {
    public static char[][] simpleMountain;
    public static char[][] complexMountain;
    public static int[][] heightArray;

    @Before
    public void setUp() {
        simpleMountain = new char[3][3];
        char[] r1 = new char[]{' ', '^', '^'};
        char[] r2 = new char[]{'^', '^', '^'};
        char[] r3 = new char[]{'-', '^', ' '};
        simpleMountain[0] = r1;
        simpleMountain[1] = r2;
        simpleMountain[2] = r3;

        heightArray = BirdMountainRiver.getHeightArray(BirdMountainRiver.convertMountainToHeights(simpleMountain));
    }

    @Test
    public void testGetLandingPositions(){
        //Test on height array
        Assert.assertEquals(6, BirdMountainRiver.getAvailablePositions(simpleMountain));
    }

    @Test
    public void testIntegrated(){
        int[] exp1 = new int[]{6,6,1,0};
        System.out.println(Arrays.toString(BirdMountainRiver.dryGround(simpleMountain)));
        Assert.assertTrue(Arrays.equals(exp1,BirdMountainRiver.dryGround(simpleMountain)));
    }

    @Test
    public void ex() {
        char[][] terrain = {
                "  ^^^^^^             ".toCharArray(),
                "^^^^^^^^       ^^^   ".toCharArray(),
                "^^^^^^^  ^^^         ".toCharArray(),
                "^^^^^^^  ^^^         ".toCharArray(),
                "^^^^^^^  ^^^         ".toCharArray(),
                "---------------------".toCharArray(),
                "^^^^^                ".toCharArray(),
                "   ^^^^^^^^  ^^^^^^^ ".toCharArray(),
                "^^^^^^^^     ^     ^ ".toCharArray(),
                "^^^^^        ^^^^^^^ ".toCharArray()
        };
        System.out.println(Arrays.toString(BirdMountainRiver.dryGround(terrain)));

        Assert.assertTrue(Arrays.equals(new int[]{189, 99, 19, 3}, BirdMountainRiver.dryGround(terrain)));
    }

}
