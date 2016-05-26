package entities;

import AI.BotMover;
import AI.AI;
//import AI.Simulation;
import AI.HillCalculator;
import AI.Simulation;
import GUIS.GuiTexture;
import Testing.Main;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

/**
 * This class represents a player in the golf game
 */
public class Player extends Entity {

    private final float RUN_SPEED = 50;
    private final float TURN_SPEED = 160;
    private final float GRAVITY = -50;
    private final float JUMP_POWER = 40;

    private float RANGE = 10;
    private static float HIT_FORCE_X = 5000;
    private static float HIT_FORCE_Y = 00;
    private static float HIT_FORCE_Z = 5000;
    
    private int gCounter;


    public Vector3f getHitPower() {
        Vector3f hitVec = new Vector3f(HIT_FORCE_X, HIT_FORCE_Y, HIT_FORCE_Z);
        return hitVec;
    }

    public void setHitPower(Vector3f hitForces) {
        HIT_FORCE_X = hitForces.x;
        HIT_FORCE_Y = hitForces.y;
        HIT_FORCE_Z = hitForces.z;
    }

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upSpeed = 0;

    private int score = 0;

    private boolean inAir = false;
    private GolfBall golfBall;

    public GolfBall getGolfBall() {
        return this.golfBall;
    }

    private boolean turnTaken = false;

    private Player opponent;


//    private BotMover ai1 = new BotMover(Main.getPlayer1(), Main.golfBallUsed1, Main.holeUsed);
    private BotMover ai21 = new BotMover(Main.getPlayer2(), Main.golfBallUsed2, Main.holeUsed);
    AI ai1 = new AI(Main.golfBallUsed1, Main.terrainChoice, Main.holeUsed);
    AI ai2= new AI(Main.golfBallUsed2, Main.terrainChoice, Main.holeUsed);

