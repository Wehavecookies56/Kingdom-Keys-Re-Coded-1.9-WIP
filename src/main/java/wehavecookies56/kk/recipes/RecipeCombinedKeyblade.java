package wehavecookies56.kk.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import wehavecookies56.kk.api.materials.Material;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.api.recipes.Recipe;
import wehavecookies56.kk.item.ModItems;

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
