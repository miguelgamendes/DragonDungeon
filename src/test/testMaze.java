package test;

import maze.*;
import chars.*;
import org.junit.*;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class MazeTest {
    @Test public void testEmptyMove() {

        Labyrinth lab = new Labyrinth();
        lab.initialParameters();

        lab.moveHero("right");
        assertEquals(2, lab.getHero().getPosX());
        assertEquals(1, lab.getHero().getPosY());

    }

    @Test public void testIllegalMove() {
        Labyrinth lab = new Labyrinth();
        lab.initialParameters();

        lab.moveHero("left");
        assertEquals(1, lab.getHero().getPosX());
        assertEquals(1,lab.getHero().getPosY());
    }

    @Test public void testArmed() {

        Labyrinth lab = new Labyrinth();
        ArrayList<String> commands = new ArrayList<String>();

        commands.add("sleepy");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("left");
        commands.add("left");
        commands.add("left");
        commands.add("down");
        commands.add("down");
        commands.add("down");

        for(int i = 0; i < 14; i++)
            lab.moveHero(commands.get(i));

        assertTrue(lab.getHero().getArmed());
    }

    @Test public void testDies() {
        Labyrinth lab = new Labyrinth();

        lab.moveHero("sleepy");
        lab.moveHero("down");

        assertFalse(lab.getHero().isAlive());
    }

    @Test public void testDragonDies() {

        Labyrinth lab = new Labyrinth();
        ArrayList<String> commands = new ArrayList<String>();

        commands.add("sleepy");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("left");
        commands.add("left");
        commands.add("left");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("up");
        commands.add("up");
        commands.add("up");
        commands.add("up");

        for(int i = 0; i < 18; i++)
            lab.moveHero(commands.get(i));

        assertFalse(lab.getDragon.isAlive());

    }

    @Test public void testWin() {
        Labyrinth lab = new Labyrinth();
        ArrayList<String> commands = new ArrayList<String>();

        commands.add("sleepy");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("left");
        commands.add("left");
        commands.add("left");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("up");
        commands.add("up");
        commands.add("up");
        commands.add("up");
        commands.add("down");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("right");
        commands.add("right");
        commands.add("up");
        commands.add("up");
        commands.add("up");
        commands.add("right");

        for(int i = 0; i < 33; i++)
            lab.moveHero(commands.get(i));

        assertTrue(checkExit());
    }


}