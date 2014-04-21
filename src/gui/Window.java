package gui;
/*
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window {
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

    public Window(){
        frame = new JFrame("Game GUI should minimally work");
        mainPanel = new JPanel(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        gamePanel = new JPanel(new GridLayout(10,10));
        buttonPanel = new JPanel();
        image = new ImageIcon[3];
        label = new JLabel[10][11];

        image[0] = new ImageIcon("brick.png");

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                label[i][j] = new JLabel(image[0]);
            }
        }

        //exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ExitListener());

        //new game button
        gameButton = new JButton("New Game");

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
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
                if(e.getKeyChar() == 'w'){
                    frame.setVisible(false);
                    System.exit(0);
                }
            }
        });
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        //adjust and show window
        frame.pack();
        frame.setVisible(true);
    }

    public void updateMap(){

    }
}*/