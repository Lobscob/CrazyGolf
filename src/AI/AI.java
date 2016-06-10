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
    private AiTools tools;
    private HillCalculator hC;

    private Vector3f velocity;

    public Vector3f getBotBallVelocity() {
        return botBallVelocity;
    }

    private Vector3f botBallVelocity;

    public AI(GolfBall b,  Terrain t, GoalHole h) {
        ball = b;
        terrain = t;
        hole = h;
        tools = new AiTools(ball,terrain,hole);
        hC = new HillCalculator(ball,hole,terrain );

    }
    public void runBot(){
        velocity  = hC.calculateVelocity();
        botBallVelocity = tools.createVelocity().get(2);
        System.out.println("Veloc" +botBallVelocity);
        ball.setVelocity(botBallVelocity);
    }


}
