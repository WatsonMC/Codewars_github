package code;
import org.junit.Test;

import static code.SimpleFun.distributionOf;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SimpleFunTest {

    @Test
    public void testBasicCases() {
        assertArrayEquals(new int[]{18, 11}, distributionOf(new int[]{4, 7, 2, 9, 5, 2}));
        assertArrayEquals(new int[]{1001, 12}, distributionOf(new int[]{10, 1000, 2, 1}));
        assertArrayEquals(new int[]{12, 1000}, distributionOf(new int[]{10, 1000, 2}));
        assertArrayEquals(new int[]{3206, 981}, distributionOf(new int[]{140, 649, 340, 982, 105, 86, 56, 610, 340, 879}));
    }
}
