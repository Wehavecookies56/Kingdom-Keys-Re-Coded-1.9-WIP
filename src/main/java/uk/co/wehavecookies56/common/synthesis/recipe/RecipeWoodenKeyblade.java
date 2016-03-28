package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;

public class RecipeWoodenKeyblade extends Recipe {

	public String name;

	public RecipeWoodenKeyblade (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.WoodenKeyblade;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Items.wooden_sword.getUnlocalizedName()), 1);
		return reqs;
	}

}
