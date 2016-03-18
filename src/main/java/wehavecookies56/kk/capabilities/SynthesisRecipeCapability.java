package wehavecookies56.kk.capabilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;
import wehavecookies56.kk.api.recipes.Recipe;
import wehavecookies56.kk.api.recipes.RecipeRegistry;
import wehavecookies56.kk.util.LogHelper;

public class SynthesisRecipeCapability {

	public List<String> knownRecipes = new ArrayList<String>();

	public interface ISynthesisRecipe {
		List<String> getKnownRecipes();

		void learnRecipe(Recipe recipe);
	}

	public static class Storage implements IStorage<ISynthesisRecipe> {

		@Override
		public NBTBase writeNBT(Capability<ISynthesisRecipe> capability, ISynthesisRecipe instance, EnumFacing side) {
			NBTTagCompound properties = new NBTTagCompound();

			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < instance.getKnownRecipes().size(); i++) {
				String s = instance.getKnownRecipes().get(i);
				if (s != null) {
					NBTTagCompound recipes = new NBTTagCompound();
					recipes.setString("Recipes" + i, s);
					tagList.appendTag(recipes);
				}

			}
			properties.setTag("RecipeList", tagList);
			return properties;
		}

		@Override
		public void readNBT(Capability<ISynthesisRecipe> capability, ISynthesisRecipe instance, EnumFacing side, NBTBase nbt) {

			NBTTagCompound properties = (NBTTagCompound) nbt;
			NBTTagList tagList = properties.getTagList("RecipeList", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound recipes = tagList.getCompoundTagAt(i);
				if (!RecipeRegistry.isRecipeKnown(instance.getKnownRecipes(), recipes.getString("Recipes" + i))) {
					instance.getKnownRecipes().add(i, recipes.getString("Recipes" + i));
					LogHelper.info("Loaded known recipe: " + recipes.getString("Recipes" + i) + " " + i);
				}

			}

		}

	}

	public static class Default implements ISynthesisRecipe {
		private List<String> knownRecipes = new ArrayList<String>();

		@Override public List<String> getKnownRecipes() { return knownRecipes; }

		@Override public void learnRecipe(Recipe recipe) { this.knownRecipes.add(recipe.getName()); }	
	}

}
