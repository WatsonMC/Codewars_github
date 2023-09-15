import code.BreakPieces;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BreakPiecesTest {
    static BreakPieces bp_common = new BreakPieces();
    static BreakPieces bp_complex = new BreakPieces();

    static String testgrid;
    static String complicatedGrid;

    static String testMulti;

    static String testInterlock;

    static BreakPieces.DiaGrid grid_common;
    static BreakPieces.DiaGrid grid_complex;

    @BeforeClass
    public static void setup(){
        testgrid =   "+---+" + "\n" +
                            "|   |"+ "\n" +
                            "|   |" +"\n" +
                            "+---+";
        grid_common = bp_common.gridGen(testgrid);

        complicatedGrid =   "+---+   " + "\n" +
                                    "|   +--+"+ "\n" +
                                    "|      |" +"\n" +
                                    "+-+    |" +"\n" +
                                    "  +----+";

        grid_complex = bp_complex.gridGen(complicatedGrid);

        testMulti = "+------------+\n" +
        "|            |\n" +
        "|            |\n" +
        "|            |\n"+
        "+------+-----+\n"+
        "|      |     |\n"+
        "|      |     |\n"+
        "+------+-----+";

        testInterlock = "+-------------------+--+\n"+
                "|                   |  |\n"+
                "|                   |  |\n"+
                "|  +----------------+  |\n"+
                "|  |                   |\n"+
                "|  |                   |\n"+
                "+--+-------------------+";

    }

    @Test
    public void testAll(){
        String[] commonShapeStrigs = BreakPieces.process(testgrid);
        String[] complexShapes = BreakPieces.process(complicatedGrid);
        String[] multiShapes = BreakPieces.process(testMulti);
        String[] interlock = BreakPieces.process(testInterlock);

        for(String shape: complexShapes){
            System.out.println(shape);
        }
        for(String shape: commonShapeStrigs){
            System.out.println(shape);
        }
        for(String shape: multiShapes){
            System.out.println(shape);
        }
        for(String shape: interlock){
            System.out.println(shape);
        }


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
    public void testShapeTravsersal(){
        //Simple test
        Set<BreakPieces.Node> shapeNodes = BreakPieces.traverseShape(grid_common.getNode(1,1),grid_common);
        List<BreakPieces.Node> expectedNodes = new ArrayList<>();
        expectedNodes.add(new BreakPieces.Node(1,1," "));
        expectedNodes.add(new BreakPieces.Node(2,1," "));
        expectedNodes.add(new BreakPieces.Node(3,1," "));
        expectedNodes.add(new BreakPieces.Node(1,2," "));
        expectedNodes.add(new BreakPieces.Node(2,2," "));
        expectedNodes.add(new BreakPieces.Node(3,2," "));

        Assertions.assertEquals(expectedNodes.size(), shapeNodes.size());
        Assertions.assertTrue(shapeNodes.containsAll(expectedNodes));

        //complicated
        String testgrid =   "+---+   " + "\n" +
                            "|   +--+"+ "\n" +
                            "|      |" +"\n" +
                            "+-+    |" +"\n" +
                            "  +----+";
        BreakPieces.DiaGrid grid2 = new BreakPieces.DiaGrid(testgrid);
        shapeNodes = BreakPieces.traverseShape(grid2.getNode(1,1),grid2);
        expectedNodes = new ArrayList<>();
        expectedNodes.add(new BreakPieces.Node(1,1," "));
        expectedNodes.add(new BreakPieces.Node(2,1," "));
        expectedNodes.add(new BreakPieces.Node(3,1," "));

        expectedNodes.add(new BreakPieces.Node(1,2," "));
        expectedNodes.add(new BreakPieces.Node(2,2," "));
        expectedNodes.add(new BreakPieces.Node(3,2," "));
        expectedNodes.add(new BreakPieces.Node(4,2," "));
        expectedNodes.add(new BreakPieces.Node(5,2," "));
        expectedNodes.add(new BreakPieces.Node(6,2," "));

        expectedNodes.add(new BreakPieces.Node(3,3," "));
        expectedNodes.add(new BreakPieces.Node(4,3," "));
        expectedNodes.add(new BreakPieces.Node(5,3," "));
        expectedNodes.add(new BreakPieces.Node(6,3," "));


        Assertions.assertEquals(expectedNodes.size(), shapeNodes.size());
        Assertions.assertTrue(shapeNodes.containsAll(expectedNodes));

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

    @Test
    public void testNodeEquals(){
        BreakPieces.Node node1 = new BreakPieces.Node(1,1," ");
        BreakPieces.Node node2 = new BreakPieces.Node(1,1," ");
        Assertions.assertEquals(node1,node2);

        Set<BreakPieces.Node> testSet =  new HashSet<>();
        testSet.add(node1);
        Assertions.assertTrue(testSet.contains(node2));

    }

    @Test
    public void testPrintShape(){
        Set<BreakPieces.Node> shape = BreakPieces.traverseShape(new BreakPieces.Node(1,1," "),grid_common);
        String printString = BreakPieces.printShape(shape);
        System.out.println(printString);

        shape = BreakPieces.traverseShape(new BreakPieces.Node(1,1," "),grid_complex);
        printString = BreakPieces.printShape(shape);
        System.out.println(printString);
    }
}

