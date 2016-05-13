package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import models.RawModel;
import textures.TextureData;

/**
 * This class is used to store vertix data in a VAO (a vertix array object) 
 */
public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	public List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	/**
	 * @param positions array of positions inside the VAO
	 * @param textureCoords array of coordinates of a model being loaded to a VAO
	 * @param normals array of normal vectors of a model being loaded to a VAO
	 * @param indeces array of indeces of a model being loaded to a VAO
	 * @return
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indeces) {
		int vaoID = createVAO();
		storeDataInAttributeList(0,3, positions);
		storeDataInAttributeList(1,2, textureCoords);
		storeDataInAttributeList(2,3, normals);
		bindIndecesBuffer(indeces);
		unbindVAO();
		return new RawModel(vaoID,indeces.length, normals, positions);
	}
	public RawModel loadNewTerrain(float[] positions, float[] textureCoords, float[] normals, int[] indeces){
		int vaoID = createVAOWithNewTerrain();

		storeDataInAttributeList(0,3, positions);
		storeDataInAttributeList(1,2, textureCoords);
		storeDataInAttributeList(2,3, normals);
		
		bindTerrainIndecesBuffer(indeces);
		//System.out.println("Terrain: " + vaoID + " VBO Arraylist: " + vbos.size());
		unbindVAO();
		return new RawModel(vaoID,indeces.length, normals, positions);
	}
	
	public RawModel loadToVAO(float[] positions, int dimensions, float[] normals, float[] indeces) {
		int vaoID = createVAO();
		this.storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length/dimensions, normals, indeces );
	}
	
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
		
	}
	
	
	public void cleanUp() {
		for(int voa:vaos) {
			GL30.glDeleteVertexArrays(voa);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	public void cleanVAO() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
	}
	public void cleanVBO() {
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	public int loadCubeMap(String[] textureFiles) {
		int texID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
		
		for(int i=0; i<textureFiles.length; i++) {
			TextureData data = decodeTextureFile("res/" + textureFiles[i] + ".png");
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), 
					data.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		textures.add(texID);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		return texID;
	}
	
	private TextureData decodeTextureFile(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream(fileName);
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Tried to load texture " + fileName + ", didn't work");
			System.exit(-1);
		}
		return new TextureData(buffer, width, height);
	}
	
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	private int createVAOWithNewTerrain() {
		GL30.glBindVertexArray(0);
		vaos.remove(0);
		vaos.add(0, 1);
		GL30.glBindVertexArray(1);
		return 1;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		
		//terrainVBOID = vboID;
		//System.out.println(terrainVBOID);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	private void bindIndecesBuffer(int[] indeces) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indeces);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	
	
	private IntBuffer terrainIntBuffer;
	private void bindTerrainIndecesBuffer(int[] indeces) {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 1);
		if(terrainIntBuffer == null) {
			terrainIntBuffer = storeDataInIntBuffer(indeces);
		} else {
			storeDataInIntTerrainBuffer(indeces);
		}
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, terrainIntBuffer, GL15.GL_STATIC_DRAW);
	}
	private IntBuffer storeDataInIntTerrainBuffer(int[] data) {
		terrainIntBuffer.clear();
		terrainIntBuffer.put(data);
		terrainIntBuffer.flip();
		return terrainIntBuffer;
	}	
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	

}
