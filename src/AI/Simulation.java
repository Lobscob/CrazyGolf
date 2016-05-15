package AI;

import static Testing.Main.staticSphereModel;
import static Testing.Main.terrainChoice;

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
	private Player AI;
	
	private List<Vector3f> predictedHits;
	
	public Simulation(Player player) {
		AI = player;
		this.HitPower = new Vector3f(0,0,0);
		this.HeuristicValues = new Vector3f(0,0,0);
		this.collided = false;
		this.rotation = 0;
	}
	
	public Vector3f calculateHitToGoal(GolfBall golfBall, GoalHole golfHole) {
		float dx = golfHole.getPosition().x - golfBall.getPosition().x;
		float dz = golfHole.getPosition().z - golfBall.getPosition().z;
		
		float vx = -dx / (DisplayManager.getFrameTimeSeconds() * golfBall.getGroundFriction() * 0.6f);
		float vz = dz / (DisplayManager.getFrameTimeSeconds() * golfBall.getGroundFriction() * 0.6f);
		Vector3f hitPower = new Vector3f(vx,0,vz);
		this.HitPower = hitPower;
		AI.setHitPower(hitPower);
		return HitPower;
	}
	public void calculateHeuristics() {
		
	}
	
	public void simulateHit(GolfBall golfBall) {
		float x = golfBall.getPosition().x;
		float z = golfBall.getPosition().z;
		GolfBall simulationBall = new GolfBall(staticSphereModel, new Vector3f(x, terrainChoice.getHeightOfTerrain(x, z), z), 0, 0, 0, 2);
		Main.simulatedBalls.add(simulationBall);
		for(int i=0; i<Main.simulatedBalls.size(); i++) {
			if(Main.simulatedBalls.get(i) == simulationBall && debug) {
				System.out.println("Sim ball present");
			}
			Vector3f simHit = calculateHitToGoal(Main.simulatedBalls.get(i), Main.holeUsed);
			System.out.println(simHit);
			simulationBall.manageSimHit(simHit);
		}
	}
	
	public void clearSim() {
		Main.simulatedBalls.clear();
	}
	
	public float angleAvoid(Entity entity, GolfBall golfBall) {
		final float epsilon = 1;
		
		float a = (entity.getCollisionZone().x * (float) Math.cos(Math.toRadians(entity.getRotY())))/2;
		float bx = entity.getPosition().x - golfBall.getPosition().x;
		float bz = entity.getPosition().z - golfBall.getPosition().z;
		float b = (float) Math.sqrt(bx*bx+bz*bz);
		float theta = (float) Math.atan((a/b));
		theta += epsilon;
		
		return theta;
	}
	
}
