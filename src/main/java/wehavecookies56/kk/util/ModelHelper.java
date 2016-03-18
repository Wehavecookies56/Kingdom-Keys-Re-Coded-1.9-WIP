package wehavecookies56.kk.util;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.obj.OBJModel;

public class ModelHelper {

	public static Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
		@Override
		public TextureAtlasSprite apply (ResourceLocation location) {
			return DummyAtlasTextureNormal.instance;
		}
	};

	public static Function<ResourceLocation, TextureAtlasSprite> textureGetterFlipU = new Function<ResourceLocation, TextureAtlasSprite>() {
		@Override
		public TextureAtlasSprite apply (ResourceLocation location) {
			return DummyAtlasTextureFlipU.instance;
		}
	};

	public static Function<ResourceLocation, TextureAtlasSprite> textureGetterFlipV = new Function<ResourceLocation, TextureAtlasSprite>() {
		@Override
		public TextureAtlasSprite apply (ResourceLocation location) {
			return DummyAtlasTextureFlipV.instance;
		}
	};

	private static class DummyAtlasTextureNormal extends TextureAtlasSprite {
		public static DummyAtlasTextureNormal instance = new DummyAtlasTextureNormal();

		protected DummyAtlasTextureNormal () {
			super("dummy");
		}

		@Override
		public float getInterpolatedU (double u) {
			return (float) u / 16;
		}

		@Override
		public float getInterpolatedV (double v) {
			return (float) v / 16;
		}
	}

	private static class DummyAtlasTextureFlipU extends TextureAtlasSprite {
		public static DummyAtlasTextureFlipU instance = new DummyAtlasTextureFlipU();

		protected DummyAtlasTextureFlipU () {
			super("dummyFlipU");
		}

		@Override
		public float getInterpolatedU (double u) {
			return (float) u / -16;
		}

		@Override
		public float getInterpolatedV (double v) {
			return (float) v / 16;
		}
	}

	private static class DummyAtlasTextureFlipV extends TextureAtlasSprite {
		public static DummyAtlasTextureFlipV instance = new DummyAtlasTextureFlipV();

		protected DummyAtlasTextureFlipV () {
			super("dummyFlipV");
		}

		@Override
		public float getInterpolatedU (double u) {
			return (float) u / 16;
		}

		@Override
		public float getInterpolatedV (double v) {
			return (float) v / -16;
		}
	}

	public static final String ALL_PARTS = "ALL";

	public static HashMap<String, IFlexibleBakedModel> getModelsForGroups (OBJModel objModel) {

		HashMap<String, IFlexibleBakedModel> modelParts = new HashMap<String, IFlexibleBakedModel>();

		if (!objModel.getMatLib().getGroups().keySet().isEmpty()) for (String key : objModel.getMatLib().getGroups().keySet()) {
			String k = key;
			if (!modelParts.containsKey(key)) modelParts.put(k, objModel.bake(new OBJModel.OBJState(ImmutableList.of(k), false), Attributes.DEFAULT_BAKED_FORMAT, ModelHelper.textureGetterFlipV));
		}

		modelParts.put(ALL_PARTS, objModel.bake(objModel.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetterFlipV));

		return modelParts;
	}

	public static void renderBakedModel (IFlexibleBakedModel bakedModel) {
		Tessellator tessellator = Tessellator.getInstance();

		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(GL11.GL_QUADS, bakedModel.getFormat());
		for (BakedQuad bakedQuad : bakedModel.getGeneralQuads())
			worldrenderer.addVertexData(bakedQuad.getVertexData());

		tessellator.draw();
	}

}
