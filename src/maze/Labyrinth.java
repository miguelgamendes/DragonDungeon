package maze;

import chars.*;
//import gui.*;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

//gui imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Labyrinth{
    //gui elements
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton gameButton;
    private JLabel[][] label;
    private ImageIcon[] image;

    private class ExitListener implements ActionListener{
        public void actionPerformed(ActionEvent arg0){
            frame.setVisible(false);
            System.exit(0);
        }
    }

    //game settings
    int mapSize;
    String strategy;
    int dragonNumber;

    //game objects
    Hero heman = new Hero('H', 1, 1);
    Sword excalibur = new Sword('E');
    Eagle hedwig = new Eagle();
    Vector<Dragon> lizzy = new Vector<Dragon>();//new Dragon('D', 1, 3);

    //map aids
    char[][] drawMap = new char[10][10];
    char[][] terrain;

    public Labyrinth(){
        //game parameters definition
        initialParameters();

        drawMap = new char[mapSize][mapSize];

        //generate map
        generateRandomMap();

        //generate dragons
        for(int i = 0; i < dragonNumber; i++){
            lizzy.add(new Dragon('D'));
            while(terrain[lizzy.get(i).getPosY()][lizzy.get(i).getPosX()] == 'X' || terrain[lizzy.get(i).getPosY()][lizzy.get(i).getPosX()] == 'S')
                lizzy.get(i).setRandomPos(mapSize);
        }

        //generate sword
        do{
            excalibur.setRandomPos(mapSize);
        }while(terrain[excalibur.getPosY()][excalibur.getPosX()] == 'X' || terrain[excalibur.getPosY()][excalibur.getPosX()] == 'S');
    }

    public Hero getHero(){
        return heman;
    }

    public Dragon getDragon(int n){
        return lizzy.get(n);
    }

    public void initialParameters(){
        Scanner scan = new Scanner(System.in);
        System.out.print("How big do you like your maps? : ");
        mapSize = scan.nextInt();
        System.out.print("Please choose dragon strategy (sleepy, random, sleepyrandom)");
        strategy = scan.nextLine();
        strategy = scan.nextLine();
        System.out.print("Choose the number of dragons you'd like to face: ");
        dragonNumber = scan.nextInt();
    }

    public void generateDefaultMap(){
        Generator gen = new Generator(mapSize);
        terrain = gen.generateDefault();
    }

    public void generateRandomMap(){
        Generator gen = new Generator(mapSize);
        terrain = gen.generateRandomizedDFS();
    }

    public void gameCycle(Labyrinth lab){
        String choice;
        Scanner scan = new Scanner(System.in);

        overlay();

        //main window gui
        initializeWindow();

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

    public void overlay(){
        //create terrain
        for(int i = 0; i < mapSize; i++){
            drawMap[i] = terrain[i].clone();
        }
        //overlay game objects

        if(hedwig.called)
            drawMap[hedwig.getPosY()][hedwig.getPosX()] = hedwig.getRep();
        for(int i = 0; i < dragonNumber; i++)
            drawMap[lizzy.get(i).getPosY()][lizzy.get(i).getPosX()] = lizzy.get(i).getRep();
        drawMap[excalibur.getPosY()][excalibur.getPosX()] = excalibur.getRep();
        drawMap[heman.getPosY()][heman.getPosX()] = heman.getRep();
    }

    public void print(){
        //draw map
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
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
            callEagle();
        else
            System.out.println("Command not found/Unpassable block!");
    }

    public void moveHero(char direction){
        if(direction == 'w' && checkPassable(heman.getPosX(), heman.getPosY() - 1))
            heman.move(direction);
        else if(direction == 's' && checkPassable(heman.getPosX(), heman.getPosY() + 1))
            heman.move(direction);
        else if(direction == 'a' && checkPassable(heman.getPosX() - 1, heman.getPosY()))
            heman.move(direction);
        else if(direction == 'd' && checkPassable(heman.getPosX() + 1, heman.getPosY()))
            heman.move(direction);
        //else if(direction.equals("eagle"))
        //    callEagle();
        else
            System.out.println("Command not found/Unpassable block!");
    }

    //moves every dragon
    public void moveDragons(){
        for(int i = 0; i < dragonNumber; i++){
        Random rand = new Random();
        int rand2 = rand.nextInt(5);
            switch(rand2){
                case 0:
                    break;
                case 1:
                    if(checkPassable(lizzy.get(i).getPosX(), lizzy.get(i).getPosY() - 1))
                        lizzy.get(i).move("up");
                    break;
                case 2:
                    if(checkPassable(lizzy.get(i).getPosX(), lizzy.get(i).getPosY() + 1))
                        lizzy.get(i).move("down");
                    break;
                case 3:
                    if(checkPassable(lizzy.get(i).getPosX() - 1, lizzy.get(i).getPosY()))
                        lizzy.get(i).move("left");
                    break;
                case 4:
                    if(checkPassable(lizzy.get(i).getPosX() + 1, lizzy.get(i).getPosY()))
                        lizzy.get(i).move("right");
                    break;
            }
        }
    }

    public void callEagle()
    {
        hedwig.setPosY(heman.getPosY());
        hedwig.setPosX(heman.getPosX());
        hedwig.startX = heman.getPosX();
        hedwig.startY = heman.getPosY();
        findEagleWay();
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
        for(int i = 0; i < dragonNumber; i++){
            if(excalibur.getPosX() == lizzy.get(i).getPosX() && excalibur.getPosY() == lizzy.get(i).getPosY() && excalibur.isPickedUp()){
                lizzy.get(i).guardSword();
            } else {
                lizzy.get(i).leaveSword();
            }
        }
    }

    //checks important situations, changes game object states accordingly
    public void checkConditions(){
        checkWeapon();
        killDragons();
    }

    //kills dragons in adjacent blocks if Hero is armed
    public void killDragons(){
        for(int i = 0; i < dragonNumber; i++){
            if(heman.isArmed() && lizzy.get(i).isAlive()){
                //check adjacent cells
                if((heman.getPosX() + 1 == lizzy.get(i).getPosX() && heman.getPosY() == lizzy.get(i).getPosY()) ||
                        (heman.getPosX() - 1 == lizzy.get(i).getPosX() && heman.getPosY() == lizzy.get(i).getPosY()) ||
                        (heman.getPosX() == lizzy.get(i).getPosX() && heman.getPosY() - 1 == lizzy.get(i).getPosY()) ||
                        (heman.getPosX() == lizzy.get(i).getPosX() && heman.getPosY() + 1 == lizzy.get(i).getPosY()))
                    lizzy.get(i).die();
            }
        }
    }

    //ends the cycle if player crosses exit while armed or meets dragon unnarmed
    public boolean checkEndConditions(){
        return !(checkExit() || checkFatalDragons());
    }

    //returns true once hero has found exit while armed
    public boolean checkExit(){
        int exitx = -1;
        int exity = -1;
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
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
        for(int i = 0; i < dragonNumber; i++){
            if(!heman.isArmed() && lizzy.get(i).isAlive() && !lizzy.get(i).isSleeping()){
                //check right cell
                if(heman.getPosX() + 1 == lizzy.get(i).getPosX() && heman.getPosY() == lizzy.get(i).getPosY()){
                    System.out.println("Death... by fire");
                    heman.kill();
                    return true;
                }
                //check left cell
                else if(heman.getPosX() - 1 == lizzy.get(i).getPosX() && heman.getPosY() == lizzy.get(i).getPosY()){
                    System.out.println("Death... by fire");
                    heman.kill();
                    return true;
                }
                //check up cell
                else if(heman.getPosX() == lizzy.get(i).getPosX() && heman.getPosY() - 1 == lizzy.get(i).getPosY()){
                    System.out.println("Death... by fire");
                    heman.kill();
                    return true;
                }
                //check down cell
                else if(heman.getPosX() == lizzy.get(i).getPosX() && heman.getPosY() + 1 == lizzy.get(i).getPosY()){
                    System.out.println("Death... by fire");
                    heman.kill();
                    return true;
                }
            }
        }

        return false;
    }

    public void findEagleWay() {
        hedwig.wayX = excalibur.getPosX() - hedwig.getPosX();
        hedwig.wayY = excalibur.getPosY() - hedwig.getPosY();
    }

    public void moveEagle() {
        if( hedwig.wayY < 0 && (hedwig.wayX == 0 || Math.abs(hedwig.wayX) <= Math.abs(hedwig.wayY)))
        {
            hedwig.setPosY(hedwig.getPosY() - 1);
            hedwig.wayY++;
        }
        else if(hedwig.wayY > 0 && (hedwig.wayX == 0 || Math.abs(hedwig.wayX) <= Math.abs(hedwig.wayY)))
        {
            hedwig.setPosY(hedwig.getPosY() + 1);
            hedwig.wayY--;
        }
        else if(hedwig.wayX < 0 && (hedwig.wayY == 0 || Math.abs(hedwig.wayX) > Math.abs(hedwig.wayY)))
        {
            hedwig.setPosX(hedwig.getPosX() - 1);
            hedwig.wayX++;
        }
        else if(hedwig.wayX > 0 && (hedwig.wayY == 0 || Math.abs(hedwig.wayX) > Math.abs(hedwig.wayY)))
        {
            hedwig.setPosX(hedwig.getPosX() + 1);
            hedwig.wayX--;
        }
        else if(!(hedwig.caughtSword) && excalibur.getPosX() == hedwig.getPosX() && excalibur.getPosY() == hedwig.getPosY())
        {

            hedwig.caughtSword = true;
            excalibur.pickUp();
            hedwig.wayX = hedwig.startX - hedwig.getPosX();
            hedwig.wayY = hedwig.startY - hedwig.getPosY();
        }
        else if(heman.getPosX() == hedwig.getPosX() && heman.getPosY() == hedwig.getPosY())
        {
            heman.arm();
            hedwig.kill();
        } //it means that it is awaiting for it's hero in the initial position
    }

    public void initializeWindow(){
        //gui stuff
        frame = new JFrame("Game GUI should minimally work");
        mainPanel = new JPanel(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        gamePanel = new JPanel(new GridLayout(mapSize,mapSize));
        buttonPanel = new JPanel();
        image = new ImageIcon[8];
        label = new JLabel[mapSize][mapSize];

        image[0] = new ImageIcon("brick.png");
        image[1] = new ImageIcon("hero.png");
        image[2] = new ImageIcon("dragon.png");
        image[3] = new ImageIcon("floor.png");
        image[4] = new ImageIcon("exit.png");
        image[5] = new ImageIcon("sword.png");
        image[6] = new ImageIcon("armedhero.png");
        image[7] = new ImageIcon("eagle.png");

        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                switch(drawMap[i][j]){
                    case 'X':
                        label[i][j] = new JLabel(image[0]);
                        break;
                    case ' ':
                        label[i][j] = new JLabel(image[3]);
                        break;
                    case 'H':
                        label[i][j] = new JLabel(image[1]);
                        break;
                    case 'D':
                    case 'd':
                        label[i][j] = new JLabel(image[2]);
                        break;
                    case 'S':
                        label[i][j] = new JLabel(image[4]);
                        break;
                    case 'E':
                        label[i][j] = new JLabel(image[5]);
                        break;
                    case 'A':
                        label[i][j] = new JLabel(image[6]);
                        break;
                    default:
                        label[i][j] = new JLabel(image[0]);
                        break;
                }
            }
        }

        //exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitListener());

        //new game button
        gameButton = new JButton("New Game");

        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                gamePanel.add(label[i][j]);
            }
        }

        //buttonPanel.add(gameButton);
        //buttonPanel.add(exitButton);

        //mainPanel.add(gamePanel);
        //mainPanel.add(buttonPanel);

        frame.add(gamePanel);

        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                overlay();
                moveHero(e.getKeyChar());
                moveDragons();
                if(hedwig.called)
                    moveEagle();
                checkConditions();
                updateGUIMap();
            }
        });
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        //adjust and show window
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void updateGUIMap(){
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                switch(drawMap[i][j]){
                    case 'X':
                        label[i][j].setIcon(image[0]);
                        break;
                    case ' ':
                        label[i][j].setIcon(image[3]);
                        break;
                    case 'H':
                        label[i][j].setIcon(image[1]);
                        break;
                    case 'D':
                    case 'd':
                        label[i][j].setIcon(image[2]);
                        break;
                    case 'S':
                        label[i][j].setIcon(image[4]);
                        break;
                    case 'E':
                        label[i][j].setIcon(image[5]);
                        break;
                    case 'A':
                        label[i][j].setIcon(image[6]);
                        break;
                    default:
                        label[i][j].setIcon(image[0]);
                        break;
                }
            }
        }
        frame.revalidate();
        frame.repaint();
    }
}