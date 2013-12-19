package se.fredin.gravitation.entity.physical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LaunchPad extends PhysicalEntity {

	private boolean isTaken;
	
	public LaunchPad(float xPos, float yPos, String texturePath, World world, float bodyWidth, float bodyHeight) {
		super(xPos, yPos, texturePath, world, bodyWidth, bodyHeight);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void tick(float delta) {
		bounds.setPosition(getBodyPosition().x - sprite.getWidth() / 2, getBodyPosition().y - sprite.getHeight() / 3);
	}
	
	public void checkIfTaken(Player player, float delta) {
		if(this.bounds.overlaps(player.getBounds())) {
			isTaken = true;
		} else {
			isTaken = false;
		}
	}

	@Override
	public Body getSpecifiedBody(float xPos, float yPos, float bodyWidth, float bodyHeight) {
		// Body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(xPos, yPos);
				
		// Box shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(bodyWidth, bodyHeight);
				
		// fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.friction = .75f;
		fixtureDef.restitution = .11f;
				
		// add to world
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		sprite.setSize(bodyWidth * 2, bodyHeight * 2);
		sprite.setPosition(xPos - sprite.getWidth() / 2, yPos);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		body.setUserData(sprite);
		
		boxShape.dispose();
		return body;
	}
	
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public boolean isTaken() {
		return isTaken;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
