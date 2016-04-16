package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Shaders.StaticShader;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import textures.ModelTexture;
import toolbox.Maths;

/**
 * This class is used specifically for entity rendering
 */
public class EntityRenderer {
	
	/**
	 * @param shader reference to the static shader used to render entities
	 * @param projectionMatrix matrix for projecting entities in 3 dimensions
	 */
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		
		this.shader = shader;
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	

	private StaticShader shader;
	
	
	/**
	 * @param entities a map of a textured model and list of entities to be rendered
	 */
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model:entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT,0);

			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawmodel = model.getRawModel();
		
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		ModelTexture texture = model.getTextureModel();
		//shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		shader.loadNumberofRows(texture.getNumberOfRows());
		
		if(texture.isHasTransparency()) {
			MasterRenderer.disableCulling();
		}
		
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTextureModel().getTextureID());
		
	}
	
	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
	}
	
	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
	
	
	
	

}
