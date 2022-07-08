package tiledgame.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SortedIntList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TiledGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture image;
	private MediaLoader media;
	private int[][] map;
	private int tilesize;
	int mapWidth = 0;
	int mapHeight = 0;
	private Vector2 guyCord;
	OrthographicCamera camera;
	private File map1;

	@Override
	public void create() {
		media = new MediaLoader();
		batch = new SpriteBatch();
		image = new Texture("libgdx.png");
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/3f, Gdx.graphics.getHeight()/3f);
		tilesize = 16;
		map1 = new FileHandle(String.valueOf(Gdx.files.internal("map.txt"))).file();


		try {
			Scanner sc = new Scanner(map1);


			while(sc.hasNextLine()){
				mapHeight += 1;
				sc.nextLine();
			}

			sc = new Scanner(map1);

			while (sc.hasNext()){
				Scanner line = new Scanner(sc.nextLine());
				while (line.hasNext()){
					mapWidth += 1;
					line.next();
				}
				break;
			}

			map = new int[mapHeight][mapWidth];

			sc = new Scanner(map1);

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

		guyCord = new Vector2(0,0);


		//sets camera to center
		//camera.translate((mapWidth*tilesize)/2f, (mapHeight*tilesize)/2f);


	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		inputCheck();

		//center camera on guy
		camera.position.set(guyCord, 0);
		camera.update();
		//

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// draw the map
		for (int row = 0; row < map.length; row++){
			for (int col = 0; col < map[row].length; col++){
				if (map[row][col] == 0){
					batch.draw(media.grass, col*tilesize, ((map.length-1)-row)*tilesize);
				}
				else if (map[row][col] == 1){
					batch.draw(media.dirt, col*tilesize, ((map.length-1)-row)*tilesize);
				}
				else if (map[row][col] == 2){
					batch.draw(media.water, col*tilesize, ((map.length-1)-row)*tilesize);
				}
			}
		}
		//Draw the guy
		batch.draw(media.guy, guyCord.x, guyCord.y);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
	}

	public void inputCheck(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			if (guyCord.x <= 0){guyCord.x = 0;}
			else {guyCord.x -= tilesize;}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			if ((guyCord.x+tilesize) >= mapWidth*tilesize){guyCord.x = (mapWidth*tilesize)-tilesize;}
			else {guyCord.x += tilesize;}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			if ((guyCord.y+tilesize) >= mapHeight*tilesize){guyCord.y = (mapHeight*tilesize)-tilesize;}
			else {guyCord.y += tilesize;}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			if (guyCord.y <= 0){guyCord.y = 0;}
			else {guyCord.y -= tilesize;}
		}
	}
}