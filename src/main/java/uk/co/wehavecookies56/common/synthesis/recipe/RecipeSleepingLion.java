package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeSleepingLion extends Recipe {

	public String name;

	public RecipeSleepingLion (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_SleepingLion;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_BlazingShard), 4);
		reqs.put(MaterialRegistry.get(Strings.SM_PowerStone), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_TranquilCrystal), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_TwilightGem), 1);
		return reqs;
	}

}
