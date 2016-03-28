package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeGuardianBell extends Recipe {

	public String name;

	public RecipeGuardianBell (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_GuardianBell;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_DarkStone), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_LightningShard), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_EnergyCrystal), 2);
		return reqs;
	}

}
