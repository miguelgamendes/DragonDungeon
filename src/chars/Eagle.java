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

    public Eagle() {
        super('A');
    }

    public void kill() {
        setRep(' ');
    }
}
