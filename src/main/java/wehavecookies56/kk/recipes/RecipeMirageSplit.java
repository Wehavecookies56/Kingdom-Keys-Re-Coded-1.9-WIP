package wehavecookies56.kk.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import wehavecookies56.kk.api.materials.Material;
import wehavecookies56.kk.api.materials.MaterialRegistry;
import wehavecookies56.kk.api.recipes.Recipe;
import wehavecookies56.kk.item.ModItems;
import wehavecookies56.kk.lib.Strings;

public class RecipeMirageSplit extends Recipe {

	public String name;

	public RecipeMirageSplit (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_MirageSplit;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_DarkShard), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkStone), 2);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkGem), 6);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkCrystal), 4);
		reqs.put(MaterialRegistry.get(Strings.SM_Orichalcum), 1);
		return reqs;
	}

}
