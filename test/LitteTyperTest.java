import code.litteTyper.LittleTyper;
import org.junit.Assert;
import org.junit.Test;

public class LitteTyperTest {

    @Test
    public void testCleanSpaces(){
        String testGeneral = "123 456  123      456";
        Assert.assertEquals("123 456 123 456", LittleTyper.cleanSpaces(testGeneral));

        String testNoChange = "1 2 34";
        Assert.assertEquals(testNoChange, LittleTyper.cleanSpaces(testNoChange));

        String testNoSpace = "12341234";
        Assert.assertEquals(testNoSpace, LittleTyper.cleanSpaces(testNoSpace));

        String testOnlySpace = "     ";
        Assert.assertEquals(" ", LittleTyper.cleanSpaces(testOnlySpace));

        //testing brackets
        String bt1 = "(s1)";
        Assert.assertEquals("( s1 )",LittleTyper.cleanSpaces(bt1) );

        String bt2 = ("( asdf 123) ((asdf))(123 )");
        Assert.assertEquals("( asdf 123 ) ( ( asdf ) ) ( 123 )",LittleTyper.cleanSpaces(bt2) );

        //testing arrows
        String at1 = "( asdf->123)";
        Assert.assertEquals("( asdf -> 123 )",LittleTyper.cleanSpaces(at1));

        //testing semi colons
        String sc1 = "concat:(List -> List -> List)";
        Assert.assertEquals("concat : ( List -> List -> List )",LittleTyper.cleanSpaces(sc1));

        String contextHard = "id : (A -> B) -> (B -> A) -> (A -> A)\n" +
                "    inv : (A -> B) -> (B -> A)\n" +
                "    a:A\n" +
                "    b:B\n" +
                "    c:A\n" +
                "    d:B\n" +
                "    aB : A -> B\n" +
                "    bA : B -> A";
        String contextHardResult = "id : ( A -> B ) -> ( B -> A ) -> ( A -> A )\n" +
                " inv : ( A -> B ) -> ( B -> A )\n" +
                " a : A\n" +
                " b : B\n" +
                " c : A\n" +
                " d : B\n" +
                " aB : A -> B\n" +
                " bA : B -> A";
        Assert.assertEquals(contextHardResult,LittleTyper.cleanSpaces(contextHard));

    }
}
