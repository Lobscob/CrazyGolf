package entities;

import java.awt.Rectangle;

import Testing.Main;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import terrains.Terrain;
import toolbox.MousePicker;

/**
 * This is a generic class for entities
 */
public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private final float MASS = 10;
	public float getMass() {return MASS; }
	
	private int textureIndex = 0;
	
	private boolean isObstacle;
	public boolean isEntityObstacle() { return isObstacle; }
	private Vector3f COLLISION_ZONE;
	
	public Vector3f getCollisionZone() { return COLLISION_ZONE; } 
	private boolean turnTaken = false;
	
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
	public Entity(TexturedModel model, Vector3f position, float rotX, 
			float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.isObstacle = isObstacle;
		this.COLLISION_ZONE = collisionSize;
		}
	
	/**
	 * @param model a textured model of what the editor will look like in 3D
	 * @param index index of the texture position in a png file
     * @param position the position vector of the model
     * @param rotX rotation around x axis of the model
     * @param rotY rotation around y axis of the model
     * @param rotZ rotation around z axis of the model
     * @param scale scale of the model
     * @param isObstacle a boolean specifying if the model is an obstacle
     * @param collisionSize collision vector
	 */
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, 
			float rotY, float rotZ, float scale, boolean isObstacle, Vector3f collisionSize) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.isObstacle = isObstacle;
		this.COLLISION_ZONE = collisionSize;
		this.textureIndex = index;
	}
	
	public boolean isTurnTaken() {
		return turnTaken;
	}

	public void setTurnTaken(boolean turnTaken) {
		this.turnTaken = turnTaken;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex%model.getTextureModel().getNumberOfRows();
		return (float)column/(float)model.getTextureModel().getNumberOfRows();
	}
	public float getTextureYOffset() {
		int row = textureIndex%model.getTextureModel().getNumberOfRows();
		return (float)row/(float)model.getTextureModel().getNumberOfRows();
	}
	
	/**
	 * 
	 * @param dx position change in x direction
	 * @param dy position change in y direction
	 * @param dz position change in z direction
	 */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	/**
	 * 
	 * @param dx rotation change around x axis
	 * @param dy rotation change around y axis
	 * @param dz rotation change around z axis
	 */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ+= dz;
		if(rotY >=360) {
			rotY = 0;
		}else if(rotY<0) {
			rotY = 360;
		}
	}


	public TexturedModel getModel() {
		return model;
	}


	public void setModel(TexturedModel model) {
		this.model = model;
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public float getRotX() {
		return rotX;
	}


	public void setRotX(float rotX) {
		this.rotX = rotX;
	}


	public float getRotY() {
		return rotY;
	}


	public void setRotY(float rotY) {
		this.rotY = rotY;
	}


	public float getRotZ() {
		return rotZ;
	}


	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}


	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}

	public void move(Terrain terrain) {
		
	}

	public void manageCollision(Entity entity) {
		
	}
	
	@Override
	public String toString(){
		String k = " ";

		String s = getModel().getIndex() + k + getPosition().x+k+getPosition().y + k + getPosition().z  + k + getRotX() + k + getRotY() + k + getRotZ() +
				k + getScale() + k + isEntityObstacle() + k +  + getCollisionZone().x+k+getCollisionZone().y + k + getCollisionZone().z ;
		
		return s;
	}

	public void setCurrentTerrainPoint(Vector3f terrainPoint) {
		
	}

}
