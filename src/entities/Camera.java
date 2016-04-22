package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
/**
 * This class represents the camera using the viewpoint set at the player's position
 */
public class Camera {
	
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(100,30,-50);
	private float pitch = 20;
	private float yaw;
	private float roll;
	private final float speed = 2f;
	
	private Entity player;
	
	/**
	 * @param player the player the camera is following
	 */
	public Camera(Entity player) {
		this.player = player;
	}
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngle();
		float dh = calcDH();
		float dv = calcDV();
		calcPosition(dh, dv);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	
	/**
	 * @param dh this is the horizontal distance from the player
	 * @param dv this is the vertical distance from the player
	 */
	private void calcPosition(float dh, float dv) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (dh * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (dh * Math.cos(Math.toRadians(theta)));
		
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + dv;
		
	}

	/**
	 * This method calculates the horizontal distance from the player
	 * @return the horizontal distance
	 */
	private float calcDH() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	/**
	 * This method calculates the vertical distance from the payer
	 * @return the vertical distance
	 */
	private float calcDV() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		if (distanceFromPlayer > 0) {
			distanceFromPlayer -=zoomLevel;
		}else {
			distanceFromPlayer = 0.1f;
		}
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			if(pitch > 0) {
				pitch -= pitchChange;
			}else {
				pitch = 0.01f;
			}
			if(pitch < 180) {
				pitch -= pitchChange;
			}else {
				pitch = 180 - 0.01f;
			}
		}
	}
	
	private void calculateAngle() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
			player.increaseRotation(0, angleAroundPlayer * 0.3f, 0);
		}
	}
	

	
	

}
