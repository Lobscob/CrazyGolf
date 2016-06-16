package AI;

import static Testing.Main.simulatedBalls;
import static Testing.Main.staticSphereModel;
import static Testing.Main.terrainChoice;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import Testing.Main;
import entities.Entity;
import entities.GoalHole;
import entities.GolfBall;
import entities.Player;
import renderEngine.DisplayManager;

public class Simulation {

    private boolean debug = false;

    private Vector3f HitPower;
    private Vector3f HeuristicValues;
    private boolean collided;
    private float rotation;


    private  List<Vector3f> predictedHits;
    private List<GolfBall> allSimulatedShots;

    private AiTools calculators;

    public Simulation() {
     
        this.HitPower = new Vector3f(0, 0, 0);
        this.HeuristicValues = new Vector3f(0, 0, 0);
        this.collided = false;
        this.rotation = 0;

        calculators = new AiTools();
        predictedHits = calculators.createVelocity();
        allSimulatedShots = new ArrayList<GolfBall>();
        //System.out.println(predictedHits.size());
    }

    public Vector3f calculateHitToGoal(GolfBall golfBall, GoalHole golfHole) {
        float dx = golfHole.getPosition().x - golfBall.getPosition().x;
        float dz = golfHole.getPosition().z - golfBall.getPosition().z;

        float vx = -dx / (DisplayManager.getFrameTimeSeconds() * golfBall.getGroundFriction() * 0.6f);
        float vz = dz / (DisplayManager.getFrameTimeSeconds() * golfBall.getGroundFriction() * 0.6f);
        Vector3f hitPower = new Vector3f(vx, 0, vz);
        this.HitPower = hitPower;
        //AI.setHitPower(hitPower);
        return HitPower;
    }


    public boolean rollingBalls(){
       float totalVelocityX = 0;
       float totalVelocityZ = 0;
        for(int i =0;i<allSimulatedShots.size();i++){
            totalVelocityX+= allSimulatedShots.get(i).velocity.x;
            totalVelocityZ+= allSimulatedShots.get(i).velocity.z;

        }
        if(Math.abs(totalVelocityX)==0 && Math.abs(totalVelocityZ)==0)return false;
        else return true;
    }



    public void simulateHit(GolfBall golfBall) {
        for (int k = 0; k < predictedHits.size(); k++) {
            float x = golfBall.getPosition().x;
            float z = golfBall.getPosition().z;
            GolfBall simulationBall = new GolfBall(staticSphereModel, new Vector3f(x, terrainChoice.getHeightOfTerrain(x, z), z), 0, 0, 0, 2);
            Main.simulatedBalls.add(simulationBall);
            allSimulatedShots.add(simulationBall);
            Vector3f simHit = predictedHits.get(k);
            //System.out.println(simHit);
            simulationBall.setVelocity(simHit);
        }

    }

    public float angleAvoid(Entity entity, GolfBall golfBall) {
        final float epsilon = 1;

        float a = (entity.getCollisionZone().x * (float) Math.cos(Math.toRadians(entity.getRotY()))) / 2;
        float bx = entity.getPosition().x - golfBall.getPosition().x;
        float bz = entity.getPosition().z - golfBall.getPosition().z;
        float b = (float) Math.sqrt(bx * bx + bz * bz);
        float theta = (float) Math.atan((a / b));
        theta += epsilon;

        return theta;
    }

    public List<GolfBall> getAllSimulatedShots() {
        return allSimulatedShots;
    }

    public List<Vector3f> getPredictedHits() {
        return predictedHits;
    }

}
