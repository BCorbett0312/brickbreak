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
    private Integer ballXDir = 1;
    private Integer ballYDir = -2;

    private MapGenerator mapGenerator;

    public Gameplay(){
        mapGenerator = new MapGenerator(3, 7);
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

        //map
        mapGenerator.draw((Graphics2D) g);

        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);

        //Top Border
        g.setColor(Color.CYAN);
        g.fillRect(0,0,692,3);

        g.setColor(Color.PINK);
        g.fillRect(692,0,3,592);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        gameOver(g);
        youWin(g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        ballCheck();

        A: for(int i = 0; i< mapGenerator.map.length; i++){
            for(int j = 0; j < mapGenerator.map[0].length; j++){
                if(mapGenerator.map[i][j] > 0){
                    int brickX = j * mapGenerator.brickWidth+ 80;
                    int brickY = i * mapGenerator.brickHeight +50;
                    int brickWidth = mapGenerator.brickWidth;
                    int brickHeight = mapGenerator.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                    Rectangle brickRect = rect;

                    if(ballRect.intersects(brickRect)){
                        mapGenerator.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;

                        if(ballPosX + 19 <= brickRect.x || ballPosX +1 >= brickRect.x + brickRect.width){
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }
                        break A;
                    }
                }
            }

        }
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
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3, 7);
                repaint();
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
        Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);
        Rectangle paddle = new Rectangle(playerX, 550, 100, 8);

        //middle
        if(ball.intersects(paddle)) {
            ballYDir = -ballYDir;

            //mid left\
            if(ball.getX() < playerX -5 && ball.getX() < playerX- 25 ){
                changeBallDirPaddle(Directions.LEFT);

            }
            //mid right
            if(ball.getX() < playerX +5 && ball.getX() < playerX+ 25 ){
                changeBallDirPaddle(Directions.RIGHT);
            }
            //far left
            if(ball.getX() < playerX -26 && ball.getX() < playerX- 45 ){
                changeBallDirPaddle(Directions.LEFT);
            }
            //far right
            if(ball.getX() < playerX +26 && ball.getX() < playerX+ 45 ){
                changeBallDirPaddle(Directions.RIGHT);
            }
            //end left
            if(ball.getX() < playerX -46){
                changeBallDirPaddle(Directions.LEFT);
            }
            //end right
            if(ball.getX() < playerX +46){
                changeBallDirPaddle(Directions.RIGHT);
            }

        }
    }

    public void changeBallDirPaddle(Directions directions){
        if(directions == Directions.LEFT) {
            if(ballXDir <= 0){
                ballXDir = -ballXDir;
            }
        }
        if(directions == Directions.RIGHT){
            if(ballXDir >= 0){
                ballXDir = -ballXDir;
            }
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



    public void gameOver(Graphics g){
        if(ballPosY > 570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
    }

    public void youWin(Graphics g){
        if(totalBricks <=0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.BLUE);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("YOU WON! Score: "+score, 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
