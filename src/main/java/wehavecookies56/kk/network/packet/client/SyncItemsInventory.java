package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.PlayerStatsCapability.IPlayerStats;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

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
		player.getCapability(KingdomKeys.PLAYER_STATS, null).getInventoryPotionsMenu().readFromNBT(data);
	}


}
