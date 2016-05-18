package observer;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import org.omg.CORBA.BooleanHolder;



/**
 * Created by Jeroen on 17/05/2016.
 */

public class GolfBall extends Entity {
	
	private int modelIndex;
	
    
    public GolfBall(Vector3f position, float radius, int modelIndex) {
    	super(position, radius);
    	this.modelIndex = modelIndex;
    	
    }
    
    public int getModelIndex() {
    	return this.modelIndex;
    }

    public Vector3f getVelocity() {
        return super.getVelocity();
    }

    public void setVelocity(Vector3f velocity) {
        super.setVelocity(velocity);
    }

    public Vector3f getPosition() {
        return super.getPosition();
    }

    public void setPosition(Vector3f position) {
        super.setPosition(position);
    }

    public Vector3f getRotation() {
        return super.getRotation();
    }

    public void setRotation(Vector3f rotation) {
        super.setRotation(rotation);
    }

    public Boolean getScore() {
        return super.getScore();
    }

    public void setScore(Boolean score) {
        super.setScore(score);
    }

    public float getRadius() {
        return super.getRadius();
    }

    public void setRadius(float radius) {
        super.setRadius(radius);
    }



}
