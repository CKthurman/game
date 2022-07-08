package tiledgame.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {

    public File mapFile;
    private int mapHeight;
    private int mapWidth;

    public int[][] map;

    public MapLoader(File mapFile){
        this.mapFile = mapFile;
        this.map = readMap(mapFile);
    }

    private int[][] readMap(File mapFile){
        try {
            Scanner sc = new Scanner(mapFile);

            while(sc.hasNextLine()){
                mapHeight += 1;
                sc.nextLine();
            }

            sc.close();

            sc = new Scanner(mapFile);

            while (sc.hasNext()){
                Scanner line = new Scanner(sc.nextLine());
                while (line.hasNext()){
                    mapWidth += 1;
                    line.next();
                }
                break;
            }

            sc.close();

            map = new int[mapHeight][mapWidth];

            sc = new Scanner(mapFile);

            while(sc.hasNextLine()) {
                for (int i=0; i<map.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j=0; j<line.length; j++) {
                        map[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
