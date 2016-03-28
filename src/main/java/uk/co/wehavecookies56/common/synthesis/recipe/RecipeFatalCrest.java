package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeFatalCrest extends Recipe {

	public String name;

	public RecipeFatalCrest (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_FatalCrest;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_DarkStone), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkGem), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_DenseStone), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_LightningShard), 1);
		return reqs;
	}

}
