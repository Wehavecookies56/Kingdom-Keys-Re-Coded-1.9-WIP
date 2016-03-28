package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeMasterXehanortsKeyblade extends Recipe {

	public String name;

	public RecipeMasterXehanortsKeyblade (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_MasterXehanortsKeyblade;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_DarkCrystal), 13);
		reqs.put(MaterialRegistry.get(Strings.SM_DarkGem), 11);
		reqs.put(MaterialRegistry.get(Strings.SM_LucidCrystal), 5);
		reqs.put(MaterialRegistry.get(Strings.SM_OrichalcumPlus), 1);
		reqs.put(MaterialRegistry.get(Strings.SM_PowerCrystal), 1);
		return reqs;
	}

}
