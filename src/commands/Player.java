package commands;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity
{
	public Player(Vector3f p, float m, GolfBall g) {
		super(p, m);
		this.g = g;
	}

	private float FX = 1000;
	private float FY = 0;
	private float FZ = 1000;
	private GolfBall g;
	

	public void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_H)) {
			Vector3f v = new Vector3f(FX,FY,FZ);
			HitCommand hit = new HitCommand(v);
			g.addCommand(hit);
			
		}
	}
}
