package uk.co.wehavecookies56.common.network.packet.client;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.wehavecookies56.common.capability.DriveStateCapability.IDriveState;
import uk.co.wehavecookies56.common.capability.ModCapabilities;
import uk.co.wehavecookies56.common.network.packet.AbstractMessage.AbstractClientMessage;

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
		player.getCapability(ModCapabilities.DRIVE_STATE, null).getInventoryDriveForms().readFromNBT(data);
	}


}
