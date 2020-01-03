import code.BirdMountainRiver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBirdMountainRiver {
    public static char[][] simpleMountain;
    public static char[][] complexMountain;
    public static int[][] heightArray;

    @Before
    public void setUp() {
        simpleMountain = new char[3][3];
        char[] r1 = new char[]{' ', '^', '^'};
        char[] r2 = new char[]{'^', '^', '^'};
        char[] r3 = new char[]{' ', '^', ' '};
        simpleMountain[0] = r1;
        simpleMountain[1] = r2;
        simpleMountain[2] = r3;

        heightArray = BirdMountainRiver.getHeightArray(BirdMountainRiver.copyMountain(simpleMountain));
    }

    @Test
    public void testGetLandingPositions(){
        //Test on height array
        Assert.assertEquals(6, BirdMountainRiver.getAvailablePositions(heightArray));
    }
}
