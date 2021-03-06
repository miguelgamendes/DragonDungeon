package test;

import maze.Labyrinth;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class MazeTest {
    @Test public void testEmptyMove() {
        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        lab.moveHero("right");
        assertEquals(2, lab.getHero().getPosX());
        assertEquals(1, lab.getHero().getPosY());

    }

    @Test public void testIllegalMove() {
        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        lab.moveHero("left");
        assertEquals(1, lab.getHero().getPosX());
        assertEquals(1,lab.getHero().getPosY());
    }

    @Test public void testArmed() {

        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        ArrayList<String> commands = new ArrayList<String>();

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

        for(int i = 0; i < 13; i++){
            lab.moveHero(commands.get(i));
            lab.checkConditions();
        }

        assertTrue(lab.getHero().isArmed());
    }

    @Test public void testDies() {
        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        lab.moveHero("down");
        lab.checkConditions();
        lab.checkEndConditions();

        assertFalse(lab.getHero().isAlive());
    }

    @Test public void testDragonDies() {

        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        ArrayList<String> commands = new ArrayList<String>();

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

        for(int i = 0; i < 17; i++){
            lab.moveHero(commands.get(i));
            lab.checkConditions();
        }

        assertFalse(lab.getDragon(0).isAlive());

    }

    @Test public void testWin() {
        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        ArrayList<String> commands = new ArrayList<String>();

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

        for(int i = 0; i < 32; i++){
            lab.moveHero(commands.get(i));
            lab.checkConditions();
        }

        assertTrue(lab.checkExit());
    }

    @Test public void testExitNotWin() {
        Labyrinth lab = new Labyrinth();
        lab.generateDefaultMap();

        ArrayList<String> commands = new ArrayList<String>();

        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("right");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("down");
        commands.add("right");

        for(int i = 0; i < 13; i++)
            lab.moveHero(commands.get(i));

        assertTrue(lab.checkEndConditions());
    }

}