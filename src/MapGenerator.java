

public class MapGenerator {

    public Integer map[][];
    public Integer brickWidth;
    public Integer brickHeight;

    public MapGenerator(Integer row, Integer col){
        map = new Integer[row][col];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j< map[0].length; j++){
                map[i][j]=1;
            }
        }
    }
}
