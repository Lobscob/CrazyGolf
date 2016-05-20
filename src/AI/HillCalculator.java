package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeroen on 19/05/2016.
 */
public class HillCalculator {

    private float percision = 120;
    private GolfBall ball;
    private GoalHole hole;
    private Vector3f holePos;

    private Terrain terrain;

    public HillCalculator(GolfBall b, GoalHole h, Terrain t) {
        ball = b;
        hole = h;
        terrain = t;
        holePos = hole.getPosition();
    }

    private Vector3f normalCalculator(float x, float z) {
        float heightL = terrain.getHeightOfTerrain(x - 1, z);
        float heightR = terrain.getHeightOfTerrain(x + 1, z);
        float heightD = terrain.getHeightOfTerrain(x, z - 1);
        float heightU = terrain.getHeightOfTerrain(x, z + 1);

        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }


    public List<Vector3f> calculatePoints() {


        Vector3f ballPos = ball.getPosition();

//        float distanceBallToHole = (float) (Math.sqrt(ballPos.x*ballPos.x + ballPos.z*ballPos.z));
        float distanceX = holePos.x - ballPos.x;
        float distanceZ = holePos.z - ballPos.z;


        float differenceX = distanceX / percision;
        float differenceZ = distanceZ / percision;

        List Vectors = new ArrayList<Vector3f>();
        float pointX = ballPos.x;
        float pointZ = ballPos.z;
        for (int i = 0; i < percision; i++) {
            Vectors.add(i, new Vector3f(pointX, (terrain.getHeightOfTerrain(pointX, pointZ)), pointZ));
            pointX += differenceX;
            pointZ += differenceZ;
        }
        Vectors.add(holePos);
/*        System.out.println("ballPos" + ballPos);
        System.out.println("HolePos" + holePos);
        System.out.println(Vectors);*/
        return Vectors;
    }

    public Vector3f calculateVelocity() {
        List<Vector3f> points = calculatePoints();
        float ax = 0;
        float az = 0;
        float ay = 0;
        int negativeX = 1;
        int negativeZ = 1;
        Vector3f normal = new Vector3f();
        for (int i = 1; i < percision + 1; i++) {
            normal = normalCalculator((points.get(i).x + points.get(i-1).x) / 2, (points.get(i).z + points.get(i-1).z) / 2);
//            System.out.println(normal);
            negativeX = 1;
            negativeZ = 1;
            float difX = (points.get(i).x - points.get(i - 1).x);
            float difY = (points.get(i).y - points.get(i - 1).y);
            float difZ = (points.get(i).z - points.get(i - 1).z);
            if ((difX) < 0) negativeX = -1;
            if ((difZ < 0)) negativeZ = -1;
/*            System.out.println(i + " NegX= " +negativeX);
            System.out.println(i + " NegZ= " +negativeZ);*/
            ax += (float) negativeX * (Math.sqrt((difX * difX) + difY * difY));
            az += (float) negativeZ * (Math.sqrt((difZ * difZ) + difY * difY));

//            System.out.println(DisplayManager.getFrameTimeSeconds() * 1 / (difX * 2 * ball.getGravity() * ball.getGroundFriction()));

        }
//        System.out.println(1/(ball.getNormal().x*2*ball.getGravity() * ball.getGroundFriction()) - ball.getWindX());
        ax *= 1.6 -ball.getWindX();
        az *= 1.6+ball.getWindZ();
/*        ax *= 1/(ball.getGravity() * ball.getGroundFriction()) - ball.getWindX();
        az *= 1/(ball.getGravity() * ball.getGroundFriction()) - ball.getWindZ();*/
        System.out.println("velocity" + new Vector3f(ax, ay, az));
        return new Vector3f(ax, ay, az);
    }

}
