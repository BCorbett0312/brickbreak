import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

public class BallChecker {



    //Keeps the game alive
    private Boolean play = false;

    //Helps determine if board is complete
    private Integer totalBricks = 21;
    private Integer score = 0;

    //Positions of Player and Ball
    private Integer playerX = 300;
    private Integer ballPosX = 340;
    private Integer ballPosY = 530;
    private Integer ballXDir = 1;
    private Integer ballYDir = -2;

    //Creates the map of blocks
    private MapGenerator mapGenerator;

    //Constructor
    public BallChecker(MapGenerator mapGenerator){
        this.mapGenerator = mapGenerator;
    }



    //Accessors for fields for other parts of game
    public Boolean getPlay(){
        return play;
    }
    public Integer getScore() { return score; }
    public Integer getBallPosY() { return ballPosY; }
    public Integer getTotalBricks(){
        return totalBricks;
    }
    public Integer getPlayerX(){
        return playerX;
    }
    public Integer getBallPosX(){
        return ballPosX;
    }


    public void checkGameState(){
        if (getPlay()) {
            ballMove();
            ballCheckBorders();
            ballCheckPaddle();
            ballCheckBlock();
        }
    }

    //Reset Game
    public void endGame(){
        play = false;
        ballXDir = 0;
        ballYDir = 0;
    }

    public void resetPositions(){
        play = true;
        ballPosX = ThreadLocalRandom.current().nextInt(20, 500);
        ballPosY = 280;
        ballXDir = -1;
        ballYDir = 2;
        playerX = 310;
        score = 0;
        totalBricks = 21;
    }

    public void setMapGenerator(MapGenerator mapGenerator){
        this.mapGenerator = mapGenerator;
    }


    //ball checks for collision against objects

    public void ballCheckBorders(){
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
            //mid left
            if(ball.getX() < playerX +50 && ball.getX() >= playerX+ 25 ){
                changeBallDirPaddle(Directions.LEFT);
                changeBallAngle(2);
            }
            //mid right
            if(ball.getX() > playerX +50 && ball.getX() <= playerX+ 75 ){
                changeBallDirPaddle(Directions.RIGHT);
                changeBallAngle(2);
            }
            //far left
            if(ball.getX() < playerX +25 && ball.getX() >= playerX+ 5 ){
                changeBallDirPaddle(Directions.LEFT);
                changeBallAngle(3);
            }
            //far right
            if(ball.getX() > playerX +75 && ball.getX() <= playerX+ 95 ){
                changeBallDirPaddle(Directions.RIGHT);
                changeBallAngle(3);
            }
            //end left
            if(ball.getX() < playerX +5){
                changeBallDirPaddle(Directions.LEFT);
                changeBallAngle(4);
            }
            //end right
            if(ball.getX() > playerX +95){
                changeBallDirPaddle(Directions.RIGHT);
                changeBallAngle(4);
            }
        }
    }

    public void ballCheckBlock(){
        A: for(int i = 0; i< mapGenerator.map.length; i++) {
            for (int j = 0; j < mapGenerator.map[0].length; j++) {
                if (mapGenerator.map[i][j] > 0) {
                    int brickX = j * mapGenerator.brickWidth + 80;
                    int brickY = i * mapGenerator.brickHeight + 50;
                    int brickWidth = mapGenerator.brickWidth;
                    int brickHeight = mapGenerator.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                    Rectangle brickRect = rect;

                    if (ballRect.intersects(brickRect)) {
                        mapGenerator.setBrickValue(0, i, j);
                        totalBricks--;
                        score += 5;
//                        speedUp();

                        if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }
                        break A;
                    }
                }
            }
        }
    }

    //Effect on ball movement and direction
    public void ballMove(){
        ballPosX += ballXDir;
        ballPosY += ballYDir;
    }

//    public void speedUp(){
//        if(ballXDir >0){
//            ballXDir++;
//        }
//        else {
//            ballXDir--;
//        }
//        if(ballYDir >0){
//            ballYDir++;
//        }
//        else {
//            ballYDir--;
//        }
//    }


    public void changeBallDirPaddle(Directions directions){
        if(directions == Directions.LEFT) {
            if(ballXDir > 0){
                ballXDir = -ballXDir;
            }
        }
        if(directions == Directions.RIGHT){
            if(ballXDir < 0){
                ballXDir = -ballXDir;
            }
        }
    }

    public void changeBallAngle(Integer changeAmount){
        if (ballXDir > 0){
            ballXDir = changeAmount;
        }
        if (ballXDir < 0){
            ballXDir = -changeAmount;
        }
    }


    //Effect on Paddle
    public void movePlayer(Directions direction){
        play = true;
        if(direction == Directions.RIGHT){
            playerX +=20;
        }
        if(direction == Directions.LEFT){
            playerX -= 20;
        }
    }

    public void moveLeftOrRight(Directions directions){
        if(directions == Directions.RIGHT){
            if(playerX >= 580){
                playerX = 580;
            }else {
                movePlayer(Directions.RIGHT);
            }
        }
        if(directions == Directions.LEFT){
            if(playerX <= 10){
                playerX = 10;
            }else {
                movePlayer(Directions.LEFT);
            }
        }
    }
}