    /**
     * @param model         a textured model of what the editor will look like in 3D
     * @param position      the position vector of the model
     * @param rotX          rotation around x axis of the model
     * @param rotY          rotation around y axis of the model
     * @param rotZ          rotation around z axis of the model
     * @param scale         scale of the model
     * @param isObstacle    a boolean specifying if the model is an obstacle
     * @param golfBall      a reference to the ball of the player :O
     * @param collisionSize collision vector
     */
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
                  boolean isObstacle, GolfBall golfBall, Vector3f collisionSize, boolean turn, Player opponent) {
        super(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize);
        this.golfBall = golfBall;
        this.turnTaken = turn;
        this.gCounter = 1;
    }

    public boolean isTurnTaken() {
        return turnTaken;
    }

    public void setTurnTaken(boolean turnTaken) {
        this.turnTaken = turnTaken;
    }

    @Override
    public void move(Terrain terrain) {
        //System.out.println("AI1 " + ai1.getAI());
        //System.out.println("AI2 " + ai2.getAI());


//        ai.runBot();


//       if (ai1.getAI()) ai1.shootBall();
        if (ai21.getAI()) ai21.shootBall();
        numberOfFrames++;
        fCounter++;
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));

        super.increasePosition(dx, 0, dz);

        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        upSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if (super.getPosition().y < terrainHeight) {
            upSpeed = 0;
            inAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if (!inAir) {
            inAir = true;
            this.upSpeed = JUMP_POWER;
        }
    }

    public boolean checkCollision(Entity entity) {
        if (this.getPosition().y + this.getCollisionZone().y / 2 < entity.getPosition().y - entity.getCollisionZone().y / 2 ||
                this.getPosition().y - this.getCollisionZone().y / 2 > entity.getPosition().y + entity.getCollisionZone().y / 2) {
            return false;
        }
        Rectangle r1 = new Rectangle((int) (this.getPosition().x - this.getCollisionZone().x / 2), (int) (this.getPosition().z - this.getCollisionZone().z / 2), (int) this.getCollisionZone().x, (int) (this.getCollisionZone().z));
        Rectangle r2 = new Rectangle((int) (entity.getPosition().x - entity.getCollisionZone().x / 2), (int) (entity.getPosition().z - entity.getCollisionZone().z / 2), (int) entity.getCollisionZone().x, (int) entity.getCollisionZone().z);

        Area a = new Area(r1);
        Area b = new Area(r2);

        float angle = (float) Math.toRadians(this.getRotY());
        AffineTransform af = new AffineTransform();
        af.rotate(angle, (int) this.getPosition().x, (int) this.getPosition().z);

        angle = (float) -Math.toRadians(entity.getRotY());
        AffineTransform bf = new AffineTransform();
        bf.rotate(angle, (int) (entity.getPosition().x), (int) (entity.getPosition().z));

        Area ra = a.createTransformedArea(af);//ra is the rotated a, a is unchanged
        Area rb = b.createTransformedArea(bf);//rb is the rotated b, b is unchanged
        return ra.intersects(rb.getBounds2D()) && rb.intersects(ra.getBounds2D());
    }

    public boolean checkInRange() {
        float xDif = golfBall.getPosition().x - this.getPosition().x;
        float zDif = golfBall.getPosition().z - this.getPosition().z;
        double distanceSquared = xDif * xDif + zDif * zDif;

        boolean collision = distanceSquared < (golfBall.getCollisionZone().x + RANGE) * (golfBall.getCollisionZone().z + RANGE);

        return collision;


    }


    public void manageCollision(Entity entity) {
        if (checkCollision(entity)) {
            this.setPosition(new Vector3f((float) (this.getPosition().x - 1f * Math.sin(Math.toRadians(this.getRotY()))), this.getPosition().y, (float) (this.getPosition().z - 1f * Math.cos(Math.toRadians(this.getRotY())))));
        }
    }

    private void increaseHitPower() {
        this.setScale((float) (getScale() * 1.1));
        HIT_FORCE_X *= 1.3;
        HIT_FORCE_Y *= 1.3;
        HIT_FORCE_Z *= 1.3;
    }

    private void decreaseHitPower() {
        if ((HIT_FORCE_X >= 0 && HIT_FORCE_Y >= 0 && HIT_FORCE_Z >= 0)) {
            this.setScale((float) (getScale() * 0.9));
            HIT_FORCE_X /= 1.3;
            HIT_FORCE_Y /= 1.3;
            HIT_FORCE_Z /= 1.3;
        }
    }

    private void checkInputs() {
        if (numberOfFrames >= 30) {
            keyPressed = true;
            numberOfFrames = 0;
        }
        if(fCounter >= 120) {
        	hitAllowed = true;
        	fCounter = 0;
        }
        		
        if (Keyboard.isKeyDown(Keyboard.KEY_Z) && keyPressed) {
				ai21.setAI(true);
//             ai2.runBot();

            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_B) && keyPressed) {
//				ai1.setAI(true);
            ai1.runBot();
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_I) && keyPressed && gCounter<10) {
        	gCounter++;
        	String s = Integer.toString(gCounter);
            increaseHitPower();
            System.out.println("Xforce: " + HIT_FORCE_X);
            System.out.println("Yforce: " + HIT_FORCE_Y);
            System.out.println("Zforce: " + HIT_FORCE_Z);
            GuiTexture power = new GuiTexture(Main.loaderUsed.loadTexture(s), new Vector2f(-0.75f, 0.9f), new Vector2f(0.21f, 0.21f));
            Main.guis.add(power);
            keyPressed = false;

        } else if (Keyboard.isKeyDown(Keyboard.KEY_O) && keyPressed && gCounter>0) {
        	gCounter--;
        	if(Main.guis.size()>2) {
        		Main.guis.remove(Main.guis.size()-1);
        		decreaseHitPower();
        		System.out.println("Xforce: " + HIT_FORCE_X);
                System.out.println("Yforce: " + HIT_FORCE_Y);
                System.out.println("Zforce: " + HIT_FORCE_Z);
        	}
            
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_H) && keyPressed && hitAllowed) {
            hit();
            keyPressed = false;
            hitAllowed = false;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;

        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;

        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            jump();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X) && keyPressed) {
            Simulation sim = new Simulation(this);
            //sim.calculateHitToGoal(this.getGolfBall(), Main.holeUsed);
            //System.out.println("Xforce: " + HIT_FORCE_X);
            //System.out.println("Yforce: " + HIT_FORCE_Y);
            //System.out.println("Zforce: " + HIT_FORCE_Z);
            sim.simulateHit(this.getGolfBall());
            keyPressed = false;
        }

        while (keyPressed) {
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                this.turnTaken = true;
                opponent.setTurnTaken(false);
                keyPressed = false;
            }
            break;
        }
    }


    public int getScore() {
        return score;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    private int numberOfFrames = 0;
    private boolean keyPressed = true;
    private int fCounter= 0;
    private boolean hitAllowed = true;

    public void hit() {
        score++;
        Vector3f forces = new Vector3f(HIT_FORCE_X, HIT_FORCE_Y, HIT_FORCE_Z);
        golfBall.manageHit(forces);
    }
}
