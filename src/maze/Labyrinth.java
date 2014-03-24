package maze;

import chars.*;

import java.util.Random;
import java.util.Scanner;

public class Labyrinth{
    Hero heman = new Hero('H', 1, 1);
    Sword excalibur = new Sword('S', 1, 8);
    Dragon lizzy = new Dragon('D', 1, 3);
    Eagle hedwig;
    Generator gen = new Generator(10);
    //Cell[][] map = new Cell[10][10];
    char[][] drawMap = new char[10][10];
    // TEST MAZE
    char[][] terrain;
    /*char[][] map = {
            {'X','X','X','X','X','X','X','X','X','X'},
            {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ',' ',' ',' ',' ','X'},
            {'X','X','X','X','X','X','X','X','X','X'}};*/

    public void initialParameters(){
        String strategy;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose dragon strategy (sleepy, random, sleepyrandom)");
        strategy = scan.nextLine();
    }

    public void generateMap(){
        //terrain = gen.generateDefault();
        terrain = gen.generateRandomizedDFS();
    }

    public void gameCycle(Labyrinth lab){
        String choice;
        Scanner scan = new Scanner(System.in);

        while(lab.checkEndConditions()){
            print();
            System.out.println("In which direction do you wish to move?");
            choice = scan.nextLine();
            moveHero(choice);
            moveDragons();
            if(hedwig.called)
                moveEagle();
            checkConditions();
        }
    }

