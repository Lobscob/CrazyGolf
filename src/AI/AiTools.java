package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jeroen on 08/06/2016.
 */
public class AiTools {
    private GolfBall ball;
    private Terrain terrain;
    private GoalHole hole;
    private List<Vector3f> forces = new ArrayList<Vector3f>();
    private List<Vector3f> velocities = new ArrayList<Vector3f>();
    private int numberOfRotation = 18;
    private float factors[][];


    public AiTools(GolfBall b, Terrain t, GoalHole h) {
        ball = b;
        terrain = t;
        hole = h;

    }

    public void createShotRotations() {
        float factors[][] = new float[numberOfRotation][numberOfRotation];
        factors[0][0] = 1;
        factors[0][1] = -1;

        float division = (2 / (float) numberOfRotation);
        System.out.println("division = " + division);
        System.out.println(0 + " Xfact = " + factors[0][0]);
        System.out.println(0 + " Zfact = " + factors[0][1]);
        for (int i = 1; i < numberOfRotation; i++) {

            factors[i][0] = factors[i - 1][0] - division;
            factors[i][1] = factors[i - 1][1] + division;
            System.out.println(i + " Xfact = " + factors[i][0]);
            System.out.println(i + " Zfact = " + factors[i][1]);
        }
    }

    public void createHitForces() {
        float xForce;
        float zForce;


        for (int i = 0; i < numberOfRotation; i++) {
            Random random = new Random();
            xForce = random.nextFloat();
            zForce = random.nextFloat();
            Vector3f force = new Vector3f(xForce, 0, zForce);
            forces.add(force);
        }


    }

    public List<Vector3f> createVelocity() {
        createShotRotations();
        createHitForces();
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
