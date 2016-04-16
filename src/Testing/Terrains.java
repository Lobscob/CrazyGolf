package Testing;

import Testing.Main;
import entities.Light;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Terrains {
    public static void loadTerrains(Loader loader) {
    	
    	if(loader == null) {
    		System.out.println("OO");
    	}
    	//terrain1
        TerrainTexture backgroundTexture1 = new TerrainTexture(loader.loadTexture("grassTexture1"));
        TerrainTexture rTexture1 = new TerrainTexture(loader.loadTexture("mudTexture1"));
        TerrainTexture gTexture1 = new TerrainTexture(loader.loadTexture("grassTexture2"));
        TerrainTexture bTexture1 = new TerrainTexture(loader.loadTexture("treeTexture"));
        TerrainTexturePack texturePack1 = new TerrainTexturePack(backgroundTexture1, rTexture1, gTexture1, bTexture1);

        //terrain2
        TerrainTexture backgroundTexture2 = new TerrainTexture(loader.loadTexture("rockTexture"));
        TerrainTexture rTexture2 = new TerrainTexture(loader.loadTexture("rockTexture"));
        TerrainTexture gTexture2 = new TerrainTexture(loader.loadTexture("lunaTexture3"));
        TerrainTexture bTexture2 = new TerrainTexture(loader.loadTexture("rockTexture"));
        TerrainTexturePack texturePack2 = new TerrainTexturePack(backgroundTexture2, rTexture2, gTexture2, bTexture2);

        //terrain3
        TerrainTexture backgroundTexture3 = new TerrainTexture(loader.loadTexture("grassTexture1"));
        TerrainTexture rTexture3 = new TerrainTexture(loader.loadTexture("mudTexture1"));
        TerrainTexture gTexture3 = new TerrainTexture(loader.loadTexture("grassTexture2"));
        TerrainTexture bTexture3 = new TerrainTexture(loader.loadTexture("treeTexture"));
        TerrainTexturePack texturePack3 = new TerrainTexturePack(backgroundTexture3, rTexture3, gTexture3, bTexture3);
        
        //terrain 4
        TerrainTexture backgroundTexture4 = new TerrainTexture(loader.loadTexture("grassTexture1"));
        TerrainTexture rTexture4 = new TerrainTexture(loader.loadTexture("mudTexture1"));
        TerrainTexture gTexture4 = new TerrainTexture(loader.loadTexture("grassTexture2"));
        TerrainTexture bTexture4 = new TerrainTexture(loader.loadTexture("treeTexture"));
        TerrainTexturePack texturePack4 = new TerrainTexturePack(backgroundTexture4, rTexture4, gTexture4, bTexture4);
        
        
        texturePack1 = new TerrainTexturePack(backgroundTexture1, rTexture1, gTexture1, bTexture1);
        TerrainTexture blendMap1 = new TerrainTexture(loader.loadTexture("blendMap1"));
        Light light1 = new Light(new Vector3f(1024000, 500000, -1024000), new Vector3f(1, 1, 1));
        Terrain terrain1 = new Terrain("1 - Plain", 0, -1, loader, texturePack1, blendMap1, "heightMap1");
        Main.setTerrain(terrain1, 1);
        Main.setLight(light1, 1);

        TerrainTexture blendMap2 = new TerrainTexture(loader.loadTexture("blendMap2"));
        Terrain terrain2 = new Terrain("2 - Luna", 0, -1, loader, texturePack2, blendMap2, "heightMap2");
        Light light2 = new Light(new Vector3f(1024000, 500000, -1024000), new Vector3f(0.7f, 0.7f, 0.8f));
        Main.setTerrain(terrain2, 2);
        Main.setLight(light2, 2);

        TerrainTexture blendMap3 = new TerrainTexture(loader.loadTexture("blendMap3"));
        Terrain terrain3 = new Terrain("3 - Hills", 0, -1, loader, texturePack3, blendMap3, "heightMap3");
        Light light3 = new Light(new Vector3f(1024000, 1024000, -1024000), new Vector3f(1, 1, 1));
        Main.setTerrain(terrain3, 3);
        Main.setLight(light3, 3);

        TerrainTexture blendMap4 = new TerrainTexture(loader.loadTexture("blendMap4"));
        Terrain terrain4 = new Terrain("4 - Grid",0, -1, loader, texturePack4, blendMap4, "heightMap4");
        Light light4 = new Light(new Vector3f(1024000,1000000,-1024000), new Vector3f(1,1,1));
        Main.setTerrain(terrain4, 4);
        Main.setLight(light4, 4);


    }


}

