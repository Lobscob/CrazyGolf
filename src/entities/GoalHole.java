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
	 * @param golfBall a reference to the golf ball of the player
	 */
	
	
	public GoalHole(Loader loader, Terrain terrain, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			boolean isObstacle, Vector3f collisionSize) {
		super(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize);
		this.terrain = terrain;
		//depressTerrain();
		terrain.updateTerrain(loader);
	}
	
	public void depressTerrain() {
		for(float i= (this.getPosition().x); i<this.getPosition().x + 0.4f; i+=0.11f) {
			for(float j= (this.getPosition().z);  j<this.getPosition().z + 6; j+=1f) {
				//terrain.createDepression(i, j);
			}
		}
	}
	private static int collisionCounter = 0;
	public boolean checkCollision(GolfBall golfBall) {
		collisionCounter = 0;
		float dx = this.getPosition().x - golfBall.getPosition().x;
		float dz = this.getPosition().z - golfBall.getPosition().z;
		
		float distance = (float) Math.sqrt(dx*dx + dz*dz);
		boolean collision = distance + golfBall.getRadius() < this.getCollisionZone().x;
		while(collision && collisionCounter == 0) {
			float v = (float) Math.sqrt(golfBall.velocity.x * golfBall.velocity.x + golfBall.velocity.z * golfBall.velocity.z);
			if(v < scoreThreshold && distance + golfBall.getRadius()>= this.getCollisionZone().x - 0.4f)  {
				//System.out.println("GET IN THE HOLE DAMMIT");
				golfBall.velocity.x = -golfBall.velocity.x * 0.99f;
				golfBall.velocity.z = -golfBall.velocity.z * 0.99f;
				if(v<0.00001f) {
					//System.out.println("Score!");
					golfBall.setIsInHole(true);
					collisionCounter++;
					collision = false;
				}
			}else {
				collision = false;
			}
			
		}
		return collision;
	}
	
	
}