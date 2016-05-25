package commands;

import org.lwjgl.util.vector.Vector3f;

public class MainCommands {

	public static void main(String[] args) {
		GolfBall golfBall = new GolfBall(new Vector3f(10,0,10), 1);
		Player player = new Player(new Vector3f(0,0,0), 10, golfBall);
		
		boolean running = true;
		while(running) {
			player.checkInputs();
			
			//made up running condition, when golfball comes to bounds
			if(golfBall.getPosition().x > 100) {  
				running = false;
			}
		}
		

	}

}
