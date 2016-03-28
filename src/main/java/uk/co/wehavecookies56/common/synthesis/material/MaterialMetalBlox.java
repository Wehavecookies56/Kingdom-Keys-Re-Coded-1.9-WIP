package uk.co.wehavecookies56.common.synthesis.material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.common.block.ModBlocks;

public class MaterialMetalBlox extends Material {

	String name;

	public MaterialMetalBlox (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public ItemStack getItem () {
		ItemStack stack = new ItemStack(ModBlocks.MetalBlox);
		return stack;
	}

	@Override
	public ResourceLocation getTexture () {
		return null;
	}

}
