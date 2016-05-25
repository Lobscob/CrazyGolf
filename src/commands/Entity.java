package commands;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Entity {
	
	private Vector3f position;
	private float mass;
	


	public Entity(Vector3f p, float m) {
		position = p;
		mass = m;
	}
	
	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	
}
