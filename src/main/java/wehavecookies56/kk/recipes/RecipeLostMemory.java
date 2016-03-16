package wehavecookies56.kk.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import wehavecookies56.kk.api.materials.Material;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.api.recipes.Recipe;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.lib.Strings;

public class RecipeLostMemory extends Recipe {

	public String name;

	public RecipeLostMemory (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_LostMemory;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_SerenityShard), 1);
		reqs.put(MaterialRegistry.get(Strings.SM_TwilightGem), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_PowerStone), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_MythrilGem), 3);
		return reqs;
	}

}
