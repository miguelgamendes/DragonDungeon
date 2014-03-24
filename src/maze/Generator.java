package maze;

import java.util.Random;

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
        printVisitTable();
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
        System.out.println("Generated inner walls");
    }

    //generates an exit in a pre-created map
    public void generateExit(){
        Random rand = new Random();
        //exit on horizontal or vertical wall -> 0 = horizontal
        int rand2 = rand.nextInt(2);
        //decide side -> 0 = left/up
        int rand3 = rand.nextInt(2);
        //determine exit cell
        int rand4;
        do{
            rand4 = rand.nextInt(side - 2) + 1;
        }while(rand4 % 2 == 0);
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
        System.out.println("Generated exit");
        System.out.println("Exit on: " + exitPlacement);
    }

    //generates a table in which a '0' is an unvisited space, '1' is a visited space and '2' is a wall
    public void generateVisitTable(){
        visitTable = new int[side][side];
        for(int i = 0; i <= side - 1; i++)
            for(int j = 0; j <= side - 1; j++)
                if(i % 2 != 0 && j % 2 != 0 && map[i][j] == ' ')
                    visitTable[i][j] = 0;
                else
                    visitTable[i][j] = 2;
        System.out.println("Generated visit table");
    }

    public void printVisitTable(){
        for(int i = 0; i <= side - 1; i++){
            for(int j = 0; j <= side - 1; j++){
                if(j == side - 1)
                    System.out.println(visitTable[i][j]);
                else
                    System.out.print(visitTable[i][j]);
            }
        }
    }

    //finalized the map through a depth first search
    public void depthFirstSearch(){
        System.out.println("Entering depth first search...");
        if(exitPlacement.equals("top")){
            System.out.println("Starting on cell - (" + exitx + ", " + (exity + 1) + ")");
            recSearch(exitx, exity + 1);
        }
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
        System.out.println("Depth first search complete.");
    }

    public void recSearch(int x, int y){
        System.out.println("Reccing on cell - (" + x + ", " + y + ")");
        visitTable[y][x] = 1;
        Random rand = new Random();
        while(!neighborsVisited(x, y)){
            int rand2 = rand.nextInt(4);
            switch(rand2){
                //upper cell
                case 0:
                    if(y > 1){
                        if(visitTable[y - 2][x] == 0){
                            map[x][y - 1] = ' ';
                            recSearch(x, y - 2);
                        }
                    }
                    break;
                //right cell
                case 1:
                    if(x < side - 3){
                        if(visitTable[y][x + 2] == 0){
                            map[x + 1][y] = ' ';
                            recSearch(x + 2, y);
                        }
                    }
                    break;
                //left cell
                case 2:
                    if(x > 1){
                        if(visitTable[y][x - 2] == 0){
                            map[x - 1][y] = ' ';
                            recSearch(x - 2, y);
                        }
                    }
                    break;
                //lower cell
                case 3:
                    if(y < side - 3){
                        if(visitTable[y + 2][x] == 0){
                            map[x][y + 1] = ' ';
                            recSearch(x, y + 2);
                        }
                    }
                    break;
            }
        }
        System.out.println("Done reccing on cell - (" + x + ", " + y + ")");
    }

    //returns true once all cells around the specified cell have been visited
    public boolean neighborsVisited(int x, int y){
        if(x == 1){
            if(y == 1)
                return (visitTable[y][x + 2] == 1 && visitTable[y + 2][x] == 1);
            else if(y == side - 2 || y == side - 3)
                return (visitTable[y][x + 2] == 1 && visitTable[y - 2][x] == 1);
            else
                return (visitTable[y][x + 2] == 1 && visitTable[y + 2][x] == 1 && visitTable[y - 2][x] == 1);
        } else if(x == side - 2 || x == side - 3) {
            if(y == 1)
                return (visitTable[y][x - 2] == 1 && visitTable[y + 2][x] == 1);
            else if(y == side - 2 || y == side - 3)
                return (visitTable[y - 2][x] == 1 && visitTable[y][x - 2] == 1);
            else
                return (visitTable[y][x - 2] == 1 && visitTable[y + 2][x] == 1 && visitTable[y - 2][x] == 1);
        } else if(y == 1) {
            if(x == side - 2 || x == side - 3) //I think this is a repeated condition
                return (visitTable[y][x - 2] == 1 && visitTable[y + 2][x] == 1);
            else
                return (visitTable[y][x - 2] == 1 && visitTable[y + 2][x] == 1 && visitTable[y][x + 2] == 1);
        } else if(y == side - 2 || y == side - 3) {
            if(x == side - 2 || x == side - 3) //I think this is a repeated condition as well
                return (visitTable[y - 2][x] == 1 && visitTable[y][x - 2] == 1);
            else
                return (visitTable[y - 2][x] == 1 && visitTable[y][x - 2] == 1 && visitTable[y][x + 2] == 1);
        } else
            return (visitTable[y - 2][x] == 1 && visitTable[y + 2][x] == 1 && visitTable[y][x + 2] == 1 && visitTable[y][x - 2] == 1);
    }
}