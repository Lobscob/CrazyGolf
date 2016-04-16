package entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * This class represents the light source for the map
 */
public class Light {
	
	private Vector3f position;
	private Vector3f colour;
	
	/**
	 * @param position position of the light source 
	 * @param colour colour of the light of the light source
	 */
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public Vector3f getColour() {
		return colour;
	}


	public void setColour(Vector3f colour) {
		this.colour = colour;
	}
	
	

}
