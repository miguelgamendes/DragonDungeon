package chars;

import java.util.Random;

public class Dragon extends GameObject{
    boolean alive = true;
    boolean sleeping = false;
    boolean onSword = false;
    int sleepTimer = 0;
    int awakeTimer = 0;

    public Dragon(char rep, int x, int y){
        super(rep, x, y);
    }

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

    public void sleep(){
        System.out.println("Sleeping"); //for debug purposes
        Random rand = new Random();
        sleepTimer = rand.nextInt(10) + 1;
        sleeping = true;
        setRep('d');
    }

    public void wakeUp(){
        System.out.println("Waking up");
        Random rand = new Random();
        awakeTimer = rand.nextInt(10) + 1;
        sleeping = false;
        setRep('D');
    }

    public boolean isAlive(){
        return alive;
    }

    public void die(){
        alive = false;
    }

    public void guardSword(){
        onSword = true;
        if(alive)
            setRep('F');
        else
            setRep(' ');
    }

    public void leaveSword(){
        onSword = false;
        if(alive)
            setRep('D');
        else
            setRep(' ');
    }
}