package uk.co.wehavecookies56.kk.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.kk.api.materials.Material;
import uk.co.wehavecookies56.kk.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.kk.api.recipes.Recipe;
import uk.co.wehavecookies56.kk.common.item.ModItems;
import uk.co.wehavecookies56.kk.common.lib.Strings;

public class RecipePainofSolitude extends Recipe {

	public String name;

	public RecipePainofSolitude (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_PainofSolitude;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_PowerShard), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkCrystal), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_DenseShard), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_TwilightStone), 1);
		return reqs;
	}

}
