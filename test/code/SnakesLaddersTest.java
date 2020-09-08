package code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakesLaddersTest {

    @Test
    public void runSampleTests(){
        SnakesLadders game = new SnakesLadders();
        assertEquals("Player 1 is on square 38", game.play(1, 1));
        assertEquals("Player 1 is on square 44", game.play(1, 5));
        assertEquals("Player 2 is on square 31", game.play(6, 2));
        assertEquals("Player 1 is on square 25", game.play(1, 1));
    }

}
