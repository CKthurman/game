package tiledgame.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediaLoader {

    public Texture dirt, grass, water, guy;

    public MediaLoader(){
        this.dirt = new Texture(Gdx.files.internal("dirt 01.png"));
        this.grass = new Texture(Gdx.files.internal("grass 01.png"));
        this.water = new Texture(Gdx.files.internal("water 01.png"));
        this.guy = new Texture(Gdx.files.internal("guy.png"));
    }
}
