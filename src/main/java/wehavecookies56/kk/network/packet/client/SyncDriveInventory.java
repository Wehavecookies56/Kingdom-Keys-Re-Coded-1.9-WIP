package wehavecookies56.kk.network.packet.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.api.recipes.RecipeRegistry;
import wehavecookies56.kk.capabilities.DriveStateCapability.IDriveState;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.capabilities.SynthesisRecipeCapability.ISynthesisRecipe;
import wehavecookies56.kk.item.ItemDriveForm;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncDriveInventory extends AbstractClientMessage<SyncDriveInventory> {

	private NBTTagCompound data;
	
	public SyncDriveInventory() {}
	
	public SyncDriveInventory(IDriveState stats) {
		data = new NBTTagCompound();
		stats.getInventoryDriveForms().writeToNBT(data);
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		data = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.getCapability(KingdomKeys.DRIVE_STATE, null).getInventoryDriveForms().readFromNBT(data);
	}


}
