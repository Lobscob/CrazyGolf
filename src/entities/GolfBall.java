package entities;

import AI.AI;
import Testing.Main;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Random;

import static Testing.Main.golfBallUsed1;
import static Testing.Main.holeUsed;

/**
 * This class represents the golf ball
 */
public class GolfBall extends Entity {

    private static final float GRAVITY = -9.80665f;
    private static final float MASS = 5;
    private static final float RADIUS = 4;
    public Vector3f velocity = new Vector3f(0, 0, 0);
    private float ax = 0;
    private float ay = GRAVITY;
    private float az = 0;

    private boolean isInHole = false;
    private float windX;
    private float windZ;
    private Vector3f normal;



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
        float windx = 0;
        float windz = 0;
        if (rand.nextFloat() < 0.01f) {
            windx += rand.nextFloat() / 5f;
            windz += rand.nextFloat() / 5f;
            if (rand.nextFloat() < 0.4) {
                windx *= -1;
            }
            if (rand.nextFloat() < 0.4) {
                windz *= -1;
            }
        }


            velocity.x += ax * DisplayManager.getFrameTimeSeconds() - (normal.x * 2) * GRAVITY + windx;
            velocity.y += ay * DisplayManager.getFrameTimeSeconds() + (normal.y) * GRAVITY;
            velocity.z -= az * DisplayManager.getFrameTimeSeconds() + (normal.z * 2) * GRAVITY + windz;
            //System.out.println(velocity.x + " " +  velocity.y + " " +  velocity.z);



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
        return collision;
    }

    private Vector2f normalOfImpact(Entity entity) {

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
        if (entity.getModel() == Main.wall) {
            return TopNormal;
        }
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

        if (Vector2f.dot(subT, TopNormal) < 0) {
            System.out.println("Top");
            return TopNormal;
        } else if (Vector2f.dot(subR, RightNormal) < 0) {
            System.out.println("Right");
            return RightNormal;
        } else if (Vector2f.dot(subB, BottomNormal) < 0) {
            System.out.println("Bottom");
            return BottomNormal;
        } else if (Vector2f.dot(subL, LeftNormal) < 0) {
            System.out.println("Left");
            return LeftNormal;
        } else {
            System.out.println("End");
            return TopNormal;
        }
    }

    private float groundFriction = 0.975f;

    public float getGroundFriction() {
        return groundFriction;
    }

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

        float dvx = (float) (forces.x); //Math.sin(Math.toRadians(this.getRotY())));
        float dvz = (float) (forces.z); //Math.cos(Math.toRadians(this.getRotY())));
        float dvy = forces.y;

        this.velocity.x -= dvx * DisplayManager.getFrameTimeSeconds();
        this.velocity.y += dvy * DisplayManager.getFrameTimeSeconds();
        this.velocity.z += dvz * DisplayManager.getFrameTimeSeconds();
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

    public float getWindX() {
        return windX;
    }

    public float getWindZ() {
        return windZ;
    }

    public Vector3f getNormal() {
        return normal;
    }
}
