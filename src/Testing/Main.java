package Testing;

import AI.AI;
import AI.HillCalculator;
import GUIS.GuiRenderer;
import GUIS.GuiTexture;
import actionListeners.ModeListener;
import actionListeners.SaveLoadListener;
import actionListeners.TerrainListener;
import entities.*;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main running class for Crazy Golf
 */

public class Main extends Objects {
    public static JTextField saveTF;
    public static JTextField loadTF;

    private static boolean editorMode = true;

    public static void setEditorMode(boolean b) {
        editorMode = b;
    }

    private static boolean terrainEditorMode = true;

    public static void setTerrainEditorMode(boolean b) {
        terrainEditorMode = b;
    }

    private static boolean playerMode = true;

    public static void setPlayerMode(boolean b) {
        playerMode = b;
    }

    public static List<Entity> entities = new ArrayList<Entity>();
    public static List<GolfBall> simulatedBalls = new ArrayList<GolfBall>();
    public static TexturedModel tree;
    public static TexturedModel crate;
    public static TexturedModel rock;
    public static TexturedModel fern;
    public static TexturedModel playerModelTextured;
    public static TexturedModel staticSphereModel;
    public static TexturedModel wall;
    public static TexturedModel castle;
    public static TexturedModel terrainMarker;
    public static TexturedModel UFO;

    //	public static Light light1;
    public static TexturedModel golfGoal;
    public static List<Terrain> terrains = new ArrayList<Terrain>();
    public static Terrain terrainChoice;
    public static Loader loaderUsed;
    public static int goalXPos = 110;
    public static int goalZPos = -50;
    public static int startX = 250;
    public static int startZ = -250;
    public static GolfBall golfBallUsed1;
    public static GolfBall golfBallUsed2;
    public static GoalHole holeUsed;
    //useless debug variables
    public static int randomObjects = 0;
    public static Loader loader;

    private static JFrame frame;
    private static Camera camera1;
    private static Camera camera2;
    private static Camera editorCamera;
    private static Camera terrainEditorCamera;

    public static Player getPlayer2() {
        return player2;
    }

    public static Player getPlayer1() {
        return player1;
    }

    private static Player player1;
    private static Player player2;
    private static Editor editor;
    private static TerrainEditor terrainEditor;
    //	public static TerrainTexturePack texturePack1;
    private static TerrainTexturePack texturePack2;
    private static TerrainTexturePack texturePack3;
    private static TerrainTexturePack texturePack4;
    //	public static TerrainTexture blendMap1;
    private static TerrainTexture blendMap2;
    private static TerrainTexture blendMap3;
    private static TerrainTexture blendMap4;
    public static Light lightChoice;
    private static Random rand = new Random();
    private static boolean menuChoiceMade = false;
    private static boolean running = true;
    //terrains
    private static Terrain terrain1;
    private static Terrain terrain2;
    private static Terrain terrain3;
    private static Terrain terrain4;
    //lights
    private static Light light1;
    private static Light light2;
    private static Light light3;
    private static Light light4;

    private static MousePicker picker;


    public static void main(String[] args) {

        frame = new JFrame("Menu");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);
        JButton editorButton = new JButton("Editor");
        JButton terrainEditorButton = new JButton("Terrain Editor");
        JButton playerButton = new JButton("Player");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        saveTF = new JTextField("name");
        loadTF = new JTextField("name");
        JPanel panel = new JPanel();
        JPanel resetPanel = new JPanel();

        panel.add(editorButton);
        panel.add(terrainEditorButton);
        panel.add(playerButton);

        JPanel terrainPanel = new JPanel();
        JButton terrain1Button = new JButton("Terrain 1 - Plain");
        JButton terrain2Button = new JButton("Terrain 2 - Luna");
        JButton terrain3Button = new JButton("Terrain 3 - Hills");
        JButton terrain4Button = new JButton("Terrain 4 - Grid");
        JLabel currentTerrain = new JLabel();


        JButton resetButton = new JButton("Reset Terrains");
        JPanel savePanel = new JPanel();
        resetPanel.add(saveButton);
        resetPanel.add(saveTF);
        resetPanel.add(loadButton);
        resetPanel.add(loadTF);

