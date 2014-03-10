package chars;

public class Sword extends GameObject{
    boolean pickedUp = false;

    public Sword(char rep, int x, int y){
        super(rep, x, y);
    }

    public boolean isPickedUp(){
        return pickedUp;
    }

    public void pickUp(){
        pickedUp = true;
        setRep(' ');
    }
}