package AI;

import entities.GoalHole;
import entities.GolfBall;
import entities.Player;
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
    private Evaluator evaluator;
    private Simulation simulation;

    private Vector3f velocity;

    public Vector3f getBotBallVelocity() {
        return botBallVelocity;
    }

    private Vector3f botBallVelocity;

    public AI(GolfBall b,  Terrain t, GoalHole h, Player p) {
        ball = b;
        terrain = t;
        hole = h;
        tools = new AiTools();
        hC = new HillCalculator(ball,hole,terrain );
        simulation= new Simulation(p);


    }
    public void runBot(){
        velocity  = hC.calculateVelocity();
//        botBallVelocity = tools.createVelocity().get(2);
        simulation.simulateHit(ball);
        evaluator = new Evaluator(simulation.getPredictedHits(),simulation.getAllSimulatedShots(),hole);

//        System.out.println("Veloc" +botBallVelocity);
        //ball.setVelocity(simulation.getPredictedHits().get(evaluator.evaluateShot()));
        System.out.println("Velocity " + ball.velocity);
        System.out.println("VelocitySIm " +simulation.getPredictedHits().get(evaluator.evaluateShot()));
    }


}
