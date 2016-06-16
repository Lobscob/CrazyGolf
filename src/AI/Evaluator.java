package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

import static Testing.Main.entities;
import static Testing.Main.holeUsed;

/**
 * Created by Jeroen on 14/06/2016.
 */
public class Evaluator {


    private List<Vector3f> predictedHits;
    private List<GolfBall> allSimulatedShots;
    private GoalHole hole;
    private GolfBall ball;
    private AiTools tools;

    public Evaluator(List<Vector3f> predictedHits,
                     List<GolfBall> allSimulatedShots, GoalHole hole,GolfBall ball) {
        this.predictedHits = predictedHits;
        this.allSimulatedShots = allSimulatedShots;
        this.hole = hole;
        this.ball = ball;
    }

/*    public int evaluateShot() {
        int bestBall = 0;
        float bestDistance = Float.MAX_VALUE;
        Vector3f currentPosition = allSimulatedShots.get(0).getPosition();

        for (int i = 0; i < allSimulatedShots.size(); i++) {
            float distanceToHoleX = Math.abs(currentPosition.getX() - hole.getPosition().getX());
            float distanceToHoleZ = Math.abs(currentPosition.getZ() - hole.getPosition().getZ());
            float distanceToHole = (float)Math.sqrt(distanceToHoleX*distanceToHoleX + distanceToHoleZ*distanceToHoleZ);
            if (distanceToHole<bestDistance){
                bestBall = i;
                bestDistance = distanceToHole;
            }
            currentPosition = allSimulatedShots.get(i).getPosition();
        }
        System.out.println("BestBall: " + bestBall);
        System.out.println("bestDistance: " + bestDistance);
        return bestBall;
    }*/
public Vector3f calculateHeuristics() {
    Vector3f heuristicsVec = new Vector3f(0, 0, 0);
    if (ball.getIsInHole()) {
        return heuristicsVec;
    }
    heuristicsVec.x = distanceFromHole(holeUsed);
    heuristicsVec.z = obstaclesBlocking(holeUsed);
    return heuristicsVec;

}

    public float distanceFromHole(GoalHole h) {
        Vector3f holePosition = h.getPosition();
        Vector3f ballPosition = ball.getPosition();
        float xDistance = Math.abs(holePosition.x - ballPosition.x);
        float zDistance = Math.abs(holePosition.z - ballPosition.z);
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(zDistance, 2));
        return distance;
    }

    public void calculateBest(){

    }

    public float obstaclesBlocking(GoalHole h) {
        Vector3f holePosition = h.getPosition();
        Vector3f ballPosition = ball.getPosition();
        float numberOfObstacles = 0;
        if (holePosition.x < ballPosition.x && holePosition.z < ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x > holePosition.x && entityPos.x < ballPosition.x && entityPos.z > holePosition.z && entityPos.z < ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x < ballPosition.x && holePosition.z > ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x > holePosition.x && entityPos.x < ballPosition.x && entityPos.z < holePosition.z && entityPos.z > ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x > ballPosition.x && holePosition.z < ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x < holePosition.x && entityPos.x > ballPosition.x && entityPos.z > holePosition.z && entityPos.z < ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x > ballPosition.x && holePosition.z > ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x < holePosition.x && entityPos.x > ballPosition.x && entityPos.z < holePosition.z && entityPos.z > ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        return numberOfObstacles;
    }

}
