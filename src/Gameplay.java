import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {


    private Boolean play = false;
    private Integer score = 0;
    private Integer totalBricks = 21;

    private Timer timer;
    private Integer delay = 8;

    private Integer playerX = 310;
    private Integer ballPosX = 120;
    private Integer ballPosY = 350;
    private Integer ballXDir = -1;
    private Integer ballYDir = -2;


    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);



        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);

        //Top Border
        g.setColor(Color.CYAN);
        g.fillRect(0,0,692,3);

        g.setColor(Color.PINK);
        g.fillRect(692,0,3,592);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        ballCheck();


        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 580){
                playerX = 580;
            }else {
                movePlayer(Directions.RIGHT);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <= 10){
                playerX = 10;
            }else {
                movePlayer(Directions.LEFT);
            }
        }


    }

    public void ballCheck() {
        if (play) {
            ballCheckPaddle();
            ballCheckBorders();


        }
    }

    public void ballCheckBorders(){
        ballPosX += ballXDir;
        ballPosY += ballYDir;
        if(ballPosX < 0){
            ballXDir = -ballXDir;
        }
        if(ballPosY < 0){
            ballYDir = -ballYDir;
        }
        if(ballPosX > 670){
            ballXDir = -ballXDir;
        }

    }

    public void ballCheckPaddle(){
        if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
            ballYDir = -ballYDir;
        }
    }


    public void movePlayer(Directions direction){
        play = true;
        if(direction == Directions.RIGHT){
            playerX +=20;
        }
        if(direction == Directions.LEFT){
            playerX -= 20;
        }


    }






    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