    //TODO: optimize this. Print movables over map. No need for so much innefective checking
    public void print(){
        //create terrain
        for(int i = 0; i < 10; i++){
            drawMap[i] = terrain[i].clone();
        }
        //overlay game objects
        drawMap[lizzy.getPosY()][lizzy.getPosX()] = lizzy.getRep();
        drawMap[excalibur.getPosY()][excalibur.getPosX()] = excalibur.getRep();
        drawMap[heman.getPosY()][heman.getPosX()] = heman.getRep();

        //draw map
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print(drawMap[i][j]);
            }
            System.out.println();
        }
    }

    //moves Hero in 'direction', preventing movement to unpassable blocks
    public void moveHero(String direction){
        if(direction.equals("up") && checkPassable(heman.getPosX(), heman.getPosY() - 1))
            heman.move(direction);
        else if(direction.equals("down") && checkPassable(heman.getPosX(), heman.getPosY() + 1))
            heman.move(direction);
        else if(direction.equals("left") && checkPassable(heman.getPosX() - 1, heman.getPosY()))
            heman.move(direction);
        else if(direction.equals("right") && checkPassable(heman.getPosX() + 1, heman.getPosY()))
            heman.move(direction);
        else if(direction.equals("eagle"))
            callEagle(heman.getPosX(), heman.getPosY());
        else
            System.out.println("Command not found/Unpassable block!");
    }

    //moves every dragon
    public void moveDragons(){
        Random rand = new Random();
        int rand2 = rand.nextInt(5);
        switch(rand2){
            case 0:
                break;
            case 1:
                if(checkPassable(lizzy.getPosX(), lizzy.getPosY() - 1))
                    lizzy.move("up");
                break;
            case 2:
                if(checkPassable(lizzy.getPosX(), lizzy.getPosY() + 1))
                    lizzy.move("down");
                break;
            case 3:
                if(checkPassable(lizzy.getPosX() - 1, lizzy.getPosY()))
                    lizzy.move("left");
                break;
            case 4:
                if(checkPassable(lizzy.getPosX() + 1, lizzy.getPosY()))
                    lizzy.move("right");
                break;
        }
    }

    public void callEagle(int x, int y)
    {
        hedwig = new Eagle(x,y);
        hedwig.called = true;
    }

    //returns true if Hero can pass through block in position (x, y)
    private boolean checkPassable(int x, int y){
        if(heman.isArmed()){
            return !(terrain[y][x] == 'X');
        } else {
            return !(terrain[y][x] == 'X' || terrain[y][x] == 'S');
        }
    }

    //arms Hero once his position matches the sword's
    public void checkWeapon(){
        if(excalibur.getPosX() == heman.getPosX() && excalibur.getPosY() == heman.getPosY()){
            heman.arm();
            excalibur.pickUp();
        }
        if(excalibur.getPosX() == lizzy.getPosX() && excalibur.getPosY() == lizzy.getPosY() && excalibur.isPickedUp()){
            lizzy.guardSword();
        } else {
            lizzy.leaveSword();
        }
    }

    //checks important situations, changes game object states accordingly
    public void checkConditions(){
        checkWeapon();
        killDragons();
    }

    //kills dragons in adjacent blocks if Hero is armed
    public void killDragons(){
        if(heman.isArmed() && lizzy.isAlive()){
            //check adjacent cells
            if((heman.getPosX() + 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()) ||
                    (heman.getPosX() - 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()) ||
                    (heman.getPosX() == lizzy.getPosX() && heman.getPosY() - 1 == lizzy.getPosY()) ||
                    (heman.getPosX() == lizzy.getPosX() && heman.getPosY() + 1 == lizzy.getPosY()))
                lizzy.die();
        }
    }

    //ends the cycle if player crosses exit while armed or meets dragon unnarmed
    public boolean checkEndConditions(){
        return !(checkExit() || checkFatalDragons());
    }

    //returns true once hero has found exit while armed
    private boolean checkExit(){
        int exitx = -1;
        int exity = -1;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(terrain[i][j] == 'S'){
                    exitx = j;
                    exity = i;
                }
            }
        }
        return (exitx == heman.getPosX() && exity == heman.getPosY() && heman.isArmed());
    }

    //TODO: I think this can be slightly optimized
    //returns true if Hero has a Dragon in adjacent block while unnarmed
    private boolean checkFatalDragons(){
        if(!heman.isArmed() && lizzy.isAlive() && !lizzy.isSleeping()){
            //check right cell
            if(heman.getPosX() + 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()){
                System.out.println("Death... by fire");
                heman.kill();
                return true;
            }
            //check left cell
            else if(heman.getPosX() - 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()){
                System.out.println("Death... by fire");
                heman.kill();
                return true;
            }
            //check up cell
            else if(heman.getPosX() == lizzy.getPosX() && heman.getPosY() - 1 == lizzy.getPosY()){
                System.out.println("Death... by fire");
                heman.kill();
                return true;
            }
            //check down cell
            else if(heman.getPosX() == lizzy.getPosX() && heman.getPosY() + 1 == lizzy.getPosY()){
                System.out.println("Death... by fire");
                heman.kill();
                return true;
            }
            else
                return false;
        } else
            return false;
    }

    public void findEagleWay() {
        hedwig.wayX = excalibur.getPosX() - hedwig.getPosX();
        hedwig.wayY = excalibur.getPosY() - hedwig.getPosY();
    }

    public void moveEagle() {
        if( hedwig.wayY < 0 && (hedwig.wayX == 0 || Math.abs(hedwig.wayX) > Math.abs(hedwig.wayY)))
        {
            moveEagle("up");
            hedwig.wayY++;
        }
        else if(hedwig.wayY > 0 && (hedwig.wayX == 0 || Math.abs(hedwig.wayY) > Math.abs(hedwig.wayX)))
        {
            moveEagle("down");
            hedwig.wayY--;
        }
        else if(hedwig.wayX < 0 && (hedwig.wayY == 0 || Math.abs(hedwig.wayX) > Math.abs(hedwig.wayY)))
        {
            moveEagle("left");
            hedwig.wayX++;
        }
        else if(hedwig.wayX > 0 && (hedwig.wayY == 0 || Math.abs(hedwig.wayX) > Math.abs(hedwig.wayY)))
        {
            moveEagle("right");
            hedwig.wayX--;
        }
        else if(!(hedwig.caughtSword))
        {
            hedwig.caughtSword = true;
            hedwig.wayX = hedwig.startX;
            hedwig.wayY = hedwig.startY;
        }
        else {} //it means that it is awaiting for it's hero in the initial position
    }

    public void moveEagle(String direction)
    {
        if(direction.equals("up"))
            hedwig.setPosY(hedwig.getPosY() - 1);
        else if(direction.equals("down"))
            hedwig.setPosY(hedwig.getPosY() + 1);
        else if(direction.equals("right"))
            hedwig.setPosX(hedwig.getPosX() + 1);
        else if(direction.equals("left"))
            hedwig.setPosX(hedwig.getPosX() - 1);
    }
}