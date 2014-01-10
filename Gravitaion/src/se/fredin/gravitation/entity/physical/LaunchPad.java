package se.fredin.gravitation.entity.physical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Launchpad class creates a launchpad in which the player will spawn. Launchpad is a physical entity
 * and will interact with the player.
 * @author johan
 *
 */
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
		bounds.setPosition(getPosition().x - sprite.getWidth() / 2, getPosition().y - sprite.getHeight() / 3);
	}
	
	/**
	 * Check if a player is currently positioned on the launchpad
	 * @param player1 the first player that might be positioned on the launchpad 
	 * @param player2 the second player that might be positioned on the launchpad
	 * @param delta the time interval
	 */
	public void checkIfTaken(Player player1, Player player2, float delta) {
		if(this.bounds.overlaps(player1.getBounds()) || this.bounds.overlaps(player2.getBounds())) {
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
	
	/**
	 * Check if this launchpad is taken
	 * @return <b>true</b> if this launchpad is taken
	 */
	public boolean isTaken() {
		return isTaken;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
