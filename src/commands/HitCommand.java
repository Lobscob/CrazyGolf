package commands;

import org.lwjgl.util.vector.Vector3f;

public class HitCommand {
	
	private float fx;
	private float fy;
	private float fz;
	
	public HitCommand(Vector3f v) {
		this.fx = v.x;
		this.fy = v.y;
		this.fz = v.z;
		
	}
	
	public Vector3f getHit() {
		Vector3f hit = new Vector3f(fx,fy,fz);
		return hit;
	}

}
