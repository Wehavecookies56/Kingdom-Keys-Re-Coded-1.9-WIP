package uk.co.wehavecookies56.kk.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.kk.api.recipes.RecipeRegistry;
import uk.co.wehavecookies56.kk.common.capability.ModCapabilities;
import uk.co.wehavecookies56.kk.common.capability.SynthesisRecipeCapability;
import uk.co.wehavecookies56.kk.common.core.helper.TextHelper;
import uk.co.wehavecookies56.kk.common.lib.Strings;
import uk.co.wehavecookies56.kk.common.network.packet.AbstractMessage;
public class UseRecipe extends AbstractMessage.AbstractServerMessage<UseRecipe> {

	String recipe1, recipe2, recipe3;

	public UseRecipe () {}

	public UseRecipe (String recipe1, String recipe2, String recipe3) {
		this.recipe1 = recipe1;
		this.recipe2 = recipe2;
		this.recipe3 = recipe3;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		recipe1 = buffer.readStringFromBuffer(40);
		recipe2 = buffer.readStringFromBuffer(40);
		recipe3 = buffer.readStringFromBuffer(40);
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeString(recipe1);
		buffer.writeString(recipe2);
		buffer.writeString(recipe3);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		boolean consume = false;
		SynthesisRecipeCapability.ISynthesisRecipe RECIPES = player.getCapability(ModCapabilities.SYNTHESIS_RECIPES, null);
		if (RecipeRegistry.get(recipe1) == null) {
			String message = "ERROR: Recipe for " + TextHelper.localize(recipe1 + ".name") + " was not learnt because it is not a valid recipe, Report this to Wehavecookies56";
			TextHelper.sendFormattedChatMessage(message, TextFormatting.RED, player);
		} else if (RecipeRegistry.isRecipeKnown(RECIPES.getKnownRecipes(), recipe1)) {
			String message = String.format(TextHelper.localize(Strings.RecipeRepeat), TextHelper.localize(recipe1 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.YELLOW, player);
		} else {
			RecipeRegistry.learnrecipe(RECIPES.getKnownRecipes(), player, recipe1);
			String message = String.format(TextHelper.localize(Strings.RecipeLearn), TextHelper.localize(recipe1 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.GREEN, player);
			consume = true;
		}
		if (RecipeRegistry.get(recipe2) == null) {
			String message = "ERROR: Recipe for " + TextHelper.localize(recipe2 + ".name") + " was not learnt because it is not a valid recipe, Report this to Wehavecookies56";
			TextHelper.sendFormattedChatMessage(message, TextFormatting.RED, player);
		} else if (RecipeRegistry.isRecipeKnown(RECIPES.getKnownRecipes(), recipe2)) {
			String message = String.format(TextHelper.localize(Strings.RecipeRepeat), TextHelper.localize(recipe2 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.YELLOW, player);
		} else {
			RecipeRegistry.learnrecipe(RECIPES.getKnownRecipes(), player, recipe2);
			String message = String.format(TextHelper.localize(Strings.RecipeLearn), TextHelper.localize(recipe2 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.GREEN, player);
			consume = true;
		}
		if (RecipeRegistry.get(recipe3) == null) {
			String message = "ERROR: Recipe for " + TextHelper.localize(recipe3 + ".name") + " was not learnt because it is not a valid recipe, Report this to Wehavecookies56";
			TextHelper.sendFormattedChatMessage(message, TextFormatting.RED, player);
		} else if (RecipeRegistry.isRecipeKnown(RECIPES.getKnownRecipes(), recipe3)) {
			String message = String.format(TextHelper.localize(Strings.RecipeRepeat), TextHelper.localize(recipe3 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.YELLOW, player);
		} else {
			RecipeRegistry.learnrecipe(RECIPES.getKnownRecipes(), player, recipe3);
			String message = String.format(TextHelper.localize(Strings.RecipeLearn), TextHelper.localize(recipe3 + ".name"));
			TextHelper.sendFormattedChatMessage(message, TextFormatting.GREEN, player);
			consume = true;
		}

		if (consume) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
	}

}