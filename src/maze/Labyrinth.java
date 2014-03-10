package maze;

import chars.*;

import java.util.Random;

public class Labyrinth{
    Hero heman = new Hero('H', 1, 1);
    Sword excalibur = new Sword('C', 1, 8);
    Dragon lizzy = new Dragon('D', 1, 3);
    //Cell[][] map = new Cell[10][10];
    char[][] drawMap = new char[10][10];
    // TEST MAZE
    char[][] map = {
            {'X','X','X','X','X','X','X','X','X','X'},
            {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ',' ',' ',' ',' ','X'},
            {'X','X','X','X','X','X','X','X','X','X'}};

    /*
    //MAZE GENERATION FUNCTIONS

    public void generateOuterWalls(){
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new Cell();
                if(i == 0 || i == map.length - 1 || j == 0 || j == map[i].length - 1)
                    map[i][j].representation = 'X';
                else
                    map[i][j].representation = ' ';
                map[i][j].posx = j;
                map[i][j].posy = i;
            }
    }

    //TODO: eliminate corner exits (yeah, it's happening)
    public void generateExit(){
        Random rand = new Random();
        //exit on horizontal or vertical wall -> 0 = horizontal
        int rand2 = rand.nextInt(2);
        //decide side -> 0 = left/up
        int rand3 = rand.nextInt(2);
        //determine exit cell
        int rand4 = rand.nextInt(8) + 1;
        if(rand2 == 0){
            if(rand3 == 0)
                map[0][rand4].representation = 'S';
            else
                map[map.length - 1][rand4].representation = 'S';
        } else {
            if(rand3 == 0)
                map[rand4][0].representation = 'S';
            else
                map[map.length - 1][rand4].representation = 'S';
        }
    }

    //TODO: complete this, obviously
    void generateWalls(){
        Stack cellStack;
        Cell currentCell;
        for(int i = 0; i < map.length ; i++)
            for(int j = 0; j < map[i].length; j++)
    }
*/
    //END OF MAZE GENERATION FUNCTIONS

    //TODO: optimize this. Print movables over map. No need for so much innefective checking
    public void print(){
        for(int i = 0; i < 10; i++){
            drawMap[i] = map[i].clone();
        }
        drawMap[heman.getPosY()][heman.getPosX()] = heman.getRep();
        drawMap[lizzy.getPosY()][lizzy.getPosX()] = lizzy.getRep();
        drawMap[excalibur.getPosY()][excalibur.getPosX()] = excalibur.getRep();
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

    //returns true if Hero can pass through block in position (x, y)
    private boolean checkPassable(int x, int y){
        if(heman.isArmed()){
            return !(map[y][x] == 'X');
        } else {
            return !(map[y][x] == 'X' || map[y][x] == 'S');
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

    //ends the cyle if player crosses exit while armed or meets dragon unnarmed
    public boolean checkEndConditions(){
        return !(checkExit() || checkFatalDragons());
    }

    //returns true once hero has found exit while armed
    private boolean checkExit(){
        int exitx = -1;
        int exity = -1;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(map[i][j] == 'S'){
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
        if(!heman.isArmed() && lizzy.isAlive()){
            //check right cell
            if(heman.getPosX() + 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()){
                System.out.println("Death... by fire");
                return true;
            }
            //check left cell
            else if(heman.getPosX() - 1 == lizzy.getPosX() && heman.getPosY() == lizzy.getPosY()){
                System.out.println("Death... by fire");
                return true;
            }
            //check up cell
            else if(heman.getPosX() == lizzy.getPosX() && heman.getPosY() - 1 == lizzy.getPosY()){
                System.out.println("Death... by fire");
                return true;
            }
            //check down cell
            else if(heman.getPosX() == lizzy.getPosX() && heman.getPosY() + 1 == lizzy.getPosY()){
                System.out.println("Death... by fire");
                return true;
            }
            else
                return false;
        } else
            return false;
    }
}