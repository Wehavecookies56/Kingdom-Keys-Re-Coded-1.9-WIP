package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;

public class RecipeCombinedKeyblade extends Recipe {

	public String name;

	public RecipeCombinedKeyblade (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_NightmaresEndandMirageSplit;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(ModItems.Chain_NightmaresEnd.getUnlocalizedName()), 1);
		reqs.put(MaterialRegistry.get(ModItems.Chain_MirageSplit.getUnlocalizedName()), 1);
		return reqs;
	}

}
