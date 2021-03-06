package observer;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jeroen on 17/05/2016.
 */
public class ObserverPhysics {
    private int time;
    private GolfBall a;
    
    public ObserverPhysics (GolfBall a) {
    	this.time = 0;
    	this.a = a;
    }
    
    public void notifyObs() {
    	time++; 
    	updatePositions(this.a);
    	updateRotations(this.a);
    	updateVelocity(this.a, new Vector3f(0,-9.80665f,0));
    }

    private void updatePositions(GolfBall a) {
      a.setPosition(new Vector3f(((a.getPosition().x+a.getVelocity().x)*time),
    		  ((a.getPosition().y+a.getVelocity().y)*time),
    		  ((a.getPosition().z+a.getVelocity().z)*time)));

    }

    private void updateRotations(GolfBall a) {
    	//example of rotation
    	a.setRotation(new Vector3f(a.getRotation().x + 1, a.getRotation().y+1, a.getRotation().z+1));
    }

    private void updateVelocity(Entity a, Vector3f accelerations) {
    	Vector3f v = a.getVelocity();
    	v.x += (accelerations.x) * time;
    	v.y += (accelerations.y) * time;
    	v.z += (accelerations.z) * time;
    	a.setVelocity(v);
    }
}
