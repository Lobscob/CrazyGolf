package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;

/**
 * Created by Jeroen on 08/06/2016.
 */
public class AiTools {
    private GolfBall ball;
    private Terrain terrain;
    private GoalHole hole;

    private double numberOfRotation = 2.0;

    public int staticForce=500;


    public AiTools() {
        ball = null;
        terrain = null;
        hole = null;

    }

    public ArrayList<Vector3f> createShotRotations() {
       ArrayList<Vector3f> factors = new ArrayList<Vector3f>();
        double epsilon = (360.0/numberOfRotation);
        //System.out.println(epsilon);
        double angle = 0;
        for (int i =0; i < numberOfRotation; i++) {
        	Vector3f direction = new Vector3f(0,0,0);

            direction.x = (float) Math.cos(Math.toRadians(angle));
            direction.y = 0;
            direction.z = (float) Math.sin(Math.toRadians(angle));
            angle+=epsilon;
            direction.normalise();
            //System.out.println("Direction: " + direction);
            
            factors.add(direction);
        }
        return factors;
    }

    public ArrayList<Vector3f> createHitForces() {
        ArrayList<Vector3f> forces = new ArrayList<Vector3f>();
        float xForce;
        float zForce;


        for (int i = 0; i < numberOfRotation; i++) {
            Random random = new Random();
            xForce = random.nextFloat() * staticForce;
            zForce = random.nextFloat() * staticForce;
            Vector3f force = new Vector3f(xForce, 0, zForce);
            forces.add(force);
        }

        return forces;
    }

    public List<Vector3f> createVelocity() {
    	ArrayList<Vector3f> factors = createShotRotations();
        ArrayList<Vector3f> forces =  createHitForces();
        ArrayList<Vector3f> velocities = new ArrayList<Vector3f>();
        //System.out.print("Last Factor" + factors[2][1]);
        //System.out.print("Last force" + forces.get(2));

        for (int i = 0; i < numberOfRotation; i++) {
            Vector3f velocity = new Vector3f();
            float xVelocity = forces.get(i).x * factors.get(i).x;
            float zVelocity = forces.get(i).z * factors.get(i).z;
            velocity.x = xVelocity;
            velocity.z = zVelocity;
            velocities.add(velocity);
        }
        return velocities;
    }

}
