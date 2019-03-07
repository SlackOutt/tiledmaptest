package fi.tamk.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private State state;
	private TiledMapRenderer tiledMapRenderer;
	Player player;
	TiledMap tiledMap;
	@Override
	public void create () {
		setState(state.RUN);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		player = new Player();
		camera.setToOrtho(false,800,450);
		tiledMap = new TmxMapLoader().load("mapforgame1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		// This makes it so program doesn't need to ask player input every frame
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(new GestureAdapter() {
			@Override
			public boolean tap(float x,float y,int count,int button) {
				return true;
			}
		}));
		multiplexer.addProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
					case Input.Keys.D:
						break;
					case Input.Keys.A:
						break;
					case Input.Keys.W:
						player.setUpMove(true);
						break;
					case Input.Keys.S:
						player.setDownMove(true);
						break;
					case Input.Keys.SPACE:
						break;
				}
				return true;
			}
			@Override
			public boolean keyUp(int keycode) {
				switch(keycode) {
					case Input.Keys.D:
						break;
					case Input.Keys.A:
						break;
					case Input.Keys.S:
						player.setDownMove(false);
						break;
					case Input.Keys.W:
						player.setUpMove(false);
						break;
				}
				return true;
			}
		});
		Gdx.input.setInputProcessor(multiplexer);
	}
	@Override
	public void render () {
		// Update Camera
		camera.update();
		tiledMapRenderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);
		float delta = Gdx.graphics.getDeltaTime();
		checkCollisions();
		switch (state) {
			case RUN:
				tiledMapRenderer.render();
				player.move(delta);
				batch.begin();
				player.draw(batch);
				camera.position.x++;
				player.hitbox.x++;
				batch.end();
				break;
			case PAUSE:
				break;
			case RESUME:
				setState(state.RUN);
				break;
			case STOPPED:

				break;
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
	public enum State {
		PAUSE,
		RUN,
		RESUME,
		STOPPED
	}
	@Override
	public void pause() {
		state = state.PAUSE;
	}
	@Override
	public void resume() {
		state = state.RESUME;
	}
	public void setState(State s) {
		state = s;
	}
	public void checkCollisions() {
		// Gets collectable rectangles layer
		MapLayer collisionObjectLayer = tiledMap.getLayers().get("collectablerectangles");
		// All the rectangles of the layer
		MapObjects mapObjects = collisionObjectLayer.getObjects();
		// to RectangleObjects array
		Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);
		// Iterate all the rectangles
		for(RectangleMapObject rectangleObject : rectangleObjects) {
			Rectangle rectangle = rectangleObject.getRectangle();

			if (player.hitbox.overlaps(rectangle)) {
				System.out.println("JAS");
				clearCollectable(rectangle.getX(),rectangle.getY(),rectangle.getWidth());
			}
		}
	}
	public void clearCollectable(float xCoord, float yCoord, float width) {
		int x = (int)xCoord / (int)width;
		int y = (int)yCoord / (int)width;
		TiledMapTileLayer wallCells = (TiledMapTileLayer)tiledMap.getLayers().get("Collectibles");
		wallCells.setCell(x,y,null);
	}

}
