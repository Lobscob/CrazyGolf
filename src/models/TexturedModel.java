package models;

import textures.ModelTexture;

/**
 * This class represents a model with a texture applied
 */
public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	private int index;
	
	/**
	 * @param model reference to the untextured model
	 * @param texture texture applied
	 */
	public TexturedModel(RawModel model, ModelTexture texture, int i) {
		this.rawModel = model;
		this.texture = texture;
		this.index = i;
	}
	
	public RawModel getRawModel() { return this.rawModel; }
	public ModelTexture getTextureModel() { return this.texture; }
	public int getIndex(){ return index; }
}
