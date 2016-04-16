package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Obstacles extends Entity{
	
	/**
	 * @param model a textured model of what the editor will look like in 3D
     * @param position the position vector of the model
     * @param rotX rotation around x axis of the model
     * @param rotY rotation around y axis of the model
     * @param rotZ rotation around z axis of the model
     * @param scale scale of the model
     * @param isObstacle a boolean specifying if the model is an obstacle
     * @param collisionSize collision vector
	 */
	public Obstacles(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			boolean isObstacle, Vector3f collisionSize) {
		super(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize);
	}

	private static final float COLLISION_ZONE = 5;

	

	

}
