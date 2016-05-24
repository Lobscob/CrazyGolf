package commands;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class GolfBall extends Entity{

	public GolfBall(Vector3f p, float m) {
		super(p, m);
	}
	
	private Vector3f velocity;
	private List<HitCommand> commands= new ArrayList<HitCommand>();
	
	public void move() {
		for(int i=0; i<commands.size(); i++) {
			velocity = commands.get(i).getHit();
			
			super.getPosition().x += velocity.x;
			super.getPosition().y += velocity.y;
			super.getPosition().z += velocity.z;
			
		}
	}
	
	public void addCommand(HitCommand c) {
		commands.add(c);
	}

}
