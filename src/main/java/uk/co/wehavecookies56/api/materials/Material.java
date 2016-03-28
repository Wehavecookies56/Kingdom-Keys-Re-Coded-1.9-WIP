package uk.co.wehavecookies56.api.materials;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class Material {

	public abstract String getName ();

	public abstract ItemStack getItem ();

	public abstract ResourceLocation getTexture ();

}
