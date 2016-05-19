package AI;

import entities.GoalHole;
import entities.GolfBall;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

/**
 * Created by Jeroen on 19/05/2016.
 */
public class AI {
    private GolfBall ball;

    private Terrain terrain;

    private GoalHole hole;

    public Vector3f getBotBallVelocity() {
        return botBallVelocity;
    }

    private Vector3f botBallVelocity;

    public AI(GolfBall b,  Terrain t, GoalHole h) {
        ball = b;
        terrain = t;
        hole = h;
    }
    public void runBot(){
        HillCalculator hC = new HillCalculator(ball,hole,terrain );
        ball.setVelocity( hC.calculateVelocity());
    }


}
