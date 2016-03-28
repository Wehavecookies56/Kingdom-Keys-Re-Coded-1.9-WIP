package uk.co.wehavecookies56.common.synthesis.material;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import uk.co.wehavecookies56.api.materials.Material;

public class MaterialWoodenSword extends Material {

	String name;

	public MaterialWoodenSword (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public ItemStack getItem () {
		ItemStack stack = new ItemStack(Items.wooden_sword);
		return stack;
	}

	@Override
	public ResourceLocation getTexture () {
		return null;
	}

}
