import java.awt.*;

public class DisplayObjects {

    public DisplayObjects(){

    }


    public void background(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);
    }

    public void block(Graphics g){

    }

    public void borders(Graphics g){
        //Left
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        //Top
        g.setColor(Color.CYAN);
        g.fillRect(0,0,692,3);
        //Right
        g.setColor(Color.PINK);
        g.fillRect(692,0,3,592);
    }

    public void scores(Graphics g, BallChecker ballChecker){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString(""+ballChecker.getScore(), 590, 30);
    }

    public void paddle(Graphics g, BallChecker ballChecker){
        g.setColor(Color.GREEN);
        g.fillRect(ballChecker.getPlayerX(), 550, 100, 8);
    }

    public void ball(Graphics g, BallChecker ballChecker){
        g.setColor(Color.YELLOW);
        g.fillOval(ballChecker.getBallPosX(), ballChecker.getBallPosY(), 20, 20);
    }

    public void gameOver(Graphics g, BallChecker ballChecker){
        if(ballChecker.getBallPosY() > 570){
            ballChecker.endGame();
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+ballChecker.getScore(), 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
    }

    public void youWin(Graphics g, BallChecker ballChecker){
        if(ballChecker.getTotalBricks() <=0){
            ballChecker.endGame();
            g.setColor(Color.BLUE);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("YOU WON! Score: "+ballChecker.getScore(), 190, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }
    }

    public void drawBlock(Graphics2D g, Integer brickWidth, Integer brickHeight, Integer i, Integer j){
        g.setColor(Color.WHITE);
        g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
    }

}
