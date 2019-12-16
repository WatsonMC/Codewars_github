import code.HiddenCubics;
import org.junit.Test;

public class TestHiddenCubics {

    @Test
    public void test1(){
        String s1 = "aqdf& 0 1 xyz 153 777.777          212341234123412341234123434 DSAVASDF 123R  SDFGASDFASDF";
        HiddenCubics hc = new HiddenCubics();
        System.out.println(hc.isSumOfCubes(s1));


       System.out.println(hc.isSumOfCubes("ASDFASDFASDF"));
    }
}
