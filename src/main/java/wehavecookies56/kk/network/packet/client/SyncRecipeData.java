package wehavecookies56.kk.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.recipes.RecipeRegistry;
import wehavecookies56.kk.capabilities.SynthesisRecipeCapability.ISynthesisRecipe;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncRecipeData extends AbstractClientMessage<SyncRecipeData> {

	private List<String> recipes;
	
	public SyncRecipeData() {}
	
	public SyncRecipeData(ISynthesisRecipe recipe) {
		this.recipes = recipe.getKnownRecipes();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		recipes = new ArrayList<String>();
		while(buffer.isReadable()){
			recipes.add(buffer.readStringFromBuffer(100));
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		for (int i = 0; i < recipes.size(); i++){
			buffer.writeString(recipes.get(i));
		}
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		System.out.println("Syncing data");
		final ISynthesisRecipe recipe = player.getCapability(KingdomKeys.SYNTHESIS_RECIPES, null);
		for (int i = 0; i < recipes.size(); i++) {
			recipe.learnRecipe(RecipeRegistry.get(recipes.get(i)));
		}
	}


}
