package uk.co.wehavecookies56.common.network.packet.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.client.sound.ModSounds;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;
import uk.co.wehavecookies56.common.network.packet.client.SyncKeybladeData;

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
		player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(false);
		PacketDispatcher.sendTo(new SyncKeybladeData(player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null)), (EntityPlayerMP) player);
	}

}
