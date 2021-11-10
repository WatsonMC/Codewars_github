package code;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class zooTest {
    @Test
    public void example() {
        final String input = "fox,bug,chicken,grass,sheep";
        final String[] expected = 	{
                "fox,bug,chicken,grass,sheep",
                "chicken eats bug",
                "fox eats chicken",
                "sheep eats grass",
                "fox eats sheep",
                "fox"};
        assertArrayEquals(expected, ZooDisaster.whoEatsWho(input));
    }

}
