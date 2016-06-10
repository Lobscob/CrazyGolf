package Testing;

import entities.GoalHole;
import entities.GolfBall;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

import static Testing.Main.*;

/**
 * Created by Jeroen on 13/04/2016.
 */
public class Objects {

    public static void loadBalls(Loader loader) {
        GolfBall golfBall1 = new GolfBall(staticSphereModel, new Vector3f(Main.startX, terrainChoice.getHeightOfTerrain(Main.startX, Main.startZ), Main.startZ), 0, 0, 0, 1);
        GolfBall golfBall2 = new GolfBall(staticSphereModel, new Vector3f(Main.startX + 10, terrainChoice.getHeightOfTerrain(Main.startX + 10, Main.startZ + 10), Main.startZ + 10), 0, 0, 0, 1);
        Main.setGolfBall1(golfBall1);
        Main.setGolfBall2(golfBall2);

    }

    public static void loadObstacles(Loader loader) {
        RawModel playerModel = OBJLoader.loadObjModel("GolfStick", loader);
        ModelTexture playerTexture = new ModelTexture(loader.loadTexture("sphereTexture"));
        playerModelTextured = new TexturedModel(playerModel, playerTexture, 0);

        RawModel ufoModel = OBJLoader.loadObjModel("UFOModel", loader);
        UFO = new TexturedModel(ufoModel, playerTexture,10);
        
        RawModel sphereModel = OBJLoader.loadObjModel("Sphere", loader);
        ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphereTexture1"));
        staticSphereModel = new TexturedModel(sphereModel, sphereTexture,1);
        
        RawModel goalModel = OBJLoader.loadObjModel("GolfGoal", loader);
        ModelTexture goalTexture = new ModelTexture(loader.loadTexture("sphereTexture"));
        golfGoal = new TexturedModel(goalModel, goalTexture,2);
        
        RawModel tree1Model = OBJLoader.loadObjModel("Tree", loader);
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("treeTexture"));
        tree = new TexturedModel(tree1Model, treeTexture,3);
        tree.getTextureModel().setHasTransparency(true);
        
        RawModel rockModel = OBJLoader.loadObjModel("rock", loader);
        ModelTexture rockTexture = new ModelTexture(loader.loadTexture("rockTexture"));
        rock = new TexturedModel(rockModel, rockTexture,4);

        RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        fern = new TexturedModel(fernModel, fernTextureAtlas,5);
        fern.getTextureModel().setHasTransparency(true);
        fern.getTextureModel().setUseFakeLighting(true);

        RawModel wallModel = OBJLoader.loadObjModel("cubeWall", loader);
        ModelTexture wallTexture = new ModelTexture(loader.loadTexture("simpleWallTexture"));
        wall = new TexturedModel(wallModel, wallTexture,6);

        RawModel castleModel = OBJLoader.loadObjModel("Castle", loader);
        ModelTexture castleTexture = new ModelTexture(loader.loadTexture("simpleWallTexture"));
        castle = new TexturedModel(castleModel, castleTexture,7);

        RawModel crateModel = OBJLoader.loadObjModel("ComplexCrate", loader);
        ModelTexture crateTexture = new ModelTexture(loader.loadTexture("crateTexture"));
        crate = new TexturedModel(crateModel, crateTexture,8);
        crate.getTextureModel().setHasTransparency(true);
        
        RawModel terrainMarkerModel = OBJLoader.loadObjModel("TerrainMarker", loader);
        terrainMarker = new TexturedModel(terrainMarkerModel, crateTexture,9);
        terrainMarker.getTextureModel().setHasTransparency(true);		

    }
    public static void loadHoll(Loader loader){
        holeUsed = new GoalHole(loader, terrainChoice, golfGoal, new Vector3f(Main.goalXPos, terrainChoice.getHeightOfTerrain(Main.goalXPos, Main.goalZPos), Main.goalZPos), 0, 0, 0, 5, true, new Vector3f(7f, 7f, 7f));
       if(Main.getPlayerMode()) {terrainChoice.createHole(holeUsed.getPosition().x, holeUsed.getPosition().z, 5);}
        terrainChoice.updateTerrain(loader);

    }

}

