package uk.co.wehavecookies56.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.capability.PlayerStatsCapability.IPlayerStats;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncItemsInventory extends AbstractClientMessage<SyncItemsInventory> {

	private NBTTagCompound data;
	
	public SyncItemsInventory() {}
	
	public SyncItemsInventory(IPlayerStats stats) {
		data = new NBTTagCompound();
		stats.getInventoryPotionsMenu().writeToNBT(data);
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
		player.getCapability(ModCapabilities.PLAYER_STATS, null).getInventoryPotionsMenu().readFromNBT(data);
	}


}
