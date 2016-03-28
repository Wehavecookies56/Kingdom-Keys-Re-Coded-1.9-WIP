package uk.co.wehavecookies56.common.synthesis.material;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Reference;
import uk.co.wehavecookies56.common.lib.Strings;
import uk.co.wehavecookies56.common.item.ItemStacks;

public class MaterialPowerGem extends Material {

	String name;

	public MaterialPowerGem (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public ItemStack getItem () {
		ItemStack stack = new ItemStack(ModItems.SynthesisMaterial);
		ItemStacks.createSynthesisItem(stack, Strings.SM_PowerGem, "A");
		return stack;
	}

	@Override
	public ResourceLocation getTexture () {
		return new ResourceLocation(Reference.MODID, "textures/gui/synthesis/powergem.png");
	}

}
