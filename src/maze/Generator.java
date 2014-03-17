package maze;

import java.util.Random;
import java.util.Stack;

public class Generator{
    int side;
    char[][] map;
    int[][] visitTable;
    String exitPlacement;
    int exitx;
    int exity;

    public Generator(int s){
        side = s;
    }

    public char[][] generateDefault(){
        map = new char[][]{
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
        return map;
    }

    public char[][] generateRandomizedDFS(){
        map = new char[side][side];
        generateInnerWalls();
        generateExit();
        generateVisitTable();
        depthFirstSearch();
        return map;
    }

    //creates a map with enclosed outer and inner walls of predetermined size
    public void generateInnerWalls(){
        for(int i = 0; i < map.length; i++)
            for(int j = 0; j < map[i].length; j++){
                if(i == 0 || i == map.length - 1 || j == 0 || j == map[i].length - 1 || i % 2 == 0 || j % 2 == 0)
                    map[i][j] = 'X';
                else
                    map[i][j] = ' ';
            }
    }

    //TODO: eliminate corner exits (yeah, it's happening)
    //generates an exit in a pre-created map
    public void generateExit(){
        Random rand = new Random();
        //exit on horizontal or vertical wall -> 0 = horizontal
        int rand2 = rand.nextInt(2);
        //decide side -> 0 = left/up
        int rand3 = rand.nextInt(2);
        //determine exit cell
        int rand4 = rand.nextInt(side - 2) + 1;
        if(rand2 == 0){
            if(rand3 == 0){
                map[0][rand4] = 'S';
                exitPlacement = "top";
                exitx = rand4;
                exity = 0;
            } else {
                map[map.length - 1][rand4] = 'S';
                exitPlacement = "bottom";
                exitx = rand4;
                exity = map.length - 1;
            }
        } else {
            if(rand3 == 0){
                map[rand4][0] = 'S';
                exitPlacement = "left";
                exitx = 0;
                exity = rand4;
            } else {
                map[map.length - 1][rand4] = 'S';
                exitPlacement = "right";
                exitx = rand4;
                exity = map.length - 1;
            }
        }
    }

    //generates a table in which a '0' is a walled space, '1' is a unvisited space and '2' is a visited one
    public void generateVisitTable(){
        visitTable = new int[side][side];
        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++)
                if(i % 2 != 0 && j % 2 != 0)
                    visitTable[i][j] = 0;
                else
                    visitTable[i][j] = 2;
    }

    //finalized the map through a depth first search
    public void depthFirstSearch(){
        if(exitPlacement.equals("top"))
            recSearch(exitx, exity + 1);
        if(exitPlacement.equals("left"))
            recSearch(exitx + 1, exity);
        if(exitPlacement.equals("right"))
            if(map[exity][exitx - 1] == ' ')
                recSearch(exitx, exity - 1);
            else {
                map[exity][exitx - 1] = ' ';
                recSearch(exitx - 2, exity);
            }
        if(exitPlacement.equals("bottom"))
            if(map[exity - 1][exitx] == ' ')
                recSearch(exitx, exity - 1);
            else {
                map[exity - 1][exitx] = ' ';
                recSearch(exitx, exity - 2);
            }
    }

    public void recSearch(int x, int y){
        visitTable[y][x] = 2;
        Random rand = new Random();
        int rand2 = rand.nextInt();
        while(!neighborsVisited(x, y)){
            switch(rand2){
                //upper cell
                case 0:
                    if(y > 1)
                        recSearch();
                //right cell
                case 1:
                //left cell
                case 2:
                //lower cell
                case 3:
            }
        }
    }

    //returns true once all cells around the specified cell have been visited
    public boolean neighborsVisited(int x, int y){
        if(x == 1){
            if(y == 1)
                return (visitTable[y][x + 2] < 1 && visitTable[y + 2][x] < 1);
            else if(y == side - 1)
                return (visitTable[y][x - 2] < 1 && visitTable[y + 2][x] < 1);
            else
                return (visitTable[y][x + 2] < 1 && visitTable[y + 2][x] < 1 && visitTable[y][x - 2] < 1);
        } else if(x == side - 1)

        /*
        if(visitTable[y][x - 2] == 2 && visitTable[y - 2][x] == 2 && visitTable[y][x + 2] == 2 && visitTable[y + 2][x] == 2)
            return true;
        else
            return false;
            */
    }
}