import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private final Timer timer;
    private final DisplayObjects displayObjects;
    private final BallChecker ballChecker;
    private Integer delay = 8;

    public Gameplay(){

        displayObjects = new DisplayObjects();
        ballChecker = new BallChecker(displayObjects.getMapGenerator());
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        displayObjects.paint(g, ballChecker);
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
                resetGame();
                repaint();
            }
        }
    }

    public void resetGame(){
        ballChecker.resetPositions();
        displayObjects.newMap();
        ballChecker.setMapGenerator(displayObjects.getMapGenerator());
    }

    public void ballCheck() {
        ballChecker.checkGameState();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
