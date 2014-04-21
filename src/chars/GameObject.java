package chars;

import java.util.Random;

public abstract class GameObject{
    private int posx = 0;
    private int posy = 0;
    private char representation = ' ';

    public GameObject(){
        posx = -1;
        posy = -1;
        representation = '?';
    }

    /**
     * Creates a GameObject with the given rep
     * @param rep The representation the GameObject will have
     */
    public GameObject(char rep)
    {
        representation = rep;
    }
    /**
     * Creates a GameObject, with the given rep, in the position (x,y) in the Map.
     *
     * @param rep The representation character of the GameObject
     * @param x The x coordinate the GameObject will be created in
     * @param y The y coordinate the GameObject will be created in
     */
    public GameObject(char rep, int x, int y){
        posx = x;
        posy = y;
        representation = rep;
    }

    //getter functions

    /**
     * Returns the x coordinate of the GameObject
     *
     * @return the x coordinate of the GameObject
     */
    public int getPosX(){
        return posx;
    }

    /**
     * Returns the y coordinate of the GameObject
     *
     * @return the y coordinate of the GameObject
     */
    public int getPosY(){
        return posy;
    }

    /**
     * Returns the representation of the GameObject
     *
     * @return the representation of the GameObject
     */
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

    public void setRandomPos(int n){
        Random rand = new Random();
        posx = rand.nextInt(n);
        posy = rand.nextInt(n);
    }
}