import java.awt.*;

public class MapGenerator {


    public Integer[][] map;
    public Integer brickWidth;
    public Integer brickHeight;

    public MapGenerator(Integer row, Integer col){
        map = new Integer[row][col];
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j]=1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g, DisplayObjects displayObjects){
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j]> 0){
                    displayObjects.drawBlock(g, brickWidth, brickHeight, i, j);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}
