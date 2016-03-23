package wehavecookies56.kk.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wehavecookies56.kk.KingdomKeys;
import wehavecookies56.kk.capabilities.SummonKeybladeCapability.ISummonKeyblade;
import wehavecookies56.kk.inventory.InventoryKeychain;
import wehavecookies56.kk.network.packet.AbstractMessage.AbstractClientMessage;

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
		final ISummonKeyblade keyblade = player.getCapability(KingdomKeys.SUMMON_KEYBLADE, null);
		keyblade.setIsKeybladeSummoned(this.summoned);
		keyblade.getInventoryKeychain().readFromNBT(data);
	}

}
