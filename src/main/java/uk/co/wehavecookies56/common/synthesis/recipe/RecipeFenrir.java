package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeFenrir extends Recipe {

	public String name;

	public RecipeFenrir (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_Fenrir;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_PowerStone), 6);
		reqs.put(MaterialRegistry.get(Strings.SM_DenseShard), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_TwilightGem), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_EnergyCrystal), 2);
		return reqs;
	}

}
