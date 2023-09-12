import code.BreakPieces;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BreakPiecesTest {

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

        //test grid get x and y

        Assertions.assertEquals(10, grid.getXLim());
        Assertions.assertEquals(5,grid.getYLim());
    }


}
