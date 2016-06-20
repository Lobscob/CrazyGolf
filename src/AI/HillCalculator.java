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

    private float percision = 60;
    private float phycicsPercision = 60;
    private GolfBall ball;
    private GoalHole hole;
    private Vector3f holePos;

    private Terrain terrain;
    private Boolean debug = false;

    public HillCalculator(GolfBall b, GoalHole h, Terrain t) {
        ball = b;
        hole = h;
        terrain = t;
        float x = hole.getPosition().x;
        float y = hole.getPosition().y;
        float z = hole.getPosition().z;
        holePos = new Vector3f(x,y,z);
        holePos.x += 0.01f;
        holePos.z += 0.02f;

        

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
//        percision = (int) Math.sqrt(distanceX * distanceX + distanceZ * distanceZ)/20;
        if (debug) System.out.println("percision = " + percision);

        float differenceX = distanceX / percision;
        float differenceZ = distanceZ / percision;

        List Vectors = new ArrayList<Vector3f>();
        float pointX = ballPos.x;
        float pointZ = ballPos.z;
        if (debug) System.out.println("terrain " + terrain.getID());
        for (int i = 0; i < percision; i++) {
            Vectors.add(i, new Vector3f(pointX, (terrain.getHeightOfTerrain(pointX, pointZ)), pointZ));
            pointX += differenceX;
            pointZ += differenceZ;
            if (debug) System.out.print(terrain.getHeightOfTerrain(pointX, pointZ));
        }
        Vectors.add(holePos);
/*        if(debug) System.out.println("ballPos" + ballPos);
        if(debug) System.out.println("HolePos" + holePos);
        if(debug) System.out.println(Vectors);*/
        return Vectors;
    }

    public Vector3f calculateVelocity() {
        List<Vector3f> points = calculatePoints();
        float ax = 0;
        float az = 0;
        float ay = 0;
        float axd = 0;
        float azd = 0;

        int negativeX = 1;
        int negativeZ = 1;
        Vector3f normal = new Vector3f();
        for (int i = 1; i < percision + 1; i++) {
            normal = normalCalculator((points.get(i).x + points.get(i - 1).x) / 2, (points.get(i).z + points.get(i - 1).z) / 2);
            if (debug) System.out.println(normal);
            negativeX = 1;
            negativeZ = 1;


            float difX = (points.get(i).x - points.get(i - 1).x);
            float difY = (points.get(i).y - points.get(i - 1).y);
            float difZ = (points.get(i).z - points.get(i - 1).z);
            if ((difX) < 0) negativeX = -1;
            if ((difZ < 0)) negativeZ = -1;
/*            if(debug) System.out.println(i + " NegX= " +negativeX);
            if(debug) System.out.println(i + " NegZ= " +negativeZ);*/
//           if (Math.abs(difY)== 0) {
            ax += (float) negativeX * (Math.sqrt((difX * difX) + difY * difY));
            axd += difX;
            azd += difZ;
//                ax += (float) negativeX * (Math.sqrt((difX * difX) + difY * difY));
            az += (float) negativeZ * (Math.sqrt((difZ * difZ) + difY * difY));
//                az += (float) negativeZ * (Math.sqrt((difZ * difZ) + difY * difY));

/*            } else {a
                ax += (float) negativeX * (Math.sqrt((difX * difX) + difY * difY))+ball.getGravity()* difZ/difY  ;
//                ax += (float) negativeX * (Math.sqrt((difX * difX) + difY * difY));
                az += (float) negativeZ * (Math.sqrt((difZ * difZ) + difY * difY)) +ball.getGravity()* difZ/difY ;
//                az += (float) negativeZ * (Math.sqrt((difZ * difZ) + difY * difY));
            }*/
//            if(debug) System.out.println(DisplayManager.getFrameTimeSeconds() * 1 / (difX * 2 * ball.getGravity() * ball.getGroundFriction()));

        }
        phycicsPercision = 0;
        float fax = ax;
        float faz = az;
        int frictionApplied = 0;
        int frictionApplied2 = 0;
        if (debug) System.out.println("ax= " + ax + " az= " + az);
        boolean running1 = true;
        boolean running2 = true;


/*        if (Math.abs(velocity.x) < 0.01f) {
            velocity.x = 0;
        } else {
            velocity.x = velocity.x * groundFriction;
        }

        if (Math.abs(velocity.z) < 0.01f) {
            velocity.z = 0;
        } else {
            velocity.z = velocity.z * groundFriction;
        }*/
        int frictionCallX = 0;
        int frictionCallZ = 0;

        /*while (fax != 0) {
            if (Math.abs(fax) < 0.01f) {
                fax = 0;
            } else {
                fax = fax * ball.getGroundFriction();
            }
            frictionCallX++;
        }
        if(debug) System.out.println("frictionCallX = " + frictionCallX + " fax = " + fax);

        while (faz != 0) {
            if (Math.abs(faz) < 0.01f) {
                faz = 0;
            } else {
                faz = faz * ball.getGroundFriction();
            }
            frictionCallZ++;
        }*/

        if (debug) System.out.println("frictionCallZ = " + frictionCallZ + " faz = " + faz);

/*        while (running1 ) {
            if(frictionApplied>100)running1 = false;
            fax=ax;
            if(debug) System.out.print(frictionApplied);
            frictionApplied++;
            fax *= frictionApplied*  ball.getGroundFriction();

            if(debug) System.out.print("fax" +fax + " ");
        }
        if(debug) System.out.println("fax" + fax);
        while (Math.abs(faz) > 0.01f ) {
            faz=az;
            frictionApplied2++;

            faz *=  frictionApplied2* ball.getGroundFriction();

        }*/
//        if(frictionApplied2>frictionApplied)frictionApplied=frictionApplied2;
//        if(debug) System.out.println("FrictionApplied" + frictionApplied);
         /*   ax +=normal.x*2*ball.getGravity() ;
            az += normal.z*2*ball.getGravity();*/
//        ax += frictionCallX * 1 / ball.getGroundFriction();
         /*   ax +=normal.x*2*ball.getGravity() ;
            az += normal.z*2*ball.getGravity();*/
//        az -= frictionCallZ * 1 / ball.getGroundFriction();
        float sum = 0;
        float sum2 = 0;
        fax = ax;
        faz = az;
        /*for (int i = 8; i < frictionCallX; i++) {
            fax *= ball.getGroundFriction();
            sum += (fax/ball.getGroundFriction())/i;
        }
        
        
        for (int i = 8; i < frictionCallZ; i++) {
             faz *= ball.getGroundFriction();
             sum2 += (faz/ball.getGroundFriction())/i;
        }*/
        if (debug) System.out.println("Sum " + sum + ", Sum2 " + sum2);
        if (debug) System.out.println("fax " + fax + ", faz " + faz);
        if (debug) System.out.println("ax " + ax + ", az " + az);


//        if(debug) System.out.println(1/(ball.getNormal().x*2*ball.getGravity() * ball.getGroundFriction()) - ball.getWindX());
//        ax *= groundFriction;
//        az *= groundFriction;
        az *= 1.6;
        ax *= 1.6;
/*        ax *= 1/(ball.getGravity() * ball.getGroundFriction()) - ball.getWindX();
        az *= 1/(ball.getGravity() * ball.getGroundFriction()) - ball.getWindZ();*/
        //if(debug) System.out.println("velocity " + new Vector3f(ax+sum, ay, az+sum2));
        //if(debug) System.out.println("velocity1 " + new Vector3f(axd, 0, azd));
        // if(debug) System.out.println("x" + (holePos.x - ball.getPosition().x) * 1.6);
        // if(debug) System.out.println("z" + (holePos.y - ball.getPosition().z) * 1.6);
        System.out.println("HC " + new Vector3f(ax, ay, az));
        return new Vector3f(ax, ay, az);
    }

}
