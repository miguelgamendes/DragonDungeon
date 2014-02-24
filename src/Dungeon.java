import java.util.Scanner;
import java.util.Random;
import java.util.Stack;

public class Dungeon{
    public static void main(String[] args){
        String choice;
        Scanner scan = new Scanner(System.in);
        Labyrinth lab = new Labyrinth();
        //maze generation
        lab.generateOuterWalls();
        lab.generateExit();
        //lab.generateWalls();

        //main game cycle
        while(lab.checkEndConditions()){
            lab.print();
            System.out.println("Where do you wanna move, bitch?");
            choice = scan.nextLine();
            lab.moveHero(choice);
            lab.moveDragon();
            lab.checkWeapon();
            lab.killDragons();
        }
    }
}

class Labyrinth{
    Hero heman = new Hero();
    Dragon lizzy = new Dragon();
    Cell[][] map = new Cell[10][10];
    /* TEST MAZE
    char[][] map = {
            {'X','X','X','X','X','X','X','X','X','X'},
            {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X',' ','X','X',' ','X',' ','X',' ','X'},
            {'X','E','X','X',' ',' ',' ',' ',' ','X'},
            {'X','X','X','X','X','X','X','X','X','X'}};*/

    //MAZE GENERATION FUNCTIONS

    void generateOuterWalls(){
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
    void generateExit(){
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
/*
    //TODO: complete this, obviously
    void generateWalls(){
        Stack cellStack;
        Cell currentCell;
        for(int i = 0; i < map.length ; i++)
            for(int j = 0; j < map[i].length; j++)
    }
*/
    //END OF MAZE GENERATION FUNCTIONS

    void print(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(i == heman.posy && j == heman.posx)
                    System.out.print(heman.representation);
                else if(i == lizzy.posy && j == lizzy.posx)
                    System.out.print(lizzy.representation);
                else
                    System.out.print(map[i][j].representation);
            }
            System.out.println();
        }
    }

    void moveHero(String direction){
        if(direction.equals("up") && checkPassable(heman.posx, heman.posy - 1))
            heman.posy--;
        else if(direction.equals("down") && checkPassable(heman.posx, heman.posy + 1))
            heman.posy++;
        else if(direction.equals("left") && checkPassable(heman.posx - 1, heman.posy))
            heman.posx--;
        else if(direction.equals("right") && checkPassable(heman.posx + 1, heman.posy))
            heman.posx++;
        else
            System.out.println("Command not found/Unpassable block!");
    }

    void moveDragon(){
        Random rand = new Random();
        int rand2 = rand.nextInt(5);
        switch(rand2){
            case 0:
                break;
            case 1:
                if(checkPassable(lizzy.posx, lizzy.posy - 1))
                    lizzy.posy--;
                break;
            case 2:
                if(checkPassable(lizzy.posx, lizzy.posy + 1))
                    lizzy.posy++;
                break;
            case 3:
                if(checkPassable(lizzy.posx -1, lizzy.posy))
                    lizzy.posx--;
                break;
            case 4:
                if(checkPassable(lizzy.posx + 1, lizzy.posy))
                    lizzy.posx++;
                break;
        }
    }

    boolean checkPassable(int x, int y){
        if(heman.representation == 'A'){
            if(map[y][x].representation == 'X')
                return false;
            else
                return true;
        } else {
            if(map[y][x].representation == 'X' || map[y][x].representation == 'S')
                return false;
            else
                return true;
        }
    }

    void checkWeapon(){
        int weaponx = -1;
        int weapony = -1;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(map[i][j].representation == 'E'){
                    weaponx = j;
                    weapony = i;
                }
            }
        }
        if(weaponx == heman.posx && weapony == heman.posy){
            heman.representation = 'A';
            map[weapony][weaponx].representation = ' ';
        }
        if(weaponx == lizzy.posx && weapony == lizzy.posy && map[weapony][weaponx].representation == 'E'){
            lizzy.representation = 'F';
        } else {
            lizzy.representation = 'D';
        }
    }

    void killDragons(){
        if(heman.representation == 'A' && lizzy.representation == 'D'){
            //check adjacent cells
            if((heman.posx + 1 == lizzy.posx && heman.posy == lizzy.posy) ||
                    (heman.posx - 1 == lizzy.posx && heman.posy == lizzy.posy) ||
                    (heman.posx == lizzy.posx && heman.posy - 1 == lizzy.posy) ||
                    (heman.posx == lizzy.posx && heman.posy + 1 == lizzy.posy))
                lizzy.representation = ' ';
        }
    }

    //ends the cicle if player crosses exit with sword or meets dragon without one
    boolean checkEndConditions(){
        return !(checkExit() || hereBeDragons());
    }

    //returns true once hero has found exit
    boolean checkExit(){
        int exitx = -1;
        int exity = -1;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(map[i][j].representation == 'S'){
                    exitx = j;
                    exity = i;
                }
            }
        }
        if(exitx == heman.posx && exity == heman.posy && heman.representation == 'A')
            return true;
        else
            return false;
    }


    boolean hereBeDragons(){
        if(heman.representation == 'H' && (lizzy.representation == 'D' || lizzy.representation == 'F')){
            //check right cell
            if(heman.posx + 1 == lizzy.posx && heman.posy == lizzy.posy){
                System.out.println("Death... by fire");
                return true;
            }
            //check left cell
            else if(heman.posx - 1 == lizzy.posx && heman.posy == lizzy.posy){
                System.out.println("Death... by fire");
                return true;
            }
            //check up cell
            else if(heman.posx == lizzy.posx && heman.posy - 1 == lizzy.posy){
                System.out.println("Death... by fire");
                return true;
            }
            //check down cell
            else if(heman.posx == lizzy.posx && heman.posy + 1 == lizzy.posy){
                System.out.println("Death... by fire");
                return true;
            }
            else
                return false;
        } else
            return false;
    }
}

class Hero{
    int posx = 1;
    int posy = 1;
    char representation = 'H';
}

class Dragon{
    int posx = 1;
    int posy = 3;
    char representation = 'D';
}

class Cell{
    int posx;
    int posy;
    char representation = ' ';
    boolean visited = false;
}