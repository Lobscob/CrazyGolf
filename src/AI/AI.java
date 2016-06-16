package AI;

import Testing.Main;
import entities.GoalHole;
import entities.GolfBall;
import entities.Player;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

import java.util.List;

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

    public static List<Vector3f> predictedHits;
    public static List<GolfBall> allSimulatedShots;

    public AI(GolfBall b,  Terrain t, GoalHole h, Player p) {
        ball = b;
        terrain = t;
        hole = h;
        tools = new AiTools();
        hC = new HillCalculator(ball,hole,terrain );
        
    }
    public void runBot(){
    	simulation= new Simulation();
        velocity  = hC.calculateVelocity();
//        botBallVelocity = tools.createVelocity().get(2);
        predictedHits = simulation.getPredictedHits();
        simulation.simulateHit(ball);
        
        allSimulatedShots = simulation.getAllSimulatedShots();
        System.out.println("ROLLING... " +simulation.rollingBalls());
/*        while(simulation.rollingBalls()){
            System.out.println("ROLLING... " +simulation.rollingBalls());
//            for(int i =0; i<simulation.getAllSimulatedShots().size();i++)
//            simulation.getAllSimulatedShots().get(i).move(terrain);
        }*/

//        evaluator.evaluate();

//        useVelocity();
//        System.out.println("Veloc" +botBallVelocity);
        System.out.println("Velocity " + ball.velocity);
//        System.out.println("VelocitySIm " +simulation.getPredictedHits().get(evaluator.evaluateShot()));
    }
    public void evaluate(){
        evaluator = new Evaluator(simulation.getPredictedHits(),simulation.getAllSimulatedShots(),hole , ball);
        
        if(evaluator.evaluate()) {
        	useVelocity();
        }
        
    }
    public void useVelocity(){
        ball.manageSimHit(predictedHits.get(Main.bestIndex));
    }


}
