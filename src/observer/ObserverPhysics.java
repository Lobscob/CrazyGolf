package observer;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jeroen on 17/05/2016.
 */
public class ObserverPhysics {
    private int time=0;

    public void updatePositions(Entity a) {
      a.setPosition(new Vector3f(((a.getPosition().x+a.getVelocity().x)*time),((a.getPosition().y+a.getVelocity().y)*time),((a.getPosition().z+a.getVelocity().z)*time)));

    }

    public void updateRotations() {

    }

    public void updateVelocity() {

    }
    public void notifyOBS(){
        time++;
  
    }
}
