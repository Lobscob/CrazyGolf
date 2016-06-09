package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;
import Testing.Main;

import java.util.Random;

/**
 * This class represents an entity that has the proper functions for editing and placing other entities in a terrain
 * or change the terrain 
 */
public class Editor extends Entity {

    private static final float RUN_SPEED = 100;
    private static final float TURN_SPEED = 200;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upSpeed = 0;
    
   

    private int numberOfFrames = 0;
    private boolean keyPressed = true;

    private boolean inAir = false;
    private GolfBall golfBall;
    private Terrain terrain;
    private Vector3f mouseTerrainPosition;
    private Entity terrainMarker;
    /**
     * Constructor
     * @param model a textured model of what the editor will look like in 3D
     * @param position the position vector of the model
     * @param rotX rotation around x axis of the model
     * @param rotY rotation around y axis of the model
     * @param rotZ rotation around z axis of the model
     * @param scale scale of the model
     * @param isObstacle a boolean specifying if the model is an obstacle (of the Entity class)
     * @param golfBall a reference to the golf ball of the player
     * @param collisionSize collision vector
     */
    public Editor(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
                  boolean isObstacle, GolfBall golfBall, Vector3f collisionSize) {
        super(model, position, rotX, rotY, rotZ, scale, isObstacle, collisionSize);
        this.mouseTerrainPosition = position;
        this.terrainMarker = new Entity(Main.terrainMarker, position, 0, this.getRotY(), 0, 10, false, new Vector3f(0,0,0));
        Main.entities.add(this.terrainMarker);
    }
    
    public void setCurrentTerrainPoint( Vector3f v) {
    	this.mouseTerrainPosition = v;
    }
    
    public void move(Terrain terrain) {
        this.terrain = terrain;
        numberOfFrames++;
        terrainMarker.setPosition(new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z));  
        
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

    private void checkInputs() {


        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;

        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;

        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        Random rand = new Random();
        
        if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
				//Main.entities.get(Main.entities.size()-1).increaseRotation(0, 90, 0);
				keyPressed = false;
			}
        if(numberOfFrames>=20){keyPressed=true;numberOfFrames=0;}
        while(keyPressed){
        	
        	if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
        		if(!Main.entities.isEmpty()) {
        			if(Main.entities.get(Main.entities.size()-1).getModel() == Main.wall) {
        				Entity last = Main.entities.get(Main.entities.size()-1);
        				xConnect = last.getPosition().x + (float) ((last.getCollisionZone().x/2) * (Math.cos(Math.toRadians(last.getRotY()))));
                        zConnect = last.getPosition().z - (float) ((last.getCollisionZone().x/2) * (Math.sin(Math.toRadians(last.getRotY()))));
                        wallCounter--;
        			}
        			Main.entities.remove(Main.entities.size() - 1);
        		}
        		keyPressed = false;
        }
        	else if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            Entity placed = new Entity(Main.rock, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 4, true, new Vector3f(15,10,15));
            //placed.setPosition(new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(x, z), mouseTerrainPosition.z));
            Main.entities.add(placed); 
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            Entity placed = new Entity(Main.tree,new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 10, true, new Vector3f(4,40,4));
            Main.entities.add(placed); 
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
            Entity placed = new Entity(Main.fern, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 1, true, new Vector3f(0,0,0));
            Main.entities.add(placed); 
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
            Entity placed = new Entity(Main.playerModelTextured, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 4, true, new Vector3f(4,14,4));
            Main.entities.add(placed); 
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
            Entity placed = new Entity(Main.staticSphereModel, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 4, true, new Vector3f(4,4,4));
            Main.entities.add(placed);
            keyPressed = false;
        }else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
        	Entity placed = new Entity(Main.crate, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 5, true, new Vector3f(7,7,7));
        	Main.entities.add(placed);
        	keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
        	if (wallCounter == 0) {
        		xConnect = mouseTerrainPosition.x;
        		zConnect = mouseTerrainPosition.z;
        	}
            Entity placed = new Entity(Main.wall, new Vector3f(xConnect , terrain.getHeightOfTerrain(xConnect, zConnect), zConnect), 0, 0, 0, 2f, true, new Vector3f(10, 10, 10));
            wallCounter ++;
                System.out.println("ROT" + placed.getRotY());
            float xN = (float) ((placed.getCollisionZone().x/2) * (Math.cos(Math.toRadians(placed.getRotY())))); 
        	float zN = (float) ((placed.getCollisionZone().x/2) * (Math.sin(Math.toRadians(placed.getRotY()))));
            placed.setPosition(new Vector3f(placed.getPosition().x - xN, placed.getPosition().y, placed.getPosition().z + zN));
            xConnect += Math.sin(Math.toRadians(this.getRotY())) * (placed.getCollisionZone().x);
            zConnect += Math.cos(Math.toRadians(this.getRotY())) * (placed.getCollisionZone().x);
            Main.entities.add(placed);
            keyPressed = false;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
        	Main.goalXPos = (int) mouseTerrainPosition.x;
        	Main.goalZPos = (int) mouseTerrainPosition.z;
            GoalHole placed = new GoalHole(Main.loaderUsed, Main.terrainChoice, Main.golfGoal, new Vector3f(mouseTerrainPosition.x, terrain.getHeightOfTerrain(mouseTerrainPosition.x, mouseTerrainPosition.z), mouseTerrainPosition.z), 0, 0, 0, 5, true, new Vector3f(6f,6f,6f));
            Main.holeUsed = placed;
            keyPressed = false;
        }else if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
        	Main.startX = (int) mouseTerrainPosition.x;
        	Main.startZ = (int) mouseTerrainPosition.z;
            keyPressed = false;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
        	wallCounter = 0;
        }
        	break;
        	}

        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            jump();
        }
        
    }
    /**
     * xConnect and zConnect are used to calculate the next position of a wall segment so walls can be placed consecutively
     */
    private static float xConnect;
    private static float zConnect;
    private static int wallCounter;



}
