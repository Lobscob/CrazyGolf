package models;

/**
 * This is a generic class for every basic model
 */
public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	private float[] normals;
	private float[] indeces;
	
	/**
	 * @param vaoID vertex array object ID position
	 * @param vertexCount total number of vertices of the model
	 */
	public RawModel(int vaoID, int vertexCount, float[] normals, float[] indeces) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.normals = normals;
		this.indeces = indeces;
	}
	
	public int getVaoID() { return vaoID; }
	public int getVertexCount() { return vertexCount; }
	public float[] getNormals() { return normals; }
	public float[] getIndeces() { return indeces; }
	

}
