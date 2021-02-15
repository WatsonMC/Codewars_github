package code;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BulbGridTest {


    @Test
    public void firsTest() {
        List<Point> testRes = BulbGrid.switchBulbs(GAME_MAPS[0]);

    }

    @Test
    public void testGetViableSets(){
        //gets all
        /**     Y -->
         *    X    x.x.x
         *         .....
         *    |    x.B.x
         *         .....
         *   \/    x.x.x
         */
        //T1 All 8 positions successfully found
        List<BulbGrid.Bulb> testBulbs = new ArrayList<>();
        testBulbs.add(new BulbGrid.Bulb(0,0));
        testBulbs.add(new BulbGrid.Bulb(2,0));
        testBulbs.add(new BulbGrid.Bulb(4,0));

        testBulbs.add(new BulbGrid.Bulb(0,2));
        testBulbs.add(new BulbGrid.Bulb(4,2));

        testBulbs.add(new BulbGrid.Bulb(0,4));
        testBulbs.add(new BulbGrid.Bulb(2,4));
        BulbGrid.Bulb bulb4x4 = new BulbGrid.Bulb(4,4);
        testBulbs.add(bulb4x4);

        BulbGrid.Bulb middleBulb = new BulbGrid.Bulb(2,2);

        Assert.assertEquals(testBulbs,middleBulb.viableBulbs(testBulbs));

        //T2 replaces diagonals
        List<BulbGrid.Bulb> testBulbs2 = new ArrayList<>();
        testBulbs2.addAll(testBulbs);
        testBulbs2.remove(new BulbGrid.Bulb(4,4));
        testBulbs2.add(new BulbGrid.Bulb(3,3));
        testBulbs.add(new BulbGrid.Bulb(3,3));

        Assert.assertEquals(testBulbs2,middleBulb.viableBulbs(testBulbs));



    }


    final private static String[] GAME_MAPS = {
            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|.....B..|\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|.....B..|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|.B......|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|.B...B..|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|.B......|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|...BB...|\n"+
                    "|........|\n"+
                    "|.B...B..|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|.B......|\n"+
                    "|.B..B...|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "|.B..B...|\n"+
                    "|.B......|\n"+
                    "|........|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|.B......|\n"+
                    "|......B.|\n"+
                    "|......B.|\n"+
                    "|.B......|\n"+
                    "|......BB|\n"+
                    "|BB......|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|...BB...|\n"+
                    "|........|\n"+
                    "|..B..B..|\n"+
                    "|....B...|\n"+
                    "|....B...|\n"+
                    "|..B.....|\n"+
                    "|.B....B.|\n"+
                    "|........|\n"+
                    "+--------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|.B.B..B.|\n"+
                    "|........|\n"+
                    "|.B..B.B.|\n"+
                    "|........|\n"+
                    "|.B.B..B.|\n"+
                    "|........|\n"+
                    "+--------+",

            "+----------+\n"+
                    "|..........|\n"+
                    "|..........|\n"+
                    "|..........|\n"+
                    "|..B.B....B|\n"+
                    "|.B...B...B|\n"+
                    "|.....B....|\n"+
                    "|..........|\n"+
                    "|.B......B.|\n"+
                    "|....B.....|\n"+
                    "|.B..BB..B.|\n"+
                    "+----------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|.BBBBBB.|\n"+
                    "|.BBBBBB.|\n"+
                    "|.BBBBBB.|\n"+
                    "|.BBBBBB.|\n"+
                    "|.BBBBBB.|\n"+
                    "|.BBBBBB.|\n"+
                    "|........|\n"+
                    "+--------+",

            "+---------+\n"+
                    "|.........|\n"+
                    "|.B.B.B.B.|\n"+
                    "|..B.B.B..|\n"+
                    "|.B.B.B.B.|\n"+
                    "|..B.B.B..|\n"+
                    "|.B.B.B.B.|\n"+
                    "|..B.B.B..|\n"+
                    "|.B.B.B.B.|\n"+
                    "|.........|\n"+
                    "+---------+",

            "+---------+\n"+
                    "|.B......B|\n"+
                    "|.B.......|\n"+
                    "|......B..|\n"+
                    "|.........|\n"+
                    "|....B....|\n"+
                    "|.....B...|\n"+
                    "|.........|\n"+
                    "|.......B.|\n"+
                    "|.........|\n"+
                    "+---------+",

            "+---------+\n"+
                    "|.........|\n"+
                    "|...BBB...|\n"+
                    "|..B...B..|\n"+
                    "|......B..|\n"+
                    "|.....B...|\n"+
                    "|....B....|\n"+
                    "|....B....|\n"+
                    "|.........|\n"+
                    "|....B....|\n"+
                    "|.........|\n"+
                    "+---------+",

            "+----------------+\n"+
                    "|B.............B.|\n"+
                    "|.B..........B...|\n"+
                    "|................|\n"+
                    "|..B........B....|\n"+
                    "|....B.....B.....|\n"+
                    "|.....B..B.......|\n"+
                    "|.......B........|\n"+
                    "|......B.........|\n"+
                    "|......B.B.......|\n"+
                    "|.....B....B.....|\n"+
                    "|................|\n"+
                    "|....B......B....|\n"+
                    "|..B.........B...|\n"+
                    "|.B............B.|\n"+
                    "|................|\n"+
                    "|B..............B|\n"+
                    "+----------------+",

            "+--------+\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|..B.....|\n"+
                    "|........|\n"+
                    "|...B....|\n"+
                    "|........|\n"+
                    "|........|\n"+
                    "+--------+",      // no solution

            "+------------------+\n"+
                    "|.B...B...B........|\n"+
                    "|B.................|\n"+
                    "|.....B.B.....B....|\n"+
                    "|..............B...|\n"+
                    "|..................|\n"+
                    "|..............B...|\n"+
                    "|.B.......B........|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|.......B.B........|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|..B........B......|\n"+
                    "|...............B..|\n"+
                    "|..................|\n"+
                    "|..................|\n"+
                    "|..............B...|\n"+
                    "|..................|\n"+
                    "|..........B.......|\n"+
                    "+------------------+",

            "+------------------------+\n"+
                    "|.....................B..|\n"+
                    "|........................|\n"+
                    "|............B...........|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|......B....B.........B..|\n"+
                    "|........................|\n"+
                    "|...................B....|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........B..B............|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........B...............|\n"+
                    "|B.......................|\n"+
                    "|.B......................|\n"+
                    "|.............BB....B....|\n"+
                    "|............B.....B.....|\n"+
                    "|.................B......|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|............B...........|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "|........................|\n"+
                    "+------------------------+"
    };

}
