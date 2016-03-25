package wehavecookies56.kk.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.client.audio.ModSounds;
import wehavecookies56.kk.network.packet.PacketDispatcher;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractServerMessage;
import wehavecookies56.kk.network.packet.client.SyncKeybladeData;
import wehavecookies56.kk.util.SoundHelper;

public class DeSummonKeyblade extends AbstractServerMessage<DeSummonKeyblade> {

	public DeSummonKeyblade () {}

	ItemStack toRemove;

	public DeSummonKeyblade (ItemStack toRemove) {
		this.toRemove = toRemove;
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		toRemove = buffer.readItemStackFromBuffer();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeItemStackToBuffer(toRemove);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		player.worldObj.playSound((EntityPlayer)null, player.getPosition(), ModSounds.unsummon, SoundCategory.MASTER, 1.0f, 1.0f);
		player.getCapability(KingdomKeys.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(false);
		PacketDispatcher.sendTo(new SyncKeybladeData(player.getCapability(KingdomKeys.SUMMON_KEYBLADE, null)), (EntityPlayerMP) player);
	}

}
