package uk.co.wehavecookies56.kk.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.kk.api.materials.Material;
import uk.co.wehavecookies56.kk.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.kk.api.recipes.Recipe;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class RecipeFrolicFlame extends Recipe {

	public String name;

	public RecipeFrolicFlame (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_FrolicFlame;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_BlazingShard), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_BlazingGem), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_BlazingCrystal), 1);
		reqs.put(MaterialRegistry.get(Strings.SM_PowerStone), 1);
		return reqs;
	}

}
