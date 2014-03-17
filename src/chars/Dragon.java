package chars;

import java.util.Random;

public class Dragon extends GameObject{
    private boolean alive = true;
    private boolean sleeping = false;
    private boolean onSword = false;
    private int sleepTimer = 0;
    private int awakeTimer = 0;

    public Dragon(char rep, int x, int y){
        super(rep, x, y);
        Random rand = new Random();
        awakeTimer = rand.nextInt(10) + 1;
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
        Random rand = new Random();
        sleepTimer = rand.nextInt(10) + 1;
        sleeping = true;
        setRep('d');
    }

    public void wakeUp(){
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
        setRep(' ');
    }

    public boolean isSleeping(){
        return sleeping;
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
        if(alive){
            if(sleeping)
                setRep('d');
            else
                setRep('D');
        } else
            setRep(' ');
    }
}