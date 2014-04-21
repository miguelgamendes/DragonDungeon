package chars;

/**
 * Created by wake on 3/24/14.
 */
public class Eagle extends GameObject {
    public boolean called = false;
    public int wayX;
    public int wayY;
    public int startX;
    public int startY;
    public boolean caughtSword = false;

    /**
     * Creates the Eagle with the rep being 'A'.
     */
    public Eagle() {
        super('A');
    }

    /**
     * Kills the Eagle, after the Hero gets the Sword the Eagle had.
     */
    public void kill() {
        setRep(' ');
    }
}
