package old;


import old.AlphabeticAnagrams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class TestAlphabeticAnagrams {
    static AlphabeticAnagrams ana;
    @BeforeAll
    public static void setUP(){
       ana = new AlphabeticAnagrams();
    }

    @Test
    public void testFactorials(){
        ana.initFactorials();
        Assertions.assertEquals(ana.factorials.get(10) ,BigInteger.valueOf(3628800));
        Assertions.assertEquals(ana.factorials.get(11) ,BigInteger.valueOf(39916800));


    }

    @Test
    public void testAnagrams(){
        String word1 = "BAAA";
        BigInteger word1Val = ana.listPosition(word1);
        Assertions.assertEquals(BigInteger.valueOf(4),word1Val);

        String word2 = "ABAB";
        BigInteger word2Val = ana.listPosition(word2);
        Assertions.assertEquals(BigInteger.valueOf(2),word2Val);


        String q = "QUESTION";
        BigInteger qval = ana.listPosition(q);
    }

    @Test
    public void testGetSubsetSize(){
        List<String> letters = new LinkedList<>();
        letters.add("A");
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");
        //5!/2! =60
        ana.initFactorials();
        Assertions.assertEquals(BigInteger.valueOf(60/5),ana.getSubsetSize(letters));

        letters.add("B");
        Assertions.assertEquals(BigInteger.valueOf(180/6),ana.getSubsetSize(letters));

    }

    @Test
    public void testRemove(){
        List<String> letters = new LinkedList<>();
        letters.add("A");
        letters.add("A");
        letters.add("B");
        letters.add("C");
        letters.add("D");

        List<String> res1 = new LinkedList<>();
        res1.add("A");
        res1.add("B");
        res1.add("C");
        res1.add("D");



        System.out.println(letters.toString());
        letters.remove("A");
        Assertions.assertEquals(letters, res1);


    }


    @Test
    public void testSortLetters(){
        String word1 = "CBA";
        List<String> w1 = new LinkedList<>();
        w1.add("A");
        w1.add("B");
        w1.add("C");
        Assertions.assertEquals(w1, ana.sortLetters(word1));

        String word2 = "ZBBAACZ";
        List<String> w2= new LinkedList<>();
        w2.add("A");
        w2.add("A");
        w2.add("B");
        w2.add("B");
        w2.add("C");
        w2.add("Z");
        w2.add("Z");
        Assertions.assertEquals(w2, ana.sortLetters(word2));



    }
}
