package old;

import old.BirdMountain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class TestBirdMountain {


    @Test
    public void testGetHeight(){
        char[][] mountain = {
                "     ^^^^^^".toCharArray(),
                " ^^^^^^^^  ".toCharArray(),
                "^^^^^^^^^  ".toCharArray(),
                "  ^^^^^^^^ ".toCharArray(),
                "  ^^^^^^^^^".toCharArray(),
                "^^^^^^^^^^^".toCharArray(),
                "^^^^^^^^^^^".toCharArray(),
                "  ^^^^^^^^^".toCharArray(),
                "  ^^^^^^^^ ".toCharArray(),
                "  ^^^^^^^  ".toCharArray(),
                "  ^^^^^^   ".toCharArray(),
                "   ^^^^^^  ".toCharArray(),
                "    ^^^^^  ".toCharArray(),
                "      ^^   ".toCharArray()
        };
        Assertions.assertEquals(5, BirdMountain.getHeight(mountain,6,5));
    }
}
