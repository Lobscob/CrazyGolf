package observer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Jeroen on 17/05/2016.
 */
public class Repository {

    public int getTime() {
        return time;
    }

    private int time = 0;
    
    public static void main(String[] args) {
    	List<Entity> entities = new ArrayList<Entity>();
    	Entity a1 = new GolfBall(new Vector3f(10,10,10), 5, 1);
    	Entity a2 = new GolfBall(new Vector3f(20,20,20), 5, 2);
    	Entity a3 = new Entity(new Vector3f(0,0,0), 10);
    	
    	entities.add(a1);
    	entities.add(a2);
    	entities.add(a3);
 
    	
    	boolean running = true;
    	
    	int t = 0;
    	while(running) {
    		t++;
    		a1.move();
    		a2.move();
    		a3.move();
    		if(t>10) {
    			running = false;
    		}
    	}
    }
}
