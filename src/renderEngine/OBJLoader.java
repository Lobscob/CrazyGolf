package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

/**
 * This class is used to load OBJ files to create a model out of vertix coordinates
 */
public class OBJLoader {
	
	/**
	 * @param fileName name of the OBJ file
	 * @param loader the loader used to load the object into a VAO
	 * @return the raw model of the data stored in the OBJ file
	 */
	public static RawModel loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load Obj file");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indeces = new ArrayList<Integer>();
		
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indecesArray = null;
		
		try {
			
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				
				if(line.startsWith("v ")) {
					
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
					
				}else if(line.startsWith("vt ")) {
					
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
					
				}else if(line.startsWith("vn ")) {
					
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
					
				}else if(line.startsWith("f ")) {
					
					textureArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}
			
			while(line!=null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indeces, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indeces, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indeces, textures, normals, textureArray, normalsArray);
				
				line = reader.readLine();
			}
			reader.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	 verticesArray = new float[vertices.size()*3];
	 indecesArray = new int[indeces.size()];
	 
	 int vertexPointer = 0;
	 for(Vector3f vertex:vertices) {
		 verticesArray[vertexPointer++] = vertex.x;
		 verticesArray[vertexPointer++] = vertex.y;
		 verticesArray[vertexPointer++] = vertex.z;

	 }
	 
	 for(int i=0; i<indeces.size(); i++) {
		 indecesArray[i] = indeces.get(i);
	 }
	 
	 return loader.loadToVAO(verticesArray,  textureArray, normalsArray,  indecesArray);
	 

	}
	
	/**
	 * @param vertexData array of strings containing data about each vertex position
	 * @param indeces list of indices of each vertex
	 * @param textures list of texture coordinates
	 * @param normals list of normal texture coordinates
	 * @param textureArray array of texture indices
	 * @param normalsArray array of normals
	 */
	private static void processVertex(String[] vertexData, List<Integer> indeces, 
			List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, 
			float[] normalsArray) {
		
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indeces.add(currentVertexPointer);
		
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;
		
	}

}
