package uk.co.wehavecookies56.common.synthesis.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import uk.co.wehavecookies56.api.materials.Material;
import uk.co.wehavecookies56.api.materials.MaterialRegistry;
import uk.co.wehavecookies56.api.recipes.Recipe;
import uk.co.wehavecookies56.common.item.ModItems;
import uk.co.wehavecookies56.common.lib.Strings;

public class RecipeUltimaWeaponKH1 extends Recipe {

	public String name;

	public RecipeUltimaWeaponKH1 (String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public Item getResult () {
		return ModItems.Chain_UltimaWeaponKH1;
	}

	@Override
	public Map<Material, Integer> getRequirements () {
		Map<Material, Integer> reqs = new HashMap<Material, Integer>();
		reqs.put(MaterialRegistry.get(Strings.SM_LightningGem), 5);
		reqs.put(MaterialRegistry.get(Strings.SM_SerenityStone), 5);
		reqs.put(MaterialRegistry.get(Strings.SM_SerenityCrystal), 5);
		reqs.put(MaterialRegistry.get(Strings.SM_StormyStone), 3);
		reqs.put(MaterialRegistry.get(Strings.SM_LostIllusion), 1);
		return reqs;
	}

}
