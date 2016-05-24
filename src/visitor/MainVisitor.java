package visitor;

import org.lwjgl.util.vector.Vector3f;

public class MainVisitor {
	
	public static void main(String[] args) {
		SupremeAI AI = new MarsAI();
		GolfBall golfBall = new GolfBall(new Vector3f(0,0,0), 1);
		
		golfBall.accept(AI);
	}
}
