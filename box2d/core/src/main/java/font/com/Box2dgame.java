package font.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import static font.com.utils.Constants.PPM;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Box2dgame extends ApplicationAdapter {
	private boolean DEBUG = false;
	private final float SCALE = 2.0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debug;
	private World world;
	private Body player, platform;
	float width, height, delta;

	private SpriteBatch batch;
	private Texture tex;

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		delta = Gdx.graphics.getDeltaTime();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width/SCALE, height/SCALE);

		world = new World(new Vector2(0, -9.8f), false);
		debug = new Box2DDebugRenderer();

		player = createBox(3, 10,32, 32, false);
		platform = createBox(0,0,64, 32, true);

		batch = new SpriteBatch();
		tex = new Texture("/Images/guy.png");

	}



	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());

		// Redner
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debug.render(world, camera.combined.scl(PPM));

		batch.begin();

		batch.end();


	}


	@Override
	public void dispose() {
		world.dispose();
		debug.dispose();
	}

	private void cameraUpdate(float delta){
		Vector3 position = camera.position;
		position.x = player.getPosition().x * PPM;
		position.y = player.getPosition().y * PPM;
		camera.position.set(position);

		camera.update();

	}

	public void update (float delta){
		world.step(delta, 6, 2);
		cameraUpdate(delta);
		inputUpdate(delta);
		batch.setProjectionMatrix(camera.combined);
	}

	public void inputUpdate(float delta){
		int horizontalForce = 0;
		int verticalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			horizontalForce += 1;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
			player.applyForceToCenter(0,300, false);
		}



		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y );
	}

	private Body createBox(int x, int y, int width, int height, boolean isStatic) {
		// sets up a physical body with the physical characteristics we give it and places it into the world
		Body pBody;
		BodyDef def = new BodyDef();

		if (isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;

		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		pBody= world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2f / PPM, height/2f / PPM);

		//Give the body the shape we created! makes it physical
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return  pBody;
	}
}