        terrainPanel.add(terrain1Button);
        terrainPanel.add(terrain2Button);
        terrainPanel.add(terrain3Button);
        terrainPanel.add(terrain4Button);
        frame.add(terrainPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
//		frame.add(savePanel);
        frame.add(resetPanel, BorderLayout.SOUTH);

        while (running) {
            setMenuChoiceMade(false);
            DisplayManager.createDisplay();
            Loader loader = new Loader();
            loaderUsed = loader;

            Terrains.loadTerrains(loaderUsed);

            Objects.loadObstacles(loaderUsed);

            ActionListener listener1 = new ModeListener();
            ActionListener terrainListener = new TerrainListener();
            ActionListener saveLoadListener = new SaveLoadListener();

            editorButton.addActionListener(listener1);
            terrainEditorButton.addActionListener(listener1);
            playerButton.addActionListener(listener1);
            terrain1Button.addActionListener(terrainListener);
            terrain2Button.addActionListener(terrainListener);
            terrain3Button.addActionListener(terrainListener);
            terrain4Button.addActionListener(terrainListener);
            resetButton.addActionListener(terrainListener);

            saveButton.addActionListener(saveLoadListener);
            loadButton.addActionListener(saveLoadListener);

            if (terrainChoice == null) {
                currentTerrain.setText("Select a terrain");
                terrainChoice = terrain1;
                lightChoice = light1;
            } else {
                currentTerrain.setText("Current terrain: " + terrainChoice.getID());
            }
            resetPanel.add(currentTerrain);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            while (!isMenuChoiceMade()) {
                System.out.print("");
            }
            frame.setVisible(false);


            Objects.loadBalls(loaderUsed);
            Objects.loadHoll(loaderUsed);

            if (editorMode) {
                editor = new Editor(playerModelTextured, new Vector3f(startX, terrainChoice.getHeightOfTerrain(startX, startZ), startZ), 0, 0, 0, 0, false, golfBallUsed1, new Vector3f(0, 0, 0));
                editorCamera = new Camera(editor);
            } else if (playerMode) {
                player1 = new Player(playerModelTextured, new Vector3f(startX, terrainChoice.getHeightOfTerrain(startX, startZ), startZ), 0, 0, 0, 3, false, golfBallUsed1, new Vector3f(2f, 14f, 2f), false, null);
                player2 = new Player(playerModelTextured, new Vector3f(startX, terrainChoice.getHeightOfTerrain(startX, startZ), startZ), 0, 0, 0, 3, false, golfBallUsed2, new Vector3f(2f, 14f, 2f), true, null);
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                camera1 = new Camera(golfBallUsed1);
                camera2 = new Camera(golfBallUsed2);
            } else if (terrainEditorMode) {
                terrainEditor = new TerrainEditor(playerModelTextured, new Vector3f(startX, terrainChoice.getHeightOfTerrain(startX, startZ), startZ), 0, 0, 0, 0, false, golfBallUsed1, new Vector3f(0, 0, 0));
                terrainEditorCamera = new Camera(terrainEditor);
            }

            if (running) {
                if (editorMode) {
                    run(lightChoice, editorCamera, editor, loaderUsed, terrainChoice, golfBallUsed1);
                } else if (terrainEditorMode) {
                    run(lightChoice, terrainEditorCamera, terrainEditor, loaderUsed, terrainChoice, golfBallUsed1);
                } else {
                    run(lightChoice, camera1, player1, loaderUsed, terrainChoice, golfBallUsed1);
                }
            } else {
                Display.destroy();
            }
        }
    }

    private static int frameCounter = 0;
    public static boolean canCollideBall = true;
    public static boolean canCollideOther = true;

    /**
     * @param light    the lighting of the chosen terrain
     * @param camera   the camera which follows the current player or editor
     * @param player   the entity of the current player model and position
     * @param loader   reference to the Loader Class used in the current Display for rendering models
     * @param terrain  the chosen terrain
     * @param golfBall reference to the golf ball of the current player
     */

    public static void run(Light light, Camera camera, Entity player, Loader loader, Terrain terrain, GolfBall golfBall) {
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        MasterRenderer renderer = new MasterRenderer(loader, camera);

        for (int i = 0; i < randomObjects; i++) {
            float x = rand.nextInt(1024);
            float z = -rand.nextInt(1024);
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(tree, new Vector3f(x, y, z), 0, rand.nextFloat() * 360, 0, 10, true, new Vector3f(4f, 40f, 4f)));
        }


