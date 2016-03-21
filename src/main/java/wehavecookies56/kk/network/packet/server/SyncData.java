package wehavecookies56.kk.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.client.SyncDriveData;
import wehavecookies56.kk.network.packet.client.SyncMagicData;

public class SyncData extends AbstractServerMessage<SyncData> {

	public SyncData () {}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		PacketDispatcher.sendTo(new SyncDriveData(player.getCapability(KingdomKeys.DRIVE_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
		PacketDispatcher.sendTo(new SyncMagicData(player.getCapability(KingdomKeys.MAGIC_STATE, null), player.getCapability(KingdomKeys.PLAYER_STATS, null)), (EntityPlayerMP) player);
	}

}
