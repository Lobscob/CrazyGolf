package entities;

import AI.AI;
import Testing.Main;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.WindNoise;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

import static Testing.Main.holeUsed;

/**
 * This class represents the golf ball
 */

import static Testing.Main.entities;


public class GolfBall extends Entity {

    private static final float GRAVITY = -9.80665f;
    private static final float MASS = 5;
    private static final float RADIUS = 4;
    public Vector3f velocity = new Vector3f(0, 0, 0);
    private float ax = 0;
    private float ay = GRAVITY;
    private float az = 0;

    private boolean isInHole = false;
    private float ballHeuristics;
    private boolean collidingBall;


    private WindNoise windNoise;

    public void setWind(WindNoise w) {
        this.windNoise = w;
    }

    private Vector3f normal;

    public boolean doneRolling() {
        boolean done = false;
        if ((float) Math.sqrt(velocity.x*velocity.x + velocity.z*velocity.z) < 0.0001f) {
            velocity.x = 0;
            velocity.z = 0;
            velocity.y = 0;
            done = true;
        }
        return done;
    }
    public boolean BallDoneRolling() {
        boolean done = false;
        if ((float) Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.z, 2)) < 0.4) {
            velocity.x = 0;
            velocity.z = 0;
            velocity.y = 0;
            done = true;
        }
        return done;
    }



    public void setIsInHole(boolean b) {
        this.isInHole = b;
    }

    public boolean getIsInHole() {
        return this.isInHole;
    }

    /**
     * @param model    a reference to the model
     * @param position a vector representing the position
     * @param rotX     rotation in the x axis
     * @param rotY     rotation in the y axis
     * @param rotZ     rotation in the z axis
     * @param scale    the scale of the model
     */
    public GolfBall(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale, true, new Vector3f(RADIUS, RADIUS, RADIUS));

    }

    public void setGolfBallLocation(Vector3f position) {

        super.setPosition(position);

    }


    public void move(Terrain terrain) {
        if (this.getPosition().z > 0 || this.getPosition().z < -1000) {
            this.velocity.z *= -1;
        }
        if (this.getPosition().x < 0 || this.getPosition().x > 1000) {
            this.velocity.x *= -1;
        }
        //if(Math.abs(this.getPosition().x) == Math.abs(holeUsed.getPosition().x-0.02f) && Math.abs(this.getPosition().z-0.5)==Math.abs(holeUsed.getPosition().z))setIsInHole(true);
        //if((Math.abs(this.getPosition().x) - Math.abs(holeUsed.getPosition().x-0.02f))==0f && (Math.abs(this.getPosition().z-0.5)-Math.abs(holeUsed.getPosition().z))==0)setIsInHole(true);

        if(Math.abs(this.getPosition().x) == Math.abs(holeUsed.getPosition().x-0.02f) && Math.abs(this.getPosition().z-0.5)==Math.abs(holeUsed.getPosition().z))setIsInHole(true);
        if((Math.abs(this.getPosition().x) - Math.abs(holeUsed.getPosition().x-0.02f))<0.1f && (Math.abs(this.getPosition().z-0.5)-Math.abs(holeUsed.getPosition().z))<0.1)setIsInHole(true);
        System.out.println("INHOLE INHOLE IN HOLE" + isInHole);
        float x = 0;
        float y = 0;
        float z = 0;

        float heightL = terrain.getHeightOfTerrain(this.getPosition().x - 1, this.getPosition().z);
        float heightR = terrain.getHeightOfTerrain(this.getPosition().x + 1, this.getPosition().z);
        float heightD = terrain.getHeightOfTerrain(this.getPosition().x, this.getPosition().z - 1);
        float heightU = terrain.getHeightOfTerrain(this.getPosition().x, this.getPosition().z + 1);

        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        //System.out.println(normal.x + " " + normal.y + " " + normal.z);


        if (Math.abs(velocity.x) < 0.01f) {
            velocity.x = 0;
        } else {
            velocity.x = velocity.x * groundFriction;
        }

        if (Math.abs(velocity.z) < 0.01f) {
            velocity.z = 0;
        } else {
            velocity.z = velocity.z * groundFriction;
        }
        Random rand = new Random();

        Vector3f w = windNoise.wind();

        velocity.x += ax * DisplayManager.getFrameTimeSeconds() - (normal.x * 2) * GRAVITY * groundFriction + w.x;
        velocity.y += ay * DisplayManager.getFrameTimeSeconds() + (normal.y) * GRAVITY;
        velocity.z -= az * DisplayManager.getFrameTimeSeconds() + (normal.z * 2) * GRAVITY * groundFriction + w.z;
        //System.out.println(velocity.x + " " +  velocity.y + " " +  velocity.z);

        //w.normalise();    

        x += velocity.x * DisplayManager.getFrameTimeSeconds();
        y += velocity.y * DisplayManager.getFrameTimeSeconds();
        z += velocity.z * DisplayManager.getFrameTimeSeconds();
        //System.out.println(x + " " +  y + " " +  z);

        super.increasePosition(x, y, z);

        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            velocity.y = 0;
            super.getPosition().y = terrainHeight;
        }
    }

    public boolean checkCollision(Entity entity) {
        boolean collision = false;
        //System.out.println("Collision");
        if (this.getPosition().y + this.getCollisionZone().y / 2 < entity.getPosition().y - entity.getCollisionZone().y / 2 ||
                this.getPosition().y - this.getCollisionZone().y / 2 > entity.getPosition().y + entity.getCollisionZone().y / 2) {
            return false;
        }
        Rectangle r1 = new Rectangle((int) (this.getPosition().x - GolfBall.RADIUS),
                (int) (this.getPosition().z - GolfBall.RADIUS),
                (int) GolfBall.RADIUS, (int) (RADIUS));
        Rectangle r2 = new Rectangle((int) (entity.getPosition().x - entity.getCollisionZone().x / 2),
                (int) (entity.getPosition().z - entity.getCollisionZone().z / 2),
                (int) entity.getCollisionZone().x,
                (int) entity.getCollisionZone().z);

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
        collision = ra.intersects(rb.getBounds2D()) && rb.intersects(ra.getBounds2D());
        if(collision){
        	collidingBall = true;
        }
        return collision;
    }

    private Vector2f normalOfImpact(Entity entity) {
        //System.out.println(entity.getModel().toString());
        //System.out.println(Main.wall.toString());
        float theta = entity.getRotY();

        float dz = (float) Math.sin(Math.toRadians(theta));
        float dx = (float) Math.cos(Math.toRadians(theta));

        Vector2f Top = new Vector2f((float) (entity.getPosition().x - entity.getCollisionZone().x / 2 * Math.sin(Math.toRadians(theta))), (float) (entity.getPosition().z - entity.getCollisionZone().z / 2 * Math.cos(Math.toRadians(theta))));
        Vector2f Right = new Vector2f((float) (entity.getPosition().x + entity.getCollisionZone().x / 2 * Math.cos(Math.toRadians(theta))), (float) (entity.getPosition().z - entity.getCollisionZone().z / 2 * Math.sin(Math.toRadians(theta))));
        Vector2f Bottom = new Vector2f((float) (entity.getPosition().x + entity.getCollisionZone().x / 2 * Math.sin(Math.toRadians(theta))), (float) (entity.getPosition().z + entity.getCollisionZone().z / 2 * Math.cos(Math.toRadians(theta))));
        Vector2f Left = new Vector2f((float) (entity.getPosition().x - entity.getCollisionZone().x / 2 * Math.cos(Math.toRadians(theta))), (float) (entity.getPosition().z + entity.getCollisionZone().z / 2 * Math.sin(Math.toRadians(theta))));

        //System.out.println("Top: " + Top);
        //System.out.println("Right: " + Right);
        //System.out.println("Bottom: " + Bottom);
        //System.out.println("Left: " + Left);
        //System.out.println(":::::::::");

        Vector2f TopNormal = new Vector2f(-dz, -dx);
        TopNormal.normalise();
        //System.out.println("Top Normal" + TopNormal);
        Vector2f RightNormal = new Vector2f(dx, -dz);
        RightNormal.normalise();
        //System.out.println("Right Normal" + RightNormal);
        Vector2f BottomNormal = new Vector2f(dz, dx);
        BottomNormal.normalise();
        //System.out.println("Bottom Normal" + BottomNormal);
        Vector2f LeftNormal = new Vector2f(-dx, dz);
        LeftNormal.normalise();
        //System.out.println("Left Normal" + LeftNormal);

        Vector2f ballPosition = new Vector2f(this.getPosition().x, this.getPosition().z);

        Vector2f subT = new Vector2f();
        Vector2f subR = new Vector2f();
        Vector2f subB = new Vector2f();
        Vector2f subL = new Vector2f();
        Vector2f.sub(Top, ballPosition, subT);
        Vector2f.sub(Right, ballPosition, subR);
        Vector2f.sub(Bottom, ballPosition, subB);
        Vector2f.sub(Left, ballPosition, subL);

        if (Vector2f.dot(TopNormal, subT) < 0) {
            //System.out.println("Top");
            return TopNormal;
        } else if (Vector2f.dot(RightNormal, subR) < 0) {
            // System.out.println("Right");
            return RightNormal;
        } else if (Vector2f.dot(BottomNormal, subB) < 0) {
            // System.out.println("Bottom");
            return BottomNormal;
        } else if (Vector2f.dot(LeftNormal, subL) < 0) {
            // System.out.println("Left");
            return LeftNormal;
        } else {
            //System.out.println("End");
            return TopNormal;
        }
    }


    public float getGroundFriction() {
        return groundFriction;
    }

    private float groundFriction = 0.975f;
    private float coefficientOfRestitution = 0.450f;

    public void manageCollision(Entity entity) {
        if (checkCollision(entity) && entity.isEntityObstacle()) {
            //System.out.println("BOUNCE BOUNCE BOUNCE");
            Main.canCollideOther = false;

            Vector2f V = new Vector2f(this.velocity.x, this.velocity.z);
            Vector2f normal = normalOfImpact(entity);

            //normal.normalise();

            Vector2f VPrime = new Vector2f();
            Vector2f u = new Vector2f();
            Vector2f w = new Vector2f();

            float tmp = Vector2f.dot(V, normal);
            u = (Vector2f) normal.scale(tmp);

            Vector2f.sub(V, u, w);
            Vector2f.sub((Vector2f) w.scale(groundFriction), (Vector2f) u.scale(coefficientOfRestitution), VPrime);

            this.velocity.x = VPrime.x;
            this.velocity.z = VPrime.y;
        }
    }


    public float getRadius() {
        return RADIUS;
    }

    public float getMass() {
        return MASS;
    }

    /**
     * @param player reference to the player hitting the ball
     * @param forces a vector of the forces involved in hitting the ball
     */
    public void manageHit(Vector3f forces) {

        float dvx = (float) ((forces.x) * Math.sin(Math.toRadians(this.getRotY())));
        float dvz = (float) ((forces.z) * Math.cos(Math.toRadians(this.getRotY())));
        float dvy = forces.y;

        this.velocity.x += dvx * DisplayManager.getFrameTimeSeconds();
        this.velocity.y += dvy * DisplayManager.getFrameTimeSeconds();
        this.velocity.z += dvz * DisplayManager.getFrameTimeSeconds();
    }


    public void manageSimHit(Vector3f forces) {

        float dvx = (float) (forces.x);
        float dvz = (float) (forces.z);
        float dvy = forces.y;

        this.velocity.x += dvx ;
        this.velocity.y += dvy ;
        this.velocity.z += dvz ;
    }

    private boolean checkCollision(GolfBall golfBall) {
        float dx = this.getPosition().x - golfBall.getPosition().x;
        float dz = this.getPosition().z - golfBall.getPosition().z;

        float distanceSquared = dx * dx + dz * dz;
        boolean collision = distanceSquared < (this.getRadius() * this.getRadius()) + (golfBall.getRadius() * golfBall.getRadius());

        return collision;
    }

    public void manageBallCollision(GolfBall g) {
        if (checkCollision(g) && Main.canCollideBall) {
            Main.canCollideBall = false;

            g.velocity.x = this.velocity.x * coefficientOfRestitution;
            g.velocity.z = this.velocity.z * coefficientOfRestitution;

            this.velocity.x *= -(coefficientOfRestitution * coefficientOfRestitution);
            this.velocity.z *= -(coefficientOfRestitution * coefficientOfRestitution);
            //System.out.println("Ball Collision");
        }

    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getGravity() {
        return GRAVITY;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f calculateHeuristics() {
        Vector3f heuristicsVec = new Vector3f(0, 0, 0);
        if (getIsInHole()) {
            return heuristicsVec;
        }
        heuristicsVec.x = distanceFromHole(holeUsed);
        if(collidingBall){
        	heuristicsVec.y = 10;
        }
        heuristicsVec.z = obstaclesBlocking(holeUsed);
        return heuristicsVec;

    }

    public float distanceFromHole(GoalHole h) {
        Vector3f holePosition = h.getPosition();
        Vector3f ballPosition = this.getPosition();
        float xDistance = Math.abs(holePosition.x - ballPosition.x);
        float zDistance = Math.abs(holePosition.z - ballPosition.z);
        float distance = (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(zDistance, 2));
        return distance;
    }

    public float obstaclesBlocking(GoalHole h) {
        Vector3f holePosition = h.getPosition();
        Vector3f ballPosition = this.getPosition();
        float numberOfObstacles = 0;
        if (holePosition.x < ballPosition.x && holePosition.z < ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x > holePosition.x && entityPos.x < ballPosition.x && entityPos.z > holePosition.z && entityPos.z < ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x < ballPosition.x && holePosition.z > ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x > holePosition.x && entityPos.x < ballPosition.x && entityPos.z < holePosition.z && entityPos.z > ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x > ballPosition.x && holePosition.z < ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x < holePosition.x && entityPos.x > ballPosition.x && entityPos.z > holePosition.z && entityPos.z < ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        if (holePosition.x > ballPosition.x && holePosition.z > ballPosition.z) {
            for (int i = 0; i < entities.size(); i++) {
                Vector3f entityPos = entities.get(i).getPosition();
                if (entityPos.x < holePosition.x && entityPos.x > ballPosition.x && entityPos.z < holePosition.z && entityPos.z > ballPosition.z) {
                    numberOfObstacles++;
                }
            }
        }
        return numberOfObstacles;
    }
    
    public void setHeuristics(float heuristics){
    	ballHeuristics = heuristics;
    }
    
    public float getHeuristics(){
    	return ballHeuristics;
    }
    
    public void noMoreCollision(){
    	collidingBall = true;
    }
}
