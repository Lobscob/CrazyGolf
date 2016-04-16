package GUIS;

import org.lwjgl.util.vector.Vector2f;

/**
 * This class applies a texture at a position at a certain scale
 */
public class GuiTexture {
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	/**
	 * @param texture position ID of the texture
	 * @param position 2D vector of the position of the texture on the screen
	 * @param scale scale of the texture
	 */
	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	
}
