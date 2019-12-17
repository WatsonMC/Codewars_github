import code.Ziggurat;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;
import java.util.*;
import java.awt.Point;

public class ZigguratsTest {

        @Test
        public void SampleTests() {
            for (int i=0 ; i < artifacts.length ; i++)
                assertEquals(solutions.get(i), Ziggurat.rideOfFortune(artifacts[i], explorers[i]));
        }

        private static String[][] artifacts = {
                {
                        "      ",
                        " A  A ",
                        "      ",
                        "      ",
                        " A  B ",
                        "      "},

                {
                        " A    A ",
                        "  A  B  ",
                        "    B   ",
                        "   B    ",
                        "    A A ",
                        "  B  A  ",
                        "   A    ",
                        " B    B "},

                {
                        " A  A  A",
                        "   A    ",
                        "     A  ",
                        " A    A ",
                        "  A     ",
                        "    A A ",
                        " A A   A",
                        "  A  A  "},

                {
                        " A   A  ",
                        "  B   A ",
                        "   A   B",
                        "    B   ",
                        " B   A  ",
                        "  A   B ",
                        "   B    ",
                        " B  A  A"},

                {
                        "   A    B ",
                        " A        ",
                        "  B    A  ",
                        "     A    ",
                        "    B  B  ",
                        "  B     B ",
                        "   A   A  ",
                        " B    B   ",
                        "    A   A ",
                        "  A  B    "}};

        private static int[][] explorers = {
                {1,1,0,3,4,4,2,5,1,4},
                {0,1,2,3,4,5,6,7,3,6,3,1,2,6,1},
                {1,3,6,3,5,0,7,3,1,2,0,7,5,3,6,2,1,1,6,3,4,6,2},
                {3,2,5,6,1,5,1,0,3,2,0,4,5,2,0,1,4,0,2,5,6,2,6,5,5,0,3,0,1,4,2,3},
                {8,3,2,1,9,0,2,6,4,7,8,1,4,1,7,4,1,6,3,2,7,8,1,0,9,0,2,6,8,3,7,1,3,1,6,8,4,6,9,6,2,7}
        };

        private static List<List<Point>> solutions = Arrays.asList(
                Arrays.asList(null,new Point(5,4),new Point(0,5),new Point(3,5),new Point(0,4),new Point(5,1),new Point(2,5),new Point(5,5),null,new Point(5,1)),
                Arrays.asList(null,null,new Point(0,4),new Point(0,3),new Point(7,4),new Point(7,2),new Point(7,3),new Point(7,1),null,new Point(7,3),new Point(0,3),new Point(0,2),null,null,null),
                Arrays.asList(new Point(7,7),new Point(1,7),new Point(5,7),new Point(7,3),new Point(7,4),null,new Point(7,2),null,new Point(0,6),new Point(7,7),new Point(0,1),null,new Point(0,1),null,new Point(7,1),new Point(0,5),new Point(0,3),null,new Point(7,1),null,new Point(0,2),new Point(7,1),null),
                Arrays.asList(new Point(0,4),null,new Point(7,2),new Point(7,3),new Point(0,2),null,new Point(0,2),null,new Point(7,7),new Point(0,3),new Point(0,1),null,new Point(7,2),null,null,null,new Point(3,7),new Point(0,1),new Point(0,3),new Point(7,2),new Point(7,3),null,new Point(7,3),null,new Point(0,6),null,new Point(2,7),new Point(0,1),new Point(7,6),null,new Point(0,3),new Point(0,4)),
                Arrays.asList(new Point(9,4),new Point(0,9),new Point(0,2),null,null,new Point(9,7),null,new Point(5,9),new Point(0,4),new Point(9,1),null,new Point(0,1),new Point(0,4),null,new Point(9,1),new Point(9,8),new Point(0,1),new Point(9,3),new Point(0,5),new Point(0,2),null,new Point(0,2),new Point(0,1),null,new Point(9,2),new Point(0,3),new Point(0,2),new Point(9,3),new Point(9,4),new Point(9,9),new Point(9,1),null,new Point(0,5),new Point(0,1),null,null,new Point(0,4),new Point(9,3),null,new Point(0,8),null,new Point(9,1))
        );
    }

