package app;

import game.Board;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void test() {
        Board b = new Board(6, "bb ab ac aa");
        System.out.println(b);
        /* assertEquals(2, b.getNbLiberties(0, 2));
        assertEquals(1, b.getNbLiberties(0, 1));
        assertEquals(1, b.getNbLiberties(0, 0));
        assertEquals(3, b.getNbLiberties(1, 1));
        */
        b.play(0,1);
        System.out.println(b);
    }
}