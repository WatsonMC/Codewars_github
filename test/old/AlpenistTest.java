package old;
import old.Alpenist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class AlpenistTest {
    static Alpenist alp;
    @BeforeAll
    public static void setUp(){
        alp = new Alpenist();
    }

    @Test
    public void testGetNextVertex(){
        Map<String,Integer> distances = new HashMap<>();

        distances.put("0,0",200);
        distances.put("1,0",110);
        distances.put("2,0",120);
        distances.put("3,0",Integer.MAX_VALUE);
        Set<String> vertices = new HashSet<>();
        vertices.add("0,0");
        vertices.add("1,0");
        vertices.add("2,0");
        vertices.add("3,0");
//        Assertions.assertEquals("1,0",Alpenist.getNextVertexDJ(vertices,distances));
        vertices.remove("1,0");
//        Assertions.assertEquals("2,0",Alpenist.getNextVertexDJ(vertices,distances));



    }

    @Test
    public void testAlpenist(){
      String  maze =
                "0707\n"+
                "7070\n"+
                "0707\n"+
                "7070";

        Assertions.assertEquals(Alpenist.pathFinder(maze),42);
        String  maze2 = "000\n"+
                "000\n"+
                "000";
        Assertions.assertEquals(Alpenist.pathFinder(maze2),0);

       String maze3 = "000000\n"+
                "000000\n"+
                "000000\n"+
                "000010\n"+
                "000109\n"+
                "001010";
        Assertions.assertEquals(Alpenist.pathFinder(maze3),4);
        String maze4 = String.join("\n",
            "700000",
            "077770",
            "077770",
            "077770"
            ,"077770"
            ,"000007");
        Assertions.assertEquals(Alpenist.pathFinder(maze4),14);

    }

    @Test
    public void testGetHeight(){
        String maze1 = "0707\n"+
                "7070\n"+
                "0707\n"+
                "7070";

        Assertions.assertEquals(Alpenist.getHeight("0,0",maze1),0);
        Assertions.assertEquals(Alpenist.getHeight("0,1",maze1),7);
        Assertions.assertEquals(Alpenist.getHeight("2,1",maze1),7);
    }

    @Test
    public void testGetNeighbours(){
        int n =3;
        String origin = "0,0";
        String zeroOne = "0,1";
        String oneZero = "1,0";
        String oneOne = "1,1";

        String oneTwo = "1,2";
        String twoOne = "2,1";
        String twoTwo = "2,2";

        Set<String> n1 = new HashSet<>();
        n1.add(oneZero);
        n1.add(zeroOne);
        Assertions.assertEquals(Alpenist.getNeighbours(origin,n),n1);

        n1.add(twoOne);
        n1.add(oneTwo);
        Assertions.assertEquals(Alpenist.getNeighbours(oneOne,n),n1);
    }
}
