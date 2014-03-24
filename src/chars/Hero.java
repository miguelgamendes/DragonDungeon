package chars;

public class Hero extends GameObject{
    private boolean armed = false;
    private boolean alive = true;

    public Hero(char rep, int x, int y){
        super(rep, x, y);
    }

    public char getRep(){
        return armed ? 'A' : 'H';
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

    public boolean isArmed(){
        return armed;
    }

    public void arm(){
        armed = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }
}