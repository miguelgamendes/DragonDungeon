import maze.Labyrinth;

import java.util.Scanner;

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