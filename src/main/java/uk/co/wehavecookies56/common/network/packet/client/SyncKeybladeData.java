package uk.co.wehavecookies56.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.capability.SummonKeybladeCapability.ISummonKeyblade;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractClientMessage;

public class SyncKeybladeData extends AbstractClientMessage<SyncKeybladeData> {

	boolean summoned;
	NBTTagCompound data;
	
	public SyncKeybladeData() {}
	
	public SyncKeybladeData(ISummonKeyblade keyblade) {
		this.summoned = keyblade.getIsKeybladeSummoned();
		this.data = new NBTTagCompound();
		keyblade.getInventoryKeychain().writeToNBT(data);
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.summoned = buffer.readBoolean();
		this.data = buffer.readNBTTagCompoundFromBuffer();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBoolean(this.summoned);
		buffer.writeNBTTagCompoundToBuffer(data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		final ISummonKeyblade keyblade = player.getCapability(ModCapabilities.SUMMON_KEYBLADE, null);
		keyblade.setIsKeybladeSummoned(this.summoned);
		keyblade.getInventoryKeychain().readFromNBT(data);
	}

}
