package terrains;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class represents the terrain of the golf course
 */
public class Terrain {
	
	private static final float SIZE = 1024;
	
	private static final float MAX_HEIGHT = 20;
	private static final float MIN_HEIGHT = -20;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;

	private String ID;
	public String getID() { return this.ID; }
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private int index;
	
	private float[][] heights;
	private float[][] originHeights;
	
	/**
	 * @param ID id of the terrain texture position
	 * @param gridX size in x dimension
	 * @param gridZ size in z dimension
	 * @param loader loader used to load the texture for the terrain
	 * @param texturePack reference to the different types of terrain textures used for this specific terrain
	 * @param blendMap the file used to determine textures
	 * @param heightMap the file used to determine heights
	 */
	public Terrain(String ID, int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap) {
		this.ID = ID;
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
		//this.index = i;
	}
	
	public void updateTerrain(Loader loader) {
			
		int VERTEX_COUNT = heights.length;
			
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float height = heights[j][i];
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				
				heights[j][i] = height;
				Vector3f normal = calculateNormal(j, i);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;					
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;				
				}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;					
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		this.model = loader.loadNewTerrain(vertices, textureCoords, normals, indices);
	}
	
	/**
	 * @param x x position
	 * @param z z position 
	 * @return the normal vector at that specific position
	 */
	public Vector3f calculateNormal(int x, int z) {
		float heightL =  0;
		float heightR = 0;
		float heightD = 0;
		float heightU = 0;
		if(x>0 && x<heights.length-1 && z>0 && z<heights.length-1) {
			heightL = heights[x-1][z];
			heightR = heights[x+1][z];
			heightD = heights[x][z-1];
			heightU = heights[x][z+1];
		}
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
		normal.normalise();
		return normal;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}
	
	/**
	 * @param worldX an x position on the map
	 * @param worldZ a z position on the map
	 */
	public void createHole(float worldX, float worldZ, float intensity) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = (SIZE) / ((float) heights.length -1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		heights[gridX][gridZ] -= intensity;
	}
	
	public void createDepression(float worldX, float worldZ, int brushSize, float intensity) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = (SIZE) / ((float) heights.length -1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		heights[gridX][gridZ] -= intensity;
		for (int i=-brushSize; i<=brushSize; i++) {
			for(int j=-brushSize; j<=brushSize; j++) {
				if(gridX+i>0 && gridX+i<256 && gridZ+j>0 && gridZ+j<256) {
					//heights[gridX+i][gridZ+j] -= intensity;
				}
			}
		}
	}
	public void createRise(float worldX, float worldZ, int brushSize, float intensity) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = SIZE / ((float) heights.length -1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		for (int i=-brushSize; i<=brushSize; i++) {
			for(int j=-brushSize; j<=brushSize; j++) {
				if(gridX+i>0 && gridX+i<256 && gridZ+j>0 && gridZ+j<256) {
					heights[gridX+i][gridZ+j] += intensity;
				}
			}
		}
	}
	public void createPlateau(float worldX, float worldZ, int brushSize) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = SIZE / ((float) heights.length -1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		float intensity = heights[gridX][gridZ];
		for (int i=-brushSize; i<=brushSize; i++) {
			for(int j=-brushSize; j<=brushSize; j++) {
				if(gridX+i>0 && gridX+i<256 && gridZ+j>0 && gridZ+j<256) {
					heights[gridX+i][gridZ+j] = intensity;
				}
			}
		}
	}
	
	/**
	 * @param worldX an x position on the map
	 * @param worldZ a z position on the map
	 * @return the height of the terrain at that position
	 */
	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		
		float gridSquareSize = SIZE / ((float) heights.length -1);
		
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		
		if(gridX >= heights.length -1 || gridZ >= heights.length -1 || gridX <0 || gridZ < 0){
			return 0;
		}
		
		float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
		
		float answer;
		if (xCoord <= (1-zCoord)) {
			answer = Maths
					.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ], 0), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths
					.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
							heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;		
	}

	private RawModel generateTerrain(Loader loader, String heightMap){
			
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/"+heightMap+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		int VERTEX_COUNT = image.getHeight();
		originHeights	= new float[VERTEX_COUNT][VERTEX_COUNT];
			
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float height = getHeight(j,i, image);
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				
				originHeights[j][i] = height;
				Vector3f normal = calculateNormal(j, i, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;					
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;				
				}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;					
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		heights = originHeights;
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	public void resetTerrain() {
		float[][] resetHeights = new float[heights.length][heights[0].length];
		for(int i=0; i<resetHeights.length; i++) {
			for(int j=0; j<resetHeights[i].length; j++) {
				resetHeights[i][j] = 0;
			}
		}
		heights = resetHeights;
	}
	
	/**
	 * @param x x position on the image
	 * @param z z position on the image
	 * @param image heightmap image
	 * @return the normal at that specific position
	 */
	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x-1, z, image);
		float heightR = getHeight(x+1, z, image);
		float heightD = getHeight(x, z-1, image);
		float heightU = getHeight(x, z+1, image);
		
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
		normal.normalise();
		return normal;
	}
		
	/**
	 * @param x x position on the image
	 * @param y y position on the image
	 * @param image image of the height map
	 * @return the height at that specific position
	 */
	private float getHeight(int x, int y, BufferedImage image) {
		if(x<0 || x>=image.getHeight() || y<0 || y>=image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
	}
	@Override
	public String toString(){
		return ID;
	}

	public float[][] getHeights(){return heights;}
	public void setHeights(float[][] heights){this.heights = heights;}



}
