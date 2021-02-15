import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class TestBoggleWords {

    final private static char[][] board = {
            {'E','A','R','A'},
            {'N','L','E','C'},
            {'I','A','I','S'},
            {'B','Y','O','R'}
    };

    final private static char[][] random = {    //expected MFFNPLMMGMUQ  = true, but was false
            {'U','D','E','Z','Q','U','M','J','E','N','K','F','C','W','H'},
            {'X','S','O','P','V','F','A','I','V','Z','O','U','U','Q','A'},
            {'C','J','Q','Y','U','U','F','T','Y','Y','H','G','X','Y','E'},
            {'W','H','R','Z','J','J','N','T','T','F','V','A','I','Q','G'},
            {'V','X','E','G','O','P','F','Z','K','R','C','H','B','O','Q'},
            {'E','X','C','Q','L','B','G','X','L','F','Y','P','Y','G','V'},
            {'A','L','C','J','M','M','U','E','X','B','Y','G','J','S','I'},
            {'C','R','M','G','D','A','M','S','B','Y','E','Y','A','E','M'},
            {'B','I','B','G','W','K','G','W','K','S','V','S','U','U','E'},
            {'R','K','R','A','H','M','A','C','P','A','A','N','U','S','S'},
            {'B','S','T','Q','U','N','T','T','Z','L','I','Q','U','R','R'},
            {'L','G','H','H','V','Q','Z','L','V','L','J','Z','Y','N','W'},
            {'K','Y','N','K','E','F','Z','K','X','L','H','V','X','K','S'},
            {'U','Q','H','V','F','T','O','J','F','C','L','J','Z','U','Q'},
            {'K','G','C','M','O','B','Z','P','Y','O','H','A','Y','T','R'}
    };


    private static String[]  toCheck   = {"C", "EAR","EARS","BAILER","RSCAREIOYBAILNEA" ,"CEREAL" ,"ROBES"};
    private static boolean[] expecteds = {true, true, false, true,    true,               false,    false };

    @Test
    public void sampleTests() {
        for (int i=0 ; i < toCheck.length ; i++) {
            System.out.println(i);
            Assert.assertEquals(expecteds[i], new code.Boggle(deepCopy(board), toCheck[i]).check() );
        }
    }

    @Test
    public void testRandom(){
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
        Assert.assertEquals(true, new code.Boggle(random,"MFFNPLMMGMUQ").check());
    }

    private char[][] deepCopy(char[][] arr) {
        return Arrays.stream(arr)
                .map( a -> Arrays.copyOf(a, a.length) )
                .toArray(char[][]::new);
    }
}
