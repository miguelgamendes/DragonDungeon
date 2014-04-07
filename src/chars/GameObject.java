package chars;

public abstract class GameObject{
    private int posx = 0;
    private int posy = 0;
    private char representation = ' ';

    public GameObject(){
        posx = -1;
        posy = -1;
        representation = '?';
    }

    public GameObject(char rep)
    {
        representation = rep;
    }

    public GameObject(char rep, int x, int y){
        posx = x;
        posy = y;
        representation = rep;
    }

    //getter functions
    public int getPosX(){
        return posx;
    }

    public int getPosY(){
        return posy;
    }

    public char getRep(){
        return representation;
    }

    //setter function
    public void setPosX(int x){
        posx = x;
    }

    public void setPosY(int y){
        posy = y;
    }

    public void setRep(char c){
        representation = c;
    }
}