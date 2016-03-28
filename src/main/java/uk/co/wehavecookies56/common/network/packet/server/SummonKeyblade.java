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
import uk.co.wehavecookies56.common.item.ItemKeyblade;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractServerMessage;
import uk.co.wehavecookies56.common.network.packet.PacketDispatcher;
import uk.co.wehavecookies56.common.network.packet.client.SyncKeybladeData;

public class SummonKeyblade extends AbstractServerMessage<SummonKeyblade> {

	ItemStack stack;

	public SummonKeyblade () {}

	public SummonKeyblade (ItemKeyblade itemKeyblade) {
		this.stack = new ItemStack(itemKeyblade);
	}

	@Override
	protected void read (PacketBuffer buffer) throws IOException {
		stack = buffer.readItemStackFromBuffer();
	}

	@Override
	protected void write (PacketBuffer buffer) throws IOException {
		buffer.writeItemStackToBuffer(stack);
	}

	@Override
	public void process (EntityPlayer player, Side side) {
		player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
		player.worldObj.playSound((EntityPlayer)null, player.getPosition(), ModSounds.summon, SoundCategory.MASTER, 1.0f, 1.0f);
		player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null).setIsKeybladeSummoned(true);
		PacketDispatcher.sendTo(new SyncKeybladeData(player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null)), (EntityPlayerMP) player);
	}
}