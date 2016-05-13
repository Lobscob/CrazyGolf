package AI;

import entities.GoalHole;
import entities.GolfBall;
import entities.Player;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;

/**
 * Created by Jeroen on 18/04/2016.
 */

public class BotMover {

    public GolfBall golfBall;
    private Player player;
    private GoalHole golfHole;
    boolean ai =false;

    public void setX(Boolean x) {
        this.x = x;
    }

    private Boolean x = true;
    private int hitForce = 95;

    public BotMover(Player p, GolfBall b, GoalHole h) {
        player = p;
        golfBall = b;
        golfHole = h;

    }


    public void moveBotToBall(Player p, GolfBall b) {
        float bX = Math.round(b.getPosition().x);
        float bY = Math.round(b.getPosition().y);
        float bZ = Math.round(b.getPosition().z);

        float pX = Math.round(p.getPosition().x);
        float pY = Math.round(p.getPosition().y);
        float pZ = Math.round(p.getPosition().z);
        if (Math.round(pX) != Math.round(bX) || Math.round(pY) != Math.round(bY) || Math.round(pZ) != Math.round(bZ)) {
            if (pX < bX) p.increasePosition(1, 0, 0);
            else p.increasePosition(-1, 0, 0);
            if (pY < bY) p.increasePosition(0, 1, 0);
            else p.increasePosition(0, -1, 0);
            if (pZ < bZ) p.increasePosition(0, 0, 1);
            else p.increasePosition(0, 0, -1);
        }
    }

    public void shootBall() {
//        while(x){

            float dvx = (-golfBall.getPosition().x + golfHole.getPosition().x) * hitForce;
            float dvy = (-golfBall.getPosition().y + golfHole.getPosition().y) * hitForce;
            float dvz = (-golfBall.getPosition().z + golfHole.getPosition().z) * hitForce;

//            System.out.println("DVX " + dvx);
//            System.out.println("DVy " + dvy);
//            System.out.println("DVz " + dvz);
//            Vector3f forces= new Vector3f(dvx,dvy,dvz);
//            golfBall.manageHit(player,forces);
            if (Math.abs(golfBall.velocity.x) == 0 && Math.abs(golfBall.velocity.y) == 0 && Math.abs(golfBall.velocity.z) == 0) {
                golfBall.velocity.x += dvx * DisplayManager.getFrameTimeSeconds();
                golfBall.velocity.y += dvy * DisplayManager.getFrameTimeSeconds();
                golfBall.velocity.z += dvz * DisplayManager.getFrameTimeSeconds();

        }
//    x = false;
    }


    public void setAI(boolean b) {
        ai = true;
    }

    public boolean getAI() {
        return ai;
    }
}
