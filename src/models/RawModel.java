package models;

/**
 * This is a generic class for every basic model
 */
public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	
	/**
	 * @param vaoID vertex array object ID position
	 * @param vertexCount total number of vertices of the model
	 */
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() { return vaoID; }
	public int getVertexCount() { return vertexCount; }
	

}