        for (int i = 0; i < randomObjects; i++) {
            float x = rand.nextInt(1024);
            float z = -rand.nextInt(1024);
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(rock, new Vector3f(x, y, z), 0, 0, 0, 4, true, new Vector3f(6f, 4f, 6f)));
        }


        for (int i = 0; i < randomObjects; i++) {
            float x = rand.nextInt(1024);
            float z = -rand.nextInt(1024);
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(fern, rand.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 1, false, new Vector3f(0, 0, 0)));
        }

        //entities.add(new Entity(wall,new Vector3f(350, terrain.getHeightOfTerrain(350, -300) ,-300),0,0,0,3.2f, true, new Vector3f(42,5,3)));
        entities.add(new Entity(castle,new Vector3f(800,terrain.getHeightOfTerrain(800, -800),-800),0,0,0,10, false, new Vector3f(0,0,0)));
        entities.add(new Entity(crate, new Vector3f(330, terrain.getHeightOfTerrain(330, -330), -330), 0, 0, 0, 10, true, new Vector3f(14, 14, 14)));
        entities.add(new Entity(UFO, new Vector3f(0,terrain.getHeightOfTerrain(0,0) + 10,0),0,0,0,10,false,new Vector3f(0,0,0)));
        
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        if (editorMode) {
            GuiTexture editorPanel = new GuiTexture(loader.loadTexture("GUIEditor"), new Vector2f(0.135f, -0.14f), new Vector2f(1.14f, 1.14f));
            guis.add(editorPanel);
        } else {
            GuiTexture playerPanel = new GuiTexture(loader.loadTexture("GUIPlayer"), new Vector2f(0.135f, -0.14f), new Vector2f(1.14f, 1.14f));
            guis.add(playerPanel);
        }
        //System.out.println(guis.size());
        picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

        while (!Display.isCloseRequested()) {
            picker.update();
            frameCounter++;

            if (!terrainEditorMode && !editorMode) {

                if (!player1.isTurnTaken()) {
                    player = player1;
                    camera = camera1;
                    golfBall = player1.getGolfBall();
                } else {
                    player = player2;
                    camera = camera2;
                    golfBall = player2.getGolfBall();
                }

                //renderer.processEntity(player1);
                //renderer.processEntity(player2);

                renderer.processEntity(golfBallUsed1);
                renderer.processEntity(golfBallUsed2);

                golfBallUsed1.move(terrain);
                golfBallUsed2.move(terrain);
            } else if (editorMode) {
                player = editor;
                camera = editorCamera;
                renderer.processEntity(editor);
                Vector3f terrainPoint = picker.getCurrentTerrainPoint();
                if (terrainPoint != null) {
                    player.setCurrentTerrainPoint(terrainPoint);
                }
            } else if (terrainEditorMode) {
                player = terrainEditor;
                camera = terrainEditorCamera;
                renderer.processEntity(terrainEditor);
                Vector3f terrainPoint = picker.getCurrentTerrainPoint();
                if (terrainPoint != null) {
                    player.setCurrentTerrainPoint(terrainPoint);
                }
            }
            //testing Github don't mind me
            //still testing
            renderer.processTerrains(terrain);

            renderer.processEntity(holeUsed);
            if (camera == null) {
                System.out.println("ohno");
            }
            renderer.render(light, camera);
            //SIMULATIONd
            for (int i = 0; i < simulatedBalls.size(); i++) {
                simulatedBalls.get(i).move(terrain);
                renderer.processEntity(simulatedBalls.get(i));
                for (int j = 0; j < entities.size(); j++) {
                    simulatedBalls.get(i).manageCollision(entities.get(j));
                }
            }
            ArrayList<Entity> collisionAlert = new ArrayList<Entity>();
            for (int i = 0; i < entities.size(); i++) {
            	if(entities.get(i).getModel() == UFO) {
            		entities.get(i).increasePosition(0.51f, 0, -0.51f);
            		entities.get(i).increaseRotation(0, 5, 0);
            	}
            	renderer.processEntity(entities.get(i));
            	if(detectInRange(golfBallUsed1, entities.get(i)) || detectInRange(golfBallUsed2, entities.get(i))) {
            		collisionAlert.add(entities.get(i));
            	}
            	player.manageCollision(entities.get(i));
            }
            if (canCollideOther) {
            	for(int i=0; i<collisionAlert.size(); i++) {
                    golfBallUsed1.manageCollision(collisionAlert.get(i));
                    golfBallUsed2.manageCollision(collisionAlert.get(i));
                }
            }
            collisionAlert.clear();
                
            if (canCollideBall) {
                golfBallUsed1.manageBallCollision(golfBallUsed2);
                golfBallUsed2.manageBallCollision(golfBallUsed1);
            }
            if (frameCounter >= 6) {
                frameCounter = 0;
                canCollideBall = true;
                canCollideOther = true;
            }
            holeUsed.checkCollision(golfBall);
            player.move(terrain);
            camera.move();
            guiRenderer.render(guis);

            DisplayManager.updateDisplay();
        }
        if (!editorMode && !terrainEditorMode) {
            if (player1.getScore() < player2.getScore()) {
                System.out.println("PLAYER 1 WINS \\(*o*)/");
            } else if (player2.getScore() < player1.getScore()) {
                System.out.println("PLAYER 2 WINS \\(*o*)/");
            } else {
                System.out.println("ITS A TIE \\_(o.o)_/");
            }
        }

        guiRenderer.cleanUP();
        renderer.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
        frame.setVisible(true);
    }

    public static void setMenuChoice(boolean menuChoice) {
        setMenuChoiceMade(menuChoice);
    }

    public static TexturedModel getModel(int i) {
        if (i == 0) return playerModelTextured;
        else if (i == 1) return staticSphereModel;
        else if (i == 2) return golfGoal;
        else if (i == 3) return tree;
        else if (i == 4) return rock;
        else if (i == 5) return fern;
        else if (i == 6) return wall;
        else if (i == 7) return castle;
        else if (i == 8) return crate;
        return terrainMarker;

    }

    public static Terrain getTerrain(int i) {
        if (i == 1) return terrain1;
        if (i == 2) return terrain2;
        if (i == 3) return terrain3;
        if (i == 4) return terrain4;
        return null;
    }

    public static Light getLight(int i) {
        if (i == 1) return light1;
        if (i == 2) return light2;
        if (i == 3) return light3;
        if (i == 4) return light4;
        return null;
    }

    public static void setTerrain(Terrain t, int i) {
        if (i == 1) terrain1 = t;
        if (i == 2) terrain2 = t;
        if (i == 3) terrain3 = t;
        if (i == 4) terrain4 = t;
    }

    public static void setLight(Light l, int i) {
        if (i == 1) light1 = l;
        if (i == 2) light2 = l;
        if (i == 3) light3 = l;
        if (i == 4) light4 = l;
    }

    public static void setGolfBall1(GolfBall g) {
        golfBallUsed1 = g;
    }

    public static void setGolfBall2(GolfBall g) {
        golfBallUsed2 = g;
    }

    public static boolean isMenuChoiceMade() {
        return menuChoiceMade;
    }

    public static void setMenuChoiceMade(boolean menuChoiceMade) {
        Main.menuChoiceMade = menuChoiceMade;
    }

    public static Terrain getTerrain(String s) {
        int i = Integer.parseInt(s.substring(0, 1));
        if (i == Integer.parseInt(terrain1.getID().substring(0, 1))) return terrain1;
        else if (i == Integer.parseInt(terrain2.getID().substring(0, 1))) return terrain2;
        else if (i == Integer.parseInt(terrain3.getID().substring(0, 1))) return terrain3;
        return terrain4;

    }

    public static boolean getPlayerMode() {
        return playerMode;
    }
    
    private static boolean detectInRange(GolfBall golfBall, Entity entity) {
		float dx = entity.getPosition().x - golfBall.getPosition().x;
		float dz = entity.getPosition().z - golfBall.getPosition().z;
		
		float distanceSquared = dx*dx + dz*dz;
		boolean collision = distanceSquared < 200;
		
		return collision;
	}
}