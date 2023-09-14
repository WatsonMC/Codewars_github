import code.BreakPieces;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class BreakPiecesTest {
    static BreakPieces bp_common = new BreakPieces();
    static BreakPieces.DiaGrid grid_common;
    @BeforeClass
    public static void setup(){
        String testgrid =   "+---+" + "\n" +
                            "|   |"+ "\n" +
                            "|   |" +"\n" +
                            "+---+";
        grid_common = bp_common.gridGen(testgrid);
    }

    @Test
    public void testGridCreation(){
        BreakPieces bp = new BreakPieces();
        String testgrid = "+---+" + "\n" +
                            "|   |"+ "\n" +
                            "|   |" +"\n" +
                            "+---+";

        BreakPieces.DiaGrid grid = bp.gridGen(testgrid);
        Assertions.assertEquals(grid.printGrid(), testgrid);

        String testgrid2 =  "  +---+   " + "\n" +
                            "  |   |   "+ "\n" +
                            "+-+   +--+" +"\n" +
                            "| |      |" +"\n" +
                            "+-+---+--+";

        grid = bp.gridGen(testgrid2);
        Assertions.assertEquals(grid.printGrid(), testgrid2);

        //test grid get x and y limits
        Assertions.assertEquals(10, grid.getXLim());
        Assertions.assertEquals(5,grid.getYLim());
    }

    @Test
    public void testGetNeighbours(){
        //test first full shape
        List<BreakPieces.Node> actuals =  grid_common.getNeighbours(1,1);
        List<BreakPieces.Node> expected = new ArrayList<>() ;
        expected.add(new BreakPieces.Node(0,1, "|"));
        expected.add(new BreakPieces.Node(0,0, "+"));
        expected.add(new BreakPieces.Node(1,0, "-"));
        expected.add(new BreakPieces.Node(2,0, "-"));
        expected.add(new BreakPieces.Node(2,1, " "));
        expected.add(new BreakPieces.Node(2,2, " "));
        expected.add(new BreakPieces.Node(1,2, " "));
        expected.add(new BreakPieces.Node(0,2, "|"));
        for(int i= 0; i<8;i++){
            Assertions.assertTrue(expected.get(i).equals(actuals.get(i)));
        }

        //test boundaries (top left)
        expected = new ArrayList<>() ;  //target 0,0
        actuals =  grid_common.getNeighbours(0,0);
        expected.add(new BreakPieces.Node(-1,0, "N"));
        expected.add(new BreakPieces.Node(-1,-1, "N"));
        expected.add(new BreakPieces.Node(0,-1, "N"));
        expected.add(new BreakPieces.Node(1,-1, "N"));
        expected.add(new BreakPieces.Node(1,0, "-"));
        expected.add(new BreakPieces.Node(1,1, " "));
        expected.add(new BreakPieces.Node(0,1, "|"));
        expected.add(new BreakPieces.Node(-1,1, "N"));
        for(int i= 0; i<8;i++){
//            System.out.println(i);
            Assertions.assertTrue(expected.get(i).equals(actuals.get(i)));
        }

        //Test bottom right (4,3)
        expected = new ArrayList<>() ;  //target 0,0
        actuals =  grid_common.getNeighbours(4,3);
        expected.add(new BreakPieces.Node(3,3, "-"));
        expected.add(new BreakPieces.Node(3,2, " "));
        expected.add(new BreakPieces.Node(4,2, "|"));
        expected.add(new BreakPieces.Node(5,2, "N"));
        expected.add(new BreakPieces.Node(5,3, "N"));
        expected.add(new BreakPieces.Node(5,4, "N"));
        expected.add(new BreakPieces.Node(4,4, "N"));
        expected.add(new BreakPieces.Node(3,4, "N"));
        for(int i= 0; i<8;i++){
//            System.out.println(i);
            Assertions.assertTrue(expected.get(i).equals(actuals.get(i)));
        }
    }

    @Test
    public void testCornerDetection(){
        String[] yesTest = {
                "+++",
                "++-",
                "|+-",
                "|++",
        };
        for(String yes:yesTest){
            Assertions.assertTrue(bp_common.matchCorner(yes));
        }
        String[] noTest = {
                "+-+",
                "--+",
                "|-+",
        };
        for(String no:noTest){
            Assertions.assertFalse(bp_common.matchCorner(no));

        }
    }


}
