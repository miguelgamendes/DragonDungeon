package chars;

import java.util.Random;

public class Dragon extends GameObject{
    private boolean alive = true;
    private boolean sleeping = false;
    private boolean onSword = false;
    private int sleepTimer = 0;
    private int awakeTimer = 0;

    /**
     * Creates a Dragon, with the given rep, in the position (x,y) in the Map.
     *
     * @param rep The representation character of the Dragon
     * @param x The x coordinate the Dragon will be created in
     * @param y The y coordinate the Dragon will be created in
     */
    public Dragon(char rep, int x, int y){
        super(rep, x, y);
        Random rand = new Random();
        awakeTimer = rand.nextInt(10) + 1;
    }

    /**
     * Creates a Dragon with the given rep.
     *
     * @param rep The representation character of the Dragon
     */
    public Dragon(char rep)
    {
        super(rep);
    }

    /**
     * Moves the Dragon in a given direction.
     *
     * @param direction The direction the Dragon will be moved in
     */
    public void move(String direction){
        if(!sleeping){
            if(awakeTimer == 0)
                sleep();
            else{
                if(direction.equals("up"))
                    super.setPosY(super.getPosY() - 1);
                else if(direction.equals("down"))
                    super.setPosY(super.getPosY() + 1);
                else if(direction.equals("right"))
                    super.setPosX(getPosX() + 1);
                else if(direction.equals("left"))
                    super.setPosX(super.getPosX() - 1);

                awakeTimer--;
            }
        } else {
            if(sleepTimer == 0)
                wakeUp();
            else
                sleepTimer--;
        }
    }

    /**
     * Puts the Dragon asleep, for a randomly generated number of turns between 1 and 10.
     */
    public void sleep(){
        Random rand = new Random();
        sleepTimer = rand.nextInt(10) + 1;
        sleeping = true;
        setRep('d');
    }

    /**
     * Awakens the Dragon, for a randomly generated number of turns between 1 and 10.
     */
    public void wakeUp(){
        Random rand = new Random();
        awakeTimer = rand.nextInt(10) + 1;
        sleeping = false;
        setRep('D');
    }

    /**
     * Returns a boolean telling if the Dragon is alive.
     *
     * @return true if the Dragon is alive, false otherwise
     */
    public boolean isAlive(){
        return alive;
    }

    /**
     *  Kills the Dragon, if the killing conditions (Hero is Armed and in an adjacent position to the Dragon).
     */
    public void die(){
        alive = false;
        setRep(' ');
    }

    /**
     * Returns a boolean telling if the Dragon is asleep.
     *
     * @return true if the Dragon is asleep, false otherwise
     */
    public boolean isSleeping(){
        return sleeping;
    }

    /**
     * Changes the representation of the Dragon, if the Dragon and the Sword are in the same position.
     */
    public void guardSword(){
        onSword = true;
        if(alive)
            setRep('F');
        else
            setRep(' ');
    }

    /**
     * Changes the representation of the Dragon to the default, if the Dragon leaves the position of the Sword.
     */
    public void leaveSword(){
        onSword = false;
        if(alive){
            if(sleeping)
                setRep('d');
            else
                setRep('D');
        } else
            setRep(' ');
    }
}