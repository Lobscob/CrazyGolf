package AI;

import Testing.Main;
import entities.GolfBall;
import entities.Player;

/**
 * Created by Jeroen on 18/04/2016.
 */

public class BotMover {
    float ballX1 = Main.golfBallUsed1.getPosition().x;
    float ballY1 = Main.golfBallUsed1.getPosition().y;
    float ballZ1 = Main.golfBallUsed1.getPosition().z;

    float ballX2 = Main.golfBallUsed2.getPosition().x;
    float ballY2 = Main.golfBallUsed2.getPosition().y;
    float ballZ2 = Main.golfBallUsed2.getPosition().z;
     
    float playerX1 = Main.getPlayer1().getPosition().x;
    float playerY1 = Main.getPlayer1().getPosition().y;
    float playerZ1 = Main.getPlayer1().getPosition().z;

    float playerX2 = Main.getPlayer2().getPosition().x;
    float playerY2 = Main.getPlayer2().getPosition().y;
    float playerZ2 = Main.getPlayer2().getPosition().z;

    public void moveBotToBall(Player p, GolfBall b){
        float bX = b.getPosition().x;
        float bY = b.getPosition().y;
        float bZ = b.getPosition().z;

        float pX = p.getPosition().x;
        float pY = p.getPosition().y;
        float pZ = p.getPosition().z;
        if(Math.round(pX)!=Math.round(bX) || Math.round(pY) != Math.round(bY) || Math.round(pZ) != Math.round(bZ)){
            if(pX<bX)p.increasePosition(1,0,0);
            else p.increasePosition(-1,0,0);
            if(pY<bY)p.increasePosition(0,1,0);
            else p.increasePosition(0,-1,0);
            if(pZ<bZ)p.increasePosition(0,0,1);
            else p.increasePosition(0,0,-1);
        }
    }


}
