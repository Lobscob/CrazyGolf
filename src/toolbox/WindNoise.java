package toolbox;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class WindNoise {
	
	
    private Vector3f windDirection;
    private float windMagnitude;
    private Random r = new Random(); 
    public WindNoise() {
    	
    	float windX = r.nextFloat();
    	float windY = r.nextFloat();
    	float windZ = r.nextFloat();
    	windDirection = new Vector3f(windX, windY, windZ);
    	windMagnitude = r.nextFloat(); 
    	
    }
    
    public Vector3f wind() {
    	 if (r.nextFloat() < 0.001f) {
             windDirection.x += r.nextFloat() / 5f;
             windDirection.z += r.nextFloat() / 5f;
             if (r.nextFloat() < 0.4) {
                 windDirection.x *= -1;
             }
             if (r.nextFloat() < 0.4) {
                 windDirection.z *= -1;
             }
         }
         windDirection.normalise();
         windMagnitude = r.nextFloat();
         if( r.nextFloat() < 0.001f) {
         	windMagnitude *= 100;
         	System.out.println(windMagnitude);
         }
         windMagnitude *= 0.01;
         windDirection.scale(windMagnitude);
         System.out.println("X " + windDirection.x);
         System.out.println("Z " + windDirection.z);
         return windDirection;
    }

}
