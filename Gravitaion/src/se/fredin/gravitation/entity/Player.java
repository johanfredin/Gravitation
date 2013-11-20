package se.fredin.gravitation.entity;

import se.fredin.gravitation.Gravitation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends AbstractEntity {
	
	private float speed = 3f;
	private Vector2 velocity;
	private GamePad gamePad;
	
	public Player(Vector2 position, String pathToTexture, TextureFilter filter) {
		super(position.x, position.y, pathToTexture, filter);
		this.gamePad = new GamePad();
		this.velocity = new Vector2();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	@Override
	public void tick(float delta) {
		sprite.setPosition(position.x, position.y);		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			velocity.set(0, speed);
		} if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			velocity.set(0, -speed);
		} if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			velocity.set(-speed, 0);
		} if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			velocity.set(speed, 0);
		}
		position.add(velocity);
		bounds.setPosition(position);
	}
	
	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public GamePad getGamePad() {
		return this.gamePad;
	}
	
	public void setVelocity(float x, float y) {
		this.velocity.set(x, y);
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public class GamePad extends ControllerAdapter {
		@Override
		public boolean povMoved(Controller controller, int povIndex, PovDirection value) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", pov " + povIndex + " pressed, direction = " + value);
			switch(value) {
			case west:
				velocity.x = -speed;
				break;
			case east:
				velocity.x = speed;
				break;
			case north:
				velocity.y = speed;
				break;
			case south:
				velocity.y = -speed;
				break;
			default:
				break;
			}
			return false;
		}
		
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", button " + buttonIndex + " pressed");
			return false;
		}
		
		@Override
		public boolean buttonUp(Controller controller, int buttonIndex) {
			Gdx.app.log(Gravitation.LOG, "#" + (controller) + ", button " + buttonIndex + " released");
			return false;
		}
	}
}
