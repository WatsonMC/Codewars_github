package code;

import org.junit.jupiter.api.Test;

public class EgyptionFractionsTest {

    @Test
    public void testGeneral(){
        String n = "1";
        String d = "2";
        String results = EgyptianFractions.decompose(n,d);

        System.out.println(results);
    }
}
