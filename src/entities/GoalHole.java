package entities;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.Loader;
import terrains.Terrain;

/**
 * This class represents the golf hole
 */
public class GoalHole extends Entity{
	
	private static final float scoreThreshold = 130f;
	private Terrain terrain;

	public static int goalXPos = (int)168.34851;
	public static int goalZPos = (int) -208.15895;

	/**
	 * @param loader a reference to the loader for terrain textures
	 * @param terrain a reference to the current terrain
	 * @param model a reference to the model
	 * @param position vector position of the model
	 * @param rotX rotation in x axis
	 * @param rotY rotation in y axis
	 * @param rotZ rotation in z axis
	 * @param scale scale of the model
	 * @param isObstacle boolean specifying whether the model is an obstacle (of the Entity class)
	 * @param collisionSize size of collision
	 */
	
	
	public GoalHole(Loader loader, Terrain terrain, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			boolean isObstacle, Vector3f collisionSize) {
		super(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize);
		this.terrain = terrain;

	}

	public void setGolfHoleLocation(Vector3f position){

		goalXPos = (int)position.getX();
		goalZPos = (int)position.getY();
	}
	
	
	public boolean checkCollision(GolfBall golfBall) {

		float dx = this.getPosition().x - golfBall.getPosition().x;
		float dz = this.getPosition().z - golfBall.getPosition().z;
		
		float distance = (float) Math.sqrt(dx*dx + dz*dz);
		boolean collision = distance + golfBall.getRadius() < this.getCollisionZone().x;
		//float v = (float) Math.sqrt(golfBall.velocity.x * golfBall.velocity.x + golfBall.velocity.z * golfBall.velocity.z);
		

		return collision;
	}
	
	
}