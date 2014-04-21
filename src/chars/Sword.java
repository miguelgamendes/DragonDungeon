package chars;

public class Sword extends GameObject{
    boolean pickedUp = false;

    /**
     * Creates a Sword, with the given representation, in the given position (x,y)
     *
     * @param rep The representation of the Sword
     * @param x The x coordinate the Sword will be created in
     * @param y The y coordinate the Sword will be created in
     */
    public Sword(char rep, int x, int y){
        super(rep, x, y);
    }

    /**
     * Returns if the Sword is picked up or not.
     *
     * @return Returns if the Sword is picked up or not
     */
    public boolean isPickedUp(){
        return pickedUp;
    }

    /**
     * Picks the Sword up.
     */
    public void pickUp(){
        pickedUp = true;
        setRep(' ');
    }
}