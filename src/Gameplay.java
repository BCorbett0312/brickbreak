import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private final Timer timer;
    private final DisplayObjects displayObjects;
    private MapGenerator mapGenerator;
    private final BallChecker ballChecker;
    private Integer delay = 8;

    public Gameplay(){
        mapGenerator = new MapGenerator(3, 7);
        ballChecker = new BallChecker(mapGenerator);
        displayObjects = new DisplayObjects();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        displayObjects.background(g);

        //map
        mapGenerator.draw((Graphics2D) g, displayObjects);

        displayObjects.borders(g);
        displayObjects.scores(g, ballChecker);
        displayObjects.paddle(g, ballChecker);
        displayObjects.ball(g, ballChecker);

        displayObjects.gameOver(g, ballChecker);
        displayObjects.youWin(g, ballChecker);

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
            ballChecker.moveLeftOrRight(Directions.RIGHT);
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            ballChecker.moveLeftOrRight(Directions.LEFT);
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!ballChecker.getPlay()){
                ballChecker.resetGame();
                mapGenerator = new MapGenerator(3, 7);
                ballChecker.setMapGenerator(mapGenerator);
                repaint();
            }
        }
    }

    public void ballCheck() {
        if (ballChecker.getPlay()) {
            ballChecker.ballMove();
            ballChecker.ballCheckBorders();
            ballChecker.ballCheckPaddle();
            ballChecker.ballCheckBlock();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
