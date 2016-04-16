package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;
import testing.Main;

import java.util.Random;

/**
 * This class represents an entity that has the proper functions for editing and placing other entities in a terrain
 * or change the terrain 
 */
public class TerrainEditor extends Entity {

    private static final float RUN_SPEED = 100;
    private static final float TURN_SPEED = 200;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float RANGE = 10;
    private final float HIT_FORCE_X = 10000;
    private final float HIT_FORCE_Y = 10000;
    private final float HIT_FORCE_Z = 10000;
    private static final float PUTTER_MASS = 10;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upSpeed = 0;
    
   

    private int numberOfFrames = 0;
    private boolean keyPressed = true;

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
    public TerrainEditor(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
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
            super.getPosition().y = terrainHeight;
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
				Main.entities.get(Main.entities.size()-1).increaseRotation(0, 1, 0);
				keyPressed = false;
			}
        if(numberOfFrames>=20){keyPressed=true;numberOfFrames=0;}
        
        while(keyPressed){
        	brushSize = (int) terrainMarker.getScale()/2;
        	if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
        		if(!Main.entities.isEmpty() && Main.entities.get(Main.entities.size()-1) == terrainMarker) {
        			Main.entities.remove(Main.entities.size() - 1);
        		}
        		keyPressed = false;
        	}else if(Main.loaderUsed.vbos.size()<1700)
        		if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
        			terrain.createDepression(terrainMarker.getPosition().x, terrainMarker.getPosition().z, this.brushSize, this.intensity);
        			terrain.updateTerrain(Main.loaderUsed);
        			keyPressed = false;
        		}else if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
        			terrain.createRise(terrainMarker.getPosition().x, terrainMarker.getPosition().z, this.brushSize, this.intensity);
        			terrain.updateTerrain(Main.loaderUsed);
        			keyPressed = false;
        		}else if(Keyboard.isKeyDown(Keyboard.KEY_H)) {
        			terrain.createPlateau(terrainMarker.getPosition().x, terrainMarker.getPosition().z, this.brushSize);
        			terrain.updateTerrain(Main.loaderUsed);
        			keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
        		terrainMarker.setScale(2);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
        		terrainMarker.setScale(4);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_3)) {
        		terrainMarker.setScale(8);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_4)) {
        		terrainMarker.setScale(10);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_5)) {
        		terrainMarker.setScale(16);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_6)) {
        		terrainMarker.setScale(20);
        		keyPressed = false;
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
        		if(intensity > 0) {
        			intensity--;
        			keyPressed = false;
        		}
        	}else if(Keyboard.isKeyDown(Keyboard.KEY_K)) {
        		if(intensity < 3) {
        			intensity++;
        			keyPressed = false;
        		}
        	}
        	break;
        }

    }
    private int brushSize;
    private float intensity = 1;
    
    public void hit() {
        Vector3f forces = new Vector3f(this.HIT_FORCE_X, this.HIT_FORCE_Y, this.HIT_FORCE_Z);
        golfBall.manageHit(this, forces);
    }


}