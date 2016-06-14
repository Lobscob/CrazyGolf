package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Jeroen on 14/06/2016.
 */
public class Evaluator {

    private List<Vector3f> predictedHits;
    private List<GolfBall> allSimulatedShots;
    private GoalHole hole;
    private AiTools tools;

    public Evaluator(List<Vector3f> predictedHits,
                     List<GolfBall> allSimulatedShots, GoalHole hole) {
        this.predictedHits = predictedHits;
        this.allSimulatedShots = allSimulatedShots;
        this.hole = hole;
    }

    public int evaluateShot() {
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
    }

}
