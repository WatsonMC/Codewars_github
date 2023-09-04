import code.litteTyper.LittleTyper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

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

        String bt2 = ("( asdf 123) ((asdf))(123 )()()");
        Assert.assertEquals("( asdf 123 ) ( ( asdf ) ) ( 123 ) ( ) ( )",LittleTyper.cleanSpaces(bt2) );

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

    @Test
    public void testCleanParenth(){
        String testSimple = "( test )";
        String testSimple2 = " ( test ( test2 ) )";
        String testSimple3 = " () () test (test (test) )";
        Assertions.assertEquals("test", LittleTyper.cleanParentheses(testSimple));
        Assertions.assertEquals("( test test2 )", LittleTyper.cleanParentheses(testSimple2));
        Assertions.assertEquals("test ( test test )", LittleTyper.cleanParentheses(testSimple3));


    }

    @Test
    public void testFunction(){
        String func1 = "func1 : A -> B";
        List<String> func1List =new ArrayList<String>(){{
            add("A");
            add("B");
        }};
        //chec name
        //heck types
        LittleTyper.Function f1 = new LittleTyper.Function(func1);
        Assertions.assertEquals(f1.getName() ,"func1");
        Assertions.assertEquals(f1.getTypes(), func1List );


        String func2 = "func2 : A -> ( B -> C ) -> D";
        List<String> func2List =new ArrayList<String>(){{
            add("A");
            add("B -> C");
            add("D");
        }};
        LittleTyper.Function f2 = new LittleTyper.Function(func2);
        Assertions.assertEquals(func2List, f2.getTypes() );

        String func3 = "Function : (A->B) -> ((A->B) -> (A->B))";
        List<String> func3List =new ArrayList<String>(){{
            add("A -> B");
            add("( A -> B ) -> ( A -> B )");
        }};
        LittleTyper.Function f3 = new LittleTyper.Function(func3);
        Assertions.assertEquals(func3List, f3.getTypes() );

    }
}
