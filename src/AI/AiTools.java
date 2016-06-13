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


    private int numberOfRotation = 10;


    public AiTools(GolfBall b, Terrain t, GoalHole h) {
        ball = b;
        terrain = t;
        hole = h;
        GoalHole hole;

    }

    public float[][] createShotRotations() {
        float factors[][] = new float[numberOfRotation][numberOfRotation];
        factors[0][0] = 1;
        factors[0][1] = 0;

        double epsilon = (360/numberOfRotation);
        float angle = 0;
        System.out.println("angle = " + epsilon);
        System.out.println(0 + " Xfact = " + factors[0][0]);
        System.out.println(0 + " Zfact = " + factors[0][1]);
        for (int i =0; i < numberOfRotation; i++) {

            factors[i][0] =  (float) Math.cos(Math.toRadians(angle));
            factors[i][1] = (float) Math.sin(Math.toRadians(angle));
            angle+=epsilon;
            System.out.println(i + " Xfact = " + factors[i][0]);
            System.out.println(i + " Zfact = " + factors[i][1]);
        }
        return factors;
    }

    public ArrayList<Vector3f> createHitForces() {
        ArrayList<Vector3f> forces = new ArrayList<Vector3f>();
        float xForce;
        float zForce;


        for (int i = 0; i < numberOfRotation; i++) {
            Random random = new Random();
            xForce = random.nextFloat() * 1000;
            zForce = random.nextFloat() * 1000;
            Vector3f force = new Vector3f(xForce, 0, zForce);
            forces.add(force);
        }

        return forces;
    }

    public List<Vector3f> createVelocity() {
       float[][] factors = createShotRotations();
        ArrayList<Vector3f> forces =  createHitForces();
        ArrayList<Vector3f>  velocities = new ArrayList<Vector3f>();
        System.out.print("Last Factor" + factors[2][1]);
        System.out.print("Last force" + forces.get(2));

        for (int i = 0; i < numberOfRotation; i++) {
            Vector3f velocity = new Vector3f();
            float xVelocity = forces.get(i).x * factors[i][0];
            float zVelocity = forces.get(i).x * factors[i][1];
            velocity.x = xVelocity;
            velocity.z = zVelocity;
            velocities.add(velocity);
        }
        return velocities;
    }

}
