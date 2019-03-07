package fi.tamk.tiko;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {
	private float speed = 200;
	private boolean leftMove = false;
	private boolean rightMove = false;
	private boolean upMove = false;
	private boolean downMove = false;
	public Player() {
		sprite = new Texture(Gdx.files.internal("player.png"));
		hitbox = new Rectangle(32,32,sprite.getWidth(),sprite.getHeight());
	}
	public void move(float delta) {
		if (rightMove) {
			hitbox.x = hitbox.x + speed * delta;
			if(hitbox.x >= worldWidth-hitbox.getWidth()) {
				hitbox.x = worldWidth-hitbox.getWidth();
			}
		}
		if (leftMove) {
			hitbox.x = hitbox.x - speed * delta;
			if (hitbox.x <= 0) {
				hitbox.x = 0;
			}
		}
		if (upMove) {
			hitbox.y = hitbox.y + speed * delta;
			if (hitbox.y >= worldHeight - hitbox.getHeight()) {
				hitbox.y = worldHeight - hitbox.getHeight();
			}
		}
		if (downMove) {
			hitbox.y = hitbox.y - speed * delta;
			if (hitbox.y <= 0 + hitbox.getHeight()) {
				hitbox.y = 0 + hitbox.getHeight();
			}
		}
	}
	public void draw(SpriteBatch batch) {
		batch.draw(sprite,hitbox.x,hitbox.y,
				hitbox.width,hitbox.height);
	}
	public void setLeftMove(boolean leftMove) {
		this.leftMove = leftMove;
	}
	public void setRightMove(boolean rightMove) {
		this.rightMove = rightMove;
	}
	public void setUpMove(boolean upMove) {
		this.upMove = upMove;
	}
	public void setDownMove(boolean downMove) {
		this.downMove = downMove;
	}
}
