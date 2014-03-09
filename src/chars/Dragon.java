package chars;

public class Dragon extends GameObject{
    boolean alive = true;

    public Dragon(char rep, int x, int y){
        super(rep, x, y);
    }

    public void move(String direction){
        if(direction.equals("up"))
            super.setPosY(super.getPosY() - 1);
        else if(direction.equals("down"))
            super.setPosY(super.getPosY() + 1);
        else if(direction.equals("right"))
            super.setPosX(getPosX() + 1);
        else if(direction.equals("left"))
            super.setPosX(super.getPosX() - 1);
    }

    public boolean isAlive(){
        return alive;
    }

    public void die(){
        alive = false;
    }
}