import code.BIrdMountainNew;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BirdMountainTest {

    public static char[][] simpleMountain;
    public static char[][] complexMountain;

    @Before
    public void setUp(){
        simpleMountain = new char[3][3];
        char[] r1 = new char[]{' ','^','^'};
        char[] r2 = new char[]{'^','^','^'};
        char[] r3 = new char[]{' ','^',' '};
        simpleMountain[0] = r1;
        simpleMountain[1] = r2;
        simpleMountain[2] = r3;

;
    }

    @Test
    public void testMountainCopy(){
        int[][] simpleResult = new int[][]{
                {0,-1,-1},
                {-1,-1,-1},
                {0,-1,0}
        };

        Assert.assertArrayEquals(simpleResult, BIrdMountainNew.copyMountain(simpleMountain));
    }
}